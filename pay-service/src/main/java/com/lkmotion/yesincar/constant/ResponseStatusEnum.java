package com.lkmotion.yesincar.constant;

import com.lkmotion.yesincar.constatnt.CodeEnum;
import lombok.Getter;

/**
 * 
 * @author chaopengfei
 */
@Getter
public enum ResponseStatusEnum implements CodeEnum {
    /**冻结100-199*/
    NOT_ALLOW_RE_FREEZE(100, "重复冻结"),
    FREEZE_NOT_ENOUGH(101, "冻结余额不足"),
    FREEZE_RECORD_EMPTY(102,"冻结记录为空"),

    /**钱包200-299*/
    PASSENGER_WALLET_EMPTY(200,"钱包为空"),


	EX(999,"none");
    private final int code;
    private final String value;

    ResponseStatusEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

}