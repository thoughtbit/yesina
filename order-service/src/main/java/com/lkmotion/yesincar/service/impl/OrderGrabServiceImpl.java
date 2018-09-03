package com.lkmotion.yesincar.service.impl;

import com.aliyuncs.dyplsapi.model.v20170525.BindAxbResponse;
import com.lkmotion.yesincar.constatnt.BusinessInterfaceStatus;
import com.lkmotion.yesincar.constatnt.OrderEnum;
import com.lkmotion.yesincar.constatnt.OrderStatusEnum;
import com.lkmotion.yesincar.consts.DriverInfoConst;
import com.lkmotion.yesincar.dto.ResponseResult;
import com.lkmotion.yesincar.dto.map.BaseResponse;
import com.lkmotion.yesincar.entity.*;
import com.lkmotion.yesincar.mapper.CarInfoMapper;
import com.lkmotion.yesincar.mapper.DriverInfoMapper;
import com.lkmotion.yesincar.mapper.OrderMapper;
import com.lkmotion.yesincar.mapper.OrderRuleMirrorMapper;
import com.lkmotion.yesincar.request.OrderRequest;
import com.lkmotion.yesincar.service.OrderGrabService;
import com.lkmotion.yesincar.util.EncriptUtil;
import com.lkmotion.yesincar.util.SecretPhoneNumberService;
import com.lkmotion.yesincar.utils.ServicesConfig;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 功能描述
 *
 * @author liheng
 * @date 2018/8/25
 */
@Service
@Slf4j
public class OrderGrabServiceImpl implements OrderGrabService {

    private static final String DRIVER_IS_NULL ="司机信息不存在";
    private static final String CALL_CAR_FAILURE ="叫车失败";
    private static final String ORDER_CONFLICT ="所抢订单冲突";
    private static final String DRIVER_OUT_OF_SERVICE="司机停用";
    private static final String ORDER_ROBBED ="订单已经被抢";
    private static final String ORDER_ID_IS_NULL ="订单ID为空";
    private static final String GRAB_FAILURE ="抢单失败";

    private String lockKey = "lock_order:";
    private String ifOk = "OK";
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DriverInfoMapper driverInfoMapper;
    @Autowired
    private OrderRuleMirrorMapper orderRuleMirrorMapper;

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private SecretPhoneNumberService secretPhoneNumberService;

    @Autowired
    private ServicesConfig servicesConfig;

    @Autowired
    private CarInfoMapper carInfoMapper;


    @Override
    public ResponseResult grab(OrderRequest orderRequest) {
        log.info("OrderRequest={}", orderRequest);
        Integer orderId = orderRequest.getOrderId();
        Integer driverId =orderRequest.getDriverId();

        if(null != orderId && orderId != 0 && null != driverId && driverId != 0){
            DriverInfo driverInfo = driverInfoMapper.selectByPrimaryKey(driverId);
            if (null == driverInfo) {
                return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), DRIVER_IS_NULL);
            }
            OrderRuleMirror orderRuleMirror = orderRuleMirrorMapper.selectByPrimaryKey(orderId);
            String rule = orderRuleMirror.getRule();
            JSONObject ruleJson = JSONObject.fromObject(rule);
            JSONObject keyRuleJson = JSONObject.fromObject(ruleJson.get("keyRule"));
            Order order = orderMapper.selectByPrimaryKey(orderId);
            Integer cityCode = Integer.parseInt(keyRuleJson.get("cityCode").toString());
            Integer carType = Integer.parseInt(keyRuleJson.get("carLevelId").toString());

            int orderStatusInt = order.getStatus();
            if (orderStatusInt != OrderStatusEnum.STATUS_ORDER_START.getCode()) {
                return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), ORDER_ROBBED);
            }
            Integer useStatus = driverInfo.getUseStatus();
            if (null != useStatus && useStatus.intValue() == 0) {
                return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), DRIVER_OUT_OF_SERVICE);
            }
            String driverPhone = driverInfo.getPhoneNumber();
            String passengerPhone;
            if(order.getOrderType() ==  OrderEnum.ORDER_TYPE_OTHER.getCode()){
                passengerPhone = order.getOtherPhone();
            }else{
                passengerPhone = order.getPassengerPhone();
            }
            ResponseResult responseResult = orderStatusProcessing(orderId,driverInfo.getWorkStatus(),driverId,
                    driverInfo.getCarId(),driverPhone,order.getOrderStartTime(),passengerPhone,cityCode,carType);
            if(responseResult.getCode() == BusinessInterfaceStatus.SUCCESS.getCode()){
                return ResponseResult.success("抢单成功");
            }else{
                return responseResult;
            }
        }
        return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), ORDER_ID_IS_NULL);
    }

    /**
     *订单状态处理
     */
    @Transactional(rollbackFor=Exception.class)
    public ResponseResult orderStatusProcessing(Integer orderId,Integer workStatus,Integer driverId,Integer carId,String driverPhone,Date startTime,
                                                String passengerPhone,Integer cityCode,Integer carType){
        JSONObject json = new JSONObject();
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if (null != workStatus && workStatus.intValue() == DriverInfoConst.WORK_START) {
            String lock = (lockKey + orderId).intern();
            String uuid = UUID.randomUUID().toString();
            BoundValueOperations<String, String> lockRedis;
            lockRedis = redisTemplate.boundValueOps(lock);
            Boolean lockBoolean = lockRedis.setIfAbsent(uuid);
            if (lockBoolean) {
                lockRedis.expire(15L, TimeUnit.SECONDS);
            } else {
                log.error(GRAB_FAILURE);
                return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), GRAB_FAILURE);
            }
            ResponseResult responseResult = updateMap(order,cityCode,carType);
            if(responseResult.getCode() != BusinessInterfaceStatus.SUCCESS.getCode()){
                return responseResult;
            }
            CarInfo carInfo = carInfoMapper.selectByPrimaryKey(carId);
            Integer status = OrderStatusEnum.STATUS_DRIVER_ACCEPT.getCode();
            order.setDriverId(driverId);
            order.setDriverPhone(driverPhone);
            order.setStatus(status);
            order.setCarId(carId);
            order.setPlateNumber(carInfo.getPlateNumber());

            driverPhone = EncriptUtil.decryptionPhoneNumber(driverPhone);
            passengerPhone = EncriptUtil.decryptionPhoneNumber(passengerPhone);
            //JSONObject phoneBoundJson = phoneBoundResponse(startTime,driverPhone,passengerPhone);
            //if(StringUtils.isNotBlank(phoneBoundJson.toString())){
            //    String merchantId = phoneBoundJson.get("axbSubsId").toString();
            //    if(StringUtils.isNotBlank(merchantId)){
            //        order.setMerchantId(merchantId);
            //        order.setMappingNumber(phoneBoundJson.get("axbSecretNo").toString());
            //    }
            //}
            order.setDriverGrabTime(new Date());
            orderMapper.updateByPrimaryKeySelective(order);
            redisTemplate.delete(lock);
        }else{
            return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), GRAB_FAILURE);
        }
        return ResponseResult.success(json);
    }

    public ResponseResult updateMap(Order order,Integer cityCode,Integer carType){
        JSONObject json = new JSONObject();
        JSONObject mapJson = new JSONObject();
        mapJson.put("orderId",order.getId());
        mapJson.put("customerDeviceId",order.getDeviceCode());
        mapJson.put("type",order.getServiceType());
        mapJson.put("orderCity",cityCode);
        mapJson.put("vehicleType",carType);
        mapJson.put("status",order.getStatus());
        mapJson.put("startLongitude",order.getStartLongitude());
        mapJson.put("startLatitude",order.getStartLatitude());
        mapJson.put("startName",order.getStartAddress());
        mapJson.put("endLongitude",order.getEndLongitude());
        mapJson.put("endLatitude",order.getEndLatitude());
        mapJson.put("endName",order.getEndAddress());
        mapJson.put("userLongitude",order.getUserLongitude());
        mapJson.put("userLatitude",order.getUserLatitude());

        try {
            String dataMapJson = getData(servicesConfig.getMapAddress()+"/order",mapJson);
            JSONObject jsStr = JSONObject.fromObject(dataMapJson);
            Object code = jsStr.get("code");
            Object message = jsStr.get("message");
            Object data = jsStr.get("data");
            if(null != code && "".equals(code)) {
                int successCode = BusinessInterfaceStatus.SUCCESS.getCode();
                int returnCode = Integer.parseInt(code.toString());
                if(successCode != returnCode) {
                    return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), message.toString());
                }else{
                    json.put("data",data);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseResult.success(json);
    }

    /**
     * 绑定手机号
     * @param startTime
     * @param driverPhone
     * @param passengerPhone
     * @return
     */
    public JSONObject phoneBoundResponse(Date startTime,String driverPhone,String passengerPhone){
        JSONObject json = new JSONObject();
        try {
            Calendar expiration = Calendar.getInstance();
            expiration.setTime(startTime);
            expiration.add(Calendar.DAY_OF_YEAR, 1);

            BindAxbResponse axbResponse = secretPhoneNumberService.bindAxb(driverPhone, passengerPhone,expiration.getTime());

            if (ifOk.equals(axbResponse.getCode())) {
                String axbSubsId = axbResponse.getSecretBindDTO() == null ? null
                        : axbResponse.getSecretBindDTO().getSubsId();
                String axbSecretNo = axbResponse.getSecretBindDTO() == null ? null
                        : axbResponse.getSecretBindDTO().getSecretNo();
                json.put("axbSubsId",axbSubsId);
                json.put("axbSecretNo",axbSecretNo);
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return json;
    }
    /**
     *
     * @param Url
     * @return
     */
    public String getData(String Url,JSONObject request)  {
        BaseResponse baseResponse = restTemplate.postForObject(Url, request, BaseResponse.class);
        return JSONObject.fromObject(baseResponse).toString();
    }
}
