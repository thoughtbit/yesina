package com.lkmotion.yesincar.constant;

import com.lkmotion.yesincar.constatnt.CodeEnum;
import lombok.Getter;

/**
 * 
 * @author chaopengfei
 */
@Getter
public enum ChangeStatusEnum implements CodeEnum {
    ADD(1, "加"),
    SUB(-1, "减"),

	EX(999,"none");
    private final int code;
    private final String value;

    ChangeStatusEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

}