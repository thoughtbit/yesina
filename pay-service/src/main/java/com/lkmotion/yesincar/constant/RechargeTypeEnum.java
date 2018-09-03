package com.lkmotion.yesincar.constant;

import com.lkmotion.yesincar.constatnt.CodeEnum;
import lombok.Getter;

/**
 * 
 * @author chaopengfei
 */
@Getter
public enum RechargeTypeEnum implements CodeEnum {
    CHARGE(1, "仅充值"),
    CONSUME(2, "充值消费"),

	EX(999,"none");
    private final int code;
    private final String value;

    RechargeTypeEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

}