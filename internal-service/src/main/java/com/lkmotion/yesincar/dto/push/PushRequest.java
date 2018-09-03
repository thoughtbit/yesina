package com.lkmotion.yesincar.dto.push;

import lombok.Data;

/**
 * @Author: chaopengfei
 */
@Data
public class PushRequest {

    private String sendId;

    private Integer sendIdentity;

    private int acceptIdentity ;

    private String acceptId;

    private int messageType;

    private String title;

    private String messageBody;

    private Integer messageChannel;

    public PushRequest() {
    }
}