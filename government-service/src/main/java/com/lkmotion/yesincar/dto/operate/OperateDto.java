package com.lkmotion.yesincar.dto.operate;

import lombok.Data;

import java.util.Date;

/**
 * 经营DTO
 *
 * @author ZhuBin
 * @date 2018/8/30
 */
@Data
public class OperateDto {
    /**
     * 订单编号
     */
    private String orderNumber;

    /**
     * 机动车驾驶证编号
     */
    private String drivingLicenceNumber;

    /**
     * 运价类型编码
     */
    private Integer ruleId;

    /**
     * 车辆号码
     */
    private String plateNumber;

    /**
     * 车辆出发经度
     */
    private String receivePassengerLongitude;

    /**
     * 车辆出发纬度
     */
    private String receivePassengerLatitude;

    /**
     * 上车时间
     */
    private Date receivePassengerTime;

    /**
     * 车辆到达经度
     */
    private String passengerGetoffLongitude;

    /**
     * 车辆到达维度
     */
    private String passengerGetoffLatitude;

    /**
     * 下车时间
     */
    private Date passengerGetoffTime;

    /**
     * 载客里程
     */
    private Double totalDistance;

    /**
     * 载客时间
     */
    private Double totalTime;

    /**
     * 订单状态
     */
    private Integer status;

    /**
     *经营上线时间
     */
    private Date workStart;

    /**
     * 经营下线时间
     */
    private Date workEnd;

    /**
     * 司机工作状态
     */
    private Integer workStatus;

}
