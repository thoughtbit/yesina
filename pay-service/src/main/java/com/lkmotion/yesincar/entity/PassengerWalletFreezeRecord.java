package com.lkmotion.yesincar.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author chaopengfei
 */
@Data
public class PassengerWalletFreezeRecord {
    private Integer id;

    private Integer passengerInfoId;

    private Double freezeCapital;

    private Double freezeGiveFee;

    private Integer orderId;

    private Integer freezeStatus;

    private Date createTime;

    private Date updateTime;

}