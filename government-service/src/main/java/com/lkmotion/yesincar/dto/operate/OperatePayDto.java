package com.lkmotion.yesincar.dto.operate;

import lombok.Data;

import java.util.Date;

/**
 * @author dulin
 * @date 2018/8/31
 */
@Data
public class OperatePayDto {
    private String orderId;
    private String onArea;
    private String licenseId;
    private Integer fareType;
    private String vehicleNo;
    /**
     * 预计上车时间
     */
    private Date bookDepTime;
    private String depLongitude;
    private String depLatitude;
    private Date depTime;
    private String destLongitude;
    private String destlatitude;
    private Date destTime;
    private Double driverMile;
    private Integer driverTime;
    private Double factPrice;
    private Double farUpPrice;
    private Double otherUpPrice;
    private Integer payState;
    private Integer invoiceStatus;

    private String orderNumber;
    private String cityCode;
    private String drivingLicenceNumber;
    private Integer ruleId;
    private String plateNumber;
    private Date orderStartTime;
    private String receivePassengerLongitude;
    private String receivePassengerLatitudu;
    private Date receivePassengerTime;
    private String passengerGetoffLongitude;
    private String passengerGetoffLatitude;
    private Date passengerGetoffTime;
    private Double totalDistance;
    private Double totalTime;
    private Double totalPrice;
    private Double beyondPrice;
    private Integer invoiceType;
    private Double roadPrice;
    private Double parkingPrice;
    private Double otherPrice;
    private Double cancelprice;
}
