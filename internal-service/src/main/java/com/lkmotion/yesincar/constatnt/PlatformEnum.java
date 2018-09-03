package com.lkmotion.yesincar.constatnt;

import lombok.Getter;

/**
 * @Author: chaopengfei
 */
@Getter
public enum PlatformEnum {
    IOS(1, "iOS"),
    ANDROID(2, "Android"),
    OTHER(9, "其他系统");

    private final int code;
    private final String value;

    PlatformEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
