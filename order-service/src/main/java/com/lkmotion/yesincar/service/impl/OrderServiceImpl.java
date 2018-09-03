package com.lkmotion.yesincar.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lkmotion.yesincar.constatnt.BusinessInterfaceStatus;
import com.lkmotion.yesincar.constatnt.ChargingCategoryEnum;
import com.lkmotion.yesincar.constatnt.OrderEnum;
import com.lkmotion.yesincar.constatnt.OrderStatusEnum;
import com.lkmotion.yesincar.dto.*;
import com.lkmotion.yesincar.dto.valuation.PriceResult;
import com.lkmotion.yesincar.dto.valuation.charging.KeyRule;
import com.lkmotion.yesincar.dto.valuation.charging.Rule;
import com.lkmotion.yesincar.entity.*;
import com.lkmotion.yesincar.mapper.*;
import com.lkmotion.yesincar.request.OrderRequest;
import com.lkmotion.yesincar.request.Request;
import com.lkmotion.yesincar.service.OrderService;
import com.lkmotion.yesincar.task.OrderRequestTask;
import com.lkmotion.yesincar.task.OtherInterfaceTask;
import com.lkmotion.yesincar.util.EncriptUtil;
import com.lkmotion.yesincar.util.RestTemplateHepler;
import com.lkmotion.yesincar.utils.IdWorker;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;

/**
 * 功能描述
 *
 * @author liheng
 * @date 2018/8/25
 */
@Service
@Slf4j
@Repository
public class OrderServiceImpl implements OrderService {


    private static final String ERR_EMPTY_CHANGE_RULE = "无计价规则";
    private static final String PASSENGERS_IS_NULL = "乘客信息为空";
    private static final String INSERT_VALUATION_RULES = "插入计价规则失败";
    private static final String VALUATION_RULES_CHANGE = "计价规则有变";

    private IdWorker idWorker = IdWorker.getInstance();



    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private PassengerInfoMapper passengerInfoMapper;
    @Autowired
    private OrderRulePriceMapper orderRulePriceMapper;
    @Autowired
    private OrderRuleMirrorMapper orderRuleMirrorMapper;
    @Autowired
    private DriverInfoMapper driverInfoMapper;
    @Autowired
    private CarInfoMapper carInfoMapper;

    @Autowired
    private OtherInterfaceTask otherInterfaceTask;
    @Autowired
    private OrderRequestTask orderRequestTask;

    private boolean flag;
    private boolean sate;

    @Override
    public ResponseResult<OrderPrice> forecastOrderCreateUpdate(OrderRequest orderRequest) throws Exception {
        log.info("OrderRequest={}", orderRequest);
        OrderPrice orderPrice = new OrderPrice();
        ResponseResult responseResult;
        Integer orderId = orderRequest.getOrderId();
        if (null == orderId) {
            try {
                responseResult = otherInterfaceTask.requestRoute(orderRequest);
                if (BusinessInterfaceStatus.SUCCESS.getCode() != responseResult.getCode()) {
                    return responseResult;
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
            responseResult = orderRequestTask.checkBaseInfo(orderRequest);
            if (BusinessInterfaceStatus.SUCCESS.getCode() == responseResult.getCode()) {
                try {
                    BaseInfoDto baseInfoDto = RestTemplateHepler.parse(responseResult, BaseInfoDto.class);
                    orderRequest.setCityName(baseInfoDto.getCityName());
                    orderRequest.setChannelName(baseInfoDto.getChannelName());
                    orderRequest.setServiceTypeName(baseInfoDto.getServiceTypeName());
                    orderRequest.setCarLevelName(baseInfoDto.getCarLevelName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                return responseResult;
            }
            Rule rule = otherInterfaceTask.getOrderChargeRule(orderRequest);
            if (null == rule) {
                log.error(ERR_EMPTY_CHANGE_RULE);
                return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), ERR_EMPTY_CHANGE_RULE);
            }
            try {
                //创建订单
                responseResult = createOrderAndOrderRuleMirrer(orderRequest,rule);
                if (BusinessInterfaceStatus.SUCCESS.getCode() == responseResult.getCode()) {
                    Object data = responseResult.getData();
                    orderId = Integer.valueOf(data.toString());
                }else{
                    return responseResult;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            responseResult = updateOrderRuleMirror(orderRequest, orderId);
            if (BusinessInterfaceStatus.SUCCESS.getCode() == responseResult.getCode()) {
                sate = true;
            } else {
                return responseResult;
            }
        }
        if (sate) {
            try {
                PriceResult priceResult = otherInterfaceTask.getOrderPrice(orderId);
                orderPrice.setPrice(priceResult.getPrice());
            } catch (Exception e) {
                e.printStackTrace();
                log.error("计价接口发生错误",e);
                throw e;
            }
        }
        orderPrice.setOrderId(orderId);
        return ResponseResult.success(orderPrice);
    }

    /**
     * 创建订单和计价规则
     * @param orderRequest
     * @return
     * @throws Exception
     */
    //@Transactional(rollbackFor = Exception.class)
    public ResponseResult createOrderAndOrderRuleMirrer(OrderRequest orderRequest,Rule rule) throws Exception{
        ResponseResult responseResult;
        Integer orderId;
        responseResult = createOrder(orderRequest);
        if (BusinessInterfaceStatus.SUCCESS.getCode() == responseResult.getCode()) {
            flag = true;
            Order order = RestTemplateHepler.parse(responseResult, Order.class);
            orderId = order.getId();
            responseResult = insertOrUpdateOrderRuleMirror(rule, orderId);
            if (BusinessInterfaceStatus.SUCCESS.getCode() != responseResult.getCode()) {
                return responseResult;
            }
            sate = true;
        } else {
            return responseResult;
        }
        return responseResult.setData(orderId);
    }

    /**
     * 创建订单
     * @param request
     * @return
     */
    public ResponseResult createOrder(Request request) {
        OrderRequest orderRequest = (OrderRequest) request;
        if (null != orderRequest.getPassengerInfoId()) {
            PassengerInfo passengerInfo = passengerInfoMapper.selectByPrimaryKey(orderRequest.getPassengerInfoId());
            if (passengerInfo == null) {
                return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), PASSENGERS_IS_NULL);
            }
            String orderNumber = idWorker.nextId();
            Order order = new Order();
            order.setOrderNumber(orderNumber);
            order.setPassengerInfoId(orderRequest.getPassengerInfoId());
            order.setPassengerPhone(passengerInfo.getPhone());
            order.setDeviceCode(orderRequest.getDeviceCode());
            order.setUserLongitude(orderRequest.getUserLongitude());
            order.setUserLatitude(orderRequest.getUserLatitude());
            order.setStartLongitude(orderRequest.getStartLongitude());
            order.setStartLatitude(orderRequest.getStartLatitude());
            order.setStartAddress(orderRequest.getStartAddress());
            order.setServiceType(orderRequest.getServiceTypeId());
            order.setStatus(OrderStatusEnum.CALL_ORDER_FORECAST.getCode());
            Date startDate = new Date();
            if (null != orderRequest.getOrderStartTime()) {
                startDate = orderRequest.getOrderStartTime();
            }
            order.setOrderStartTime(startDate);
            order.setOrderChannel(orderRequest.getChannelId());
            order.setServiceType(orderRequest.getServiceTypeId());
            order.setEndLongitude(orderRequest.getEndLongitude());
            order.setEndLatitude(orderRequest.getEndLatitude());
            order.setEndAddress(orderRequest.getEndAddress());
            order.setSource(orderRequest.getSource());
            order.setCreateTime(new Date());
            orderMapper.insertSelective(order);
            return ResponseResult.success(order);
        } else {
            log.error(PASSENGERS_IS_NULL + "乘客手机号：" + orderRequest.getPassengerPhone());
            return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), PASSENGERS_IS_NULL);
        }
    }

    /**
     * 叫车
     *
     * @param orderRequest
     * @return
     */
    @Override
    //@Transactional(rollbackFor = Exception.class)
    public ResponseResult callCar(OrderRequest orderRequest) throws Exception {
        ResponseResult responseResult = new ResponseResult();
        log.info("OrderRequest={}", orderRequest);
        try{
            Integer orderId = orderRequest.getOrderId();
            OrderRuleMirror orderRuleMirror = orderRuleMirrorMapper.selectByPrimaryKey(orderId);
            String originalRule = orderRuleMirror.getRule();
            Rule rule = parse(originalRule,Rule.class);

            KeyRule keyRule = rule.getKeyRule();
            orderRequest.setCarLevelId(keyRule.getCarLevelId());
            orderRequest.setCarLevelName(keyRule.getCarLevelName());
            orderRequest.setServiceTypeId(keyRule.getServiceTypeId());
            orderRequest.setServiceTypeName(keyRule.getServiceTypeName());
            orderRequest.setChannelId(keyRule.getChannelId());
            orderRequest.setChannelName(keyRule.getChannelName());
            orderRequest.setCityCode(keyRule.getCityCode());
            orderRequest.setCityName(keyRule.getCityName());

            Rule newRule = otherInterfaceTask.getOrderChargeRule(orderRequest);
            String newestRule = new ObjectMapper().writeValueAsString(newRule);
            flag = false;
            responseResult = insertOrUpdateOrderRuleMirror(newRule, orderId);
            if (BusinessInterfaceStatus.SUCCESS.getCode() != responseResult.getCode()) {
                return responseResult;
            } else {
                if(!originalRule.equals(newestRule)){
                    log.error("计价规则有变");
                    return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), VALUATION_RULES_CHANGE);
                }
                Order order = orderMapper.selectByPrimaryKey(orderId);
                order.setStartTime(new Date());
                order.setStatus(OrderStatusEnum.STATUS_ORDER_START.getCode());
                order.setOrderType(orderRequest.getOrderType());
                String otherPhone;
                if (orderRequest.getOrderType() == OrderEnum.ORDER_TYPE_OTHER.getCode()) {
                    otherPhone = EncriptUtil.encryptionPhoneNumber(orderRequest.getOtherPhone());
                    order.setOtherName(orderRequest.getOtherName());
                } else {
                    PassengerInfo passengerInfo = passengerInfoMapper.selectByPrimaryKey(order.getPassengerInfoId());
                    otherPhone = passengerInfo.getPhone();
                    order.setOtherName(passengerInfo.getPassengerName());
                }
                order.setOtherPhone(otherPhone);
                orderMapper.updateByPrimaryKeySelective(order);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
        return ResponseResult.success("叫车成功");
    }

    @Override
    //@Transactional(rollbackFor = Exception.class)
    public ResponseResult updateOrder(Order order) throws Exception{
        log.info("Order={}", order);
        JSONObject jsonObject = new JSONObject();
        try{
            int code = orderMapper.updateByPrimaryKeySelective(order);
            if (code == 0) {
                return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), "更新失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
        return ResponseResult.success(jsonObject);
    }

    @Override
    //@Transactional(rollbackFor = Exception.class)
    public ResponseResult<OrderOtherPrice> otherPriceBalance(OrderRequest orderRequest) throws Exception{
        log.info("OrderRequest{}",orderRequest);
        OrderOtherPrice orderOtherPrice = new OrderOtherPrice();
        try{
            OrderRulePrice orderRulePrice = orderRulePriceMapper.selectByPrimaryKey(orderRequest.getOrderId(), ChargingCategoryEnum.Settlement.getCode());
            Double totalPrice = orderRulePrice.getTotalPrice();
            if(StringUtils.isBlank(orderRequest.getOrderId().toString())){
                return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), "orderId为空");
            }
            if (!ObjectUtils.nullSafeEquals(orderRequest.getRoadPrice(),0)) {
                totalPrice = totalPrice + orderRequest.getRoadPrice();
                orderRulePrice.setRoadPrice(orderRequest.getRoadPrice());
            }
            if (!ObjectUtils.nullSafeEquals(orderRequest.getParkingPrice(),0)) {
                totalPrice = totalPrice + orderRequest.getParkingPrice();
                orderRulePrice.setParkingPrice(orderRequest.getParkingPrice());
            }
            if (!ObjectUtils.nullSafeEquals(orderRequest.getOtherPrice(),0)) {
                totalPrice = totalPrice + orderRequest.getOtherPrice();
                orderRulePrice.setOtherPrice(orderRequest.getOtherPrice());
            }
            orderRulePrice.setTotalPrice(totalPrice);
            int updateSize = orderRulePriceMapper.updateByPrimaryKeySelective(orderRulePrice);
            if (updateSize == 0) {
                return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), "更新价格失败");
            } else {
                updateSize = 0;
                Order order = orderMapper.selectByPrimaryKey(orderRequest.getOrderId());
                order.setStatus(orderRequest.getStatus());
                updateSize = orderMapper.updateByPrimaryKeySelective(order);

                if (updateSize == 0) {
                    return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), "更新订单失败");
                }
                orderOtherPrice.setOrderId(order.getId());
                orderOtherPrice.setPassengerId(order.getPassengerInfoId());
                if(totalPrice >= orderRulePrice.getLowestPrice()){
                    orderOtherPrice.setTotalPrice(totalPrice);
                }else{
                    orderOtherPrice.setTotalPrice(orderRulePrice.getLowestPrice());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
        return ResponseResult.success(orderOtherPrice);
    }

    /**
     * 订单改派
     * @param request
     * @return
     */
    @Override
    //@Transactional(rollbackFor = Exception.class)
    public ResponseResult reassignmentOrder(OrderRequest request) throws Exception{
        log.info("OrderRequest{}",request);
        try{
            DriverInfo driverInfo = driverInfoMapper.selectByPrimaryKey(request.getDriverIdNow());
            if (null == driverInfo) {
                return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), "司机不存在");
            }
            Order order = orderMapper.selectByPrimaryKey(request.getOrderId());
            CarInfo carInfo = carInfoMapper.selectByPrimaryKey(driverInfo.getCarId());
            if (null == carInfo) {
                return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), "车辆不存在");
            }
            order.setDriverId(request.getDriverIdNow());
            order.setDriverPhone(driverInfo.getPhoneNumber());
            order.setCarId(driverInfo.getCarId());
            order.setPlateNumber(carInfo.getPlateNumber());
            order.setStatus(OrderStatusEnum.STATUS_DRIVER_ACCEPT.getCode());
            orderMapper.updateByPrimaryKeySelective(order);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
        return ResponseResult.success("改派成功");
    }

    /**
     * 修改计价规则
     * @param orderRequest
     * @param orderId
     * @return
     */
    public ResponseResult updateOrderRuleMirror(OrderRequest orderRequest, Integer orderId) {
        ResponseResult responseResult = new ResponseResult();
        OrderRuleMirror orderRuleMirror = orderRuleMirrorMapper.selectByPrimaryKey(orderId);
        String rule = orderRuleMirror.getRule();
        JSONObject ruleJson = JSONObject.fromObject(rule);
        JSONObject keyRule = JSONObject.fromObject(ruleJson.get("keyRule"));
        OrderKeyRuleDto orderKeyRuleDto = new OrderKeyRuleDto();
        orderKeyRuleDto.setOrderId(orderId);
        orderRequest.setServiceTypeId(Integer.valueOf(keyRule.get("serviceTypeId").toString()));
        orderRequest.setServiceTypeName(keyRule.get("serviceTypeName").toString());
        orderRequest.setChannelId(Integer.valueOf(keyRule.get("channelId").toString()));
        orderRequest.setChannelName(keyRule.get("channelName").toString());
        orderRequest.setCityCode(keyRule.get("cityCode").toString());
        orderRequest.setCityName(keyRule.get("cityName").toString());

        Rule rule1= otherInterfaceTask.getOrderChargeRule(orderRequest);
        if (null == rule1) {
            log.error(ERR_EMPTY_CHANGE_RULE);
            return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), ERR_EMPTY_CHANGE_RULE);
        } else {
            flag = false;
            responseResult = insertOrUpdateOrderRuleMirror(rule1, orderId);
            if (BusinessInterfaceStatus.SUCCESS.getCode() != responseResult.getCode()) {
                return responseResult;
            }
        }
        return ResponseResult.success("更新成功");
    }

    /**
     * 插入更新计价规则
     * @param orderId
     * @return
     */
    public ResponseResult insertOrUpdateOrderRuleMirror(Rule rule, Integer orderId)  {
        OrderRuleMirror orderRuleMirror = new OrderRuleMirror();
        try {
            orderRuleMirror.setOrderId(orderId);
            orderRuleMirror.setRuleId(rule.getId());
            orderRuleMirror.setRule(new ObjectMapper().writeValueAsString(rule));
            int up = 0;
            if (!flag) {
                up = orderRuleMirrorMapper.updateByPrimaryKeySelective(orderRuleMirror);
            } else {
                orderRuleMirror.setCreateTime(new Date());
                up = orderRuleMirrorMapper.insertSelective(orderRuleMirror);
            }
            if (up == 0) {
                return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(),"更新计价规则失败");
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return ResponseResult.success("");
    }

    /**
     * 字符串转实体类
     * @param jsonStr
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T parse(String jsonStr, Class<T> clazz) {
        ObjectMapper om = new ObjectMapper();
        T readValue = null;
        try {
            readValue = om.readValue(jsonStr, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return readValue;
    }


    }
