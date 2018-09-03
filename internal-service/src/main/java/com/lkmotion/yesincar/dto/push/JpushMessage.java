package com.lkmotion.yesincar.dto.push;

import lombok.Data;

/**
 * @Author: chaopengfei
 */
@Data
public class JpushMessage {

    private int messageType;

    private String title;

    private String messageBody;
}