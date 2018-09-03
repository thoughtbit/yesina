package com.lkmotion.yesincar.constatnt;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Li---Heng
 * 订单状态
 */
@Getter
@AllArgsConstructor
public enum OrderEnum implements CodeEnum {
    ORDER_TYPE_OTHER(2,"他人订单");

    private int code;
    private String value;
}
