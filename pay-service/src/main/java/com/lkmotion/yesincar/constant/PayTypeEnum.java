package com.lkmotion.yesincar.constant;

import com.lkmotion.yesincar.constatnt.CodeEnum;
import lombok.Getter;

/**
 * 
 * @author chaopengfei
 */
@Getter
public enum PayTypeEnum implements CodeEnum {
    WXPAY(1, "微信支付"),
    BALANCE(2, "余额支付"),
    SYSTEM(3, "平台账户"),
    ALIPAY(4, "支付宝支付"),

	EX(999,"none");
    private final int code;
    private final String value;

    PayTypeEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

}