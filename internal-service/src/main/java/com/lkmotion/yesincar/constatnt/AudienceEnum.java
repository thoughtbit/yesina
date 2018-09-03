package com.lkmotion.yesincar.constatnt;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author chaopengfei
 */
@Getter
@AllArgsConstructor
public enum AudienceEnum implements CodeEnum {
    /**
     * 别名
     */
    ALIAS(1, "别名"),

    /**
     * 注册Id
     */
    REGISTRATION_ID(2, "注册Id"),

    /**
     * 其他
     */
    OTHER(9, "其他");

    private final int code;
    private final String value;
}
