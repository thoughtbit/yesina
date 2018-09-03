package com.lkmotion.yesincar.utils;

/**
 * 功能描述
 *
 * @author liheng
 * @date 2018/8/17
 */
public enum Distance {
    DISTANCE(260000, "distance");

    private final int code;
    private final String value;

    Distance(int code, String value) {
        this.code = code;
        this.value = value;
    }

    /**

     * @Title: getCode
     * @Description: 获得状态码
     * @return 状态码
     */
    public int getCode(){
        return code;
    }

    /**
     *
     * @Title: getValue
     * @Description: 获得异常状态信息
     * @return 异常状态信息
     */
    public String getValue(){
        return value;
    }

}
