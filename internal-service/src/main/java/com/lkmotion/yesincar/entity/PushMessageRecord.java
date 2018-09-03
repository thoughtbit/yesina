package com.lkmotion.yesincar.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author chaopengfei
 */
@Data
public class PushMessageRecord {
    private Integer id;

    private String source;

    private String jpushId;

    private int messageType;

    private String messageBody;

    private Integer sendResult;

    private String sendId;

    private Integer sendIdentity;

    private Date createTime;

    private Date updateTime;

}