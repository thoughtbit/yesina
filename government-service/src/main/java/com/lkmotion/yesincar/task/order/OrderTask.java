package com.lkmotion.yesincar.task.order;

import com.lkmotion.yesincar.constatnt.IdentityEnum;
import com.lkmotion.yesincar.constatnt.OrderStatusEnum;
import com.lkmotion.yesincar.dto.order.OrderCancelDto;
import com.lkmotion.yesincar.dto.order.OrderDto;
import com.lkmotion.yesincar.exception.ParameterException;
import com.lkmotion.yesincar.mapper.OrderMapper;
import com.lkmotion.yesincar.task.AbstractSupervisionTask;
import com.yipin.data.upload.proto.OTIpcDef;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 * 订单数据上报
 *
 * @author ZhuBin
 * @date 2018/8/24
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class OrderTask extends AbstractSupervisionTask {

    private static final String ERR_EMPTY_ORDER_ID = "订单ID为空";
    private static final String ERR_EMPTY_ORDER = "订单为空";
    private static final String ERR_EMPTY_ORDER_CANCEL = "订单取消明细为空";

    @NonNull
    private OrderMapper orderMapper;

    /**
     * 监听到插入操作
     *
     * @param id 主键
     * @return 是否为合法上报数据
     */
    @Override
    public boolean insert(Integer id) {
        OrderDto order = findOrder(id);

        if (OrderStatusEnum.STATUS_ORDER_START.getCode() == order.getStatus()) {
            ipcType = OTIpcDef.IpcType.orderCreate;
            messageMap.put("OrderId", order.getOrderNumber());
            messageMap.put("DepartTime", formatDateTime(order.getStartTime(), DateTimePatternEnum.DateTime));
            messageMap.put("OrderTime", order.getOrderStartTime());
            messageMap.put("Departure", order.getStartAddress());
            messageMap.put("DepLongitude", toCoordinates(order.getStartLongitude()));
            messageMap.put("DepLatitude", toCoordinates(order.getStartLatitude()));
            messageMap.put("Destination", order.getStartAddress());
            messageMap.put("DestLongitude", toCoordinates(order.getEndLongitude()));
            messageMap.put("DestLatitude", toCoordinates(order.getEndLatitude()));
            messageMap.put("Encrypt", 1);
            messageMap.put("FareType", order.getRuleId());
            return true;
        }

        return false;
    }

    /**
     * 监听到更新操作
     *
     * @param id 主键
     * @return 是否为合法上报数据
     */
    @Override
    public boolean update(Integer id) {
        OrderDto order = findOrder(id);

        if (OrderStatusEnum.STATUS_DRIVER_ACCEPT.getCode() == order.getStatus()) {
            //订单请求成功，匹配到司机
            ipcType = OTIpcDef.IpcType.orderMatch;
            messageMap.put("OrderId", order.getOrderNumber());
            messageMap.put("Encrypt", 1);
            messageMap.put("LicenseId", order.getDrivingLicenceNumber());
            messageMap.put("DriverPhone", getPhoneNumber(IdentityEnum.DRIVER, order.getDriverId()));
            messageMap.put("VehicleNo", order.getPlateNumber());
            messageMap.put("DistributeTime", formatDateTime(order.getDriverGrabTime(), DateTimePatternEnum.DateTime));
            return true;
        } else if (1 == order.getIsCancel()) {
            //订单取消
            ipcType = OTIpcDef.IpcType.orderCancel;
            OrderCancelDto cancelDto = findCancelDetail(id);
            messageMap.put("OrderId", cancelDto.getOrderNumber());
            messageMap.put("CancelTime", formatDateTime(cancelDto.getCreateTime(), DateTimePatternEnum.DateTime));
            messageMap.put("Operator", 1 == cancelDto.getOperatorType() ? "1" : "3");
            messageMap.put("CancelTypeCode", getCancelTypeCode(cancelDto));
            return true;
        }

        return false;
    }

    /**
     * 监听到删除操作
     *
     * @param id 主键
     * @return 是否为合法上报数据
     */
    @Override
    public boolean delete(Integer id) {
        return false;
    }

    /**
     * 查找订单
     *
     * @param id 订单ID
     * @return 订单DTO
     */
    private OrderDto findOrder(Integer id) {
        if (id == null) {
            log.error(ERR_EMPTY_ORDER_ID);
            throw new ParameterException(ERR_EMPTY_ORDER_ID);
        }

        OrderDto dto = orderMapper.selectByOrderId(id);

        if (dto == null) {
            log.error(ERR_EMPTY_ORDER + ", order={}", id);
            throw new ParameterException(ERR_EMPTY_ORDER + ", order=" + id);
        }

        return dto;
    }

    /**
     * 查找取消的订单明细
     *
     * @param id 订单ID
     * @return 订单取消DTO
     */
    private OrderCancelDto findCancelDetail(Integer id) {
        if (id == null) {
            log.error(ERR_EMPTY_ORDER_ID);
            throw new ParameterException(ERR_EMPTY_ORDER_ID);
        }

        OrderCancelDto dto = orderMapper.selectCancelDetail(id);

        if (dto == null) {
            log.error(ERR_EMPTY_ORDER_CANCEL + ", order={}", id);
            throw new ParameterException(ERR_EMPTY_ORDER_CANCEL + ", order=" + id);
        }

        return dto;
    }

    /**
     * 获取撤销类型代码
     *
     * @param dto 订单取消DTO
     * @return 撤销类型代码
     */
    private String getCancelTypeCode(OrderCancelDto dto) {
        if (ObjectUtils.nullSafeEquals(dto.getOperatorType(), 1)) {
            //客户取消
            return ObjectUtils.nullSafeEquals(dto.getIsCharge(), 1) ? "4" : "1";
        } else {
            //客服平台取消
            return "3";
        }
    }
}
