package com.lkmotion.yesincar.constatnt;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 计价规则种类枚举
 *
 * @author ZhuBin
 * @date 2018/8/14
 */
@Getter
@AllArgsConstructor
public enum ChargingCategoryEnum implements CodeEnum {

    /**
     * 预估订单: 0
     */
    Forecast(0, "预约"),

    /**
     * 实时订单: 1
     */
    Settlement(1, "实际");

    private int code;
    private String name;
}
