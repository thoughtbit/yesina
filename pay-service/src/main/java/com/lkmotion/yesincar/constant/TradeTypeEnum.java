package com.lkmotion.yesincar.constant;

import com.lkmotion.yesincar.constatnt.CodeEnum;
import lombok.Getter;

/**
 * 
 * @author chaopengfei
 */
@Getter
public enum TradeTypeEnum implements CodeEnum {

    RECHARGE(1, "充值"),
    CONSUME(2, "消费"),
    REFUND(3,"退款"),

	EX(999,"none");
    private final int code;
    private final String value;

    TradeTypeEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

}