package com.lkmotion.yesincar.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author chaopengfei
 */
@Data
public class Sms {

    private Integer id;

    private String passengerPhoneNumber;

    private String smsContent;

    private Date sendTime;

    private String operator;

    private Integer sendFlag;

    private Integer sendNumber;

    private Date createTime;

}