package com.lkmotion.yesincar.constant;

import com.lkmotion.yesincar.constatnt.CodeEnum;
import lombok.Getter;

/**
 * 
 * @author chaopengfei
 */
@Getter
public enum PayEnum implements CodeEnum {

	/**
     * 已支付
     */
    PAID(1, "已支付"),
	/**
     *
     */
    UN_PAID(0, "未支付"),


	EX(999,"none");
    private final int code;
    private final String value;

    PayEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

}