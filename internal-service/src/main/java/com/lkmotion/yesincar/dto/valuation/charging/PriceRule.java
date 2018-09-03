package com.lkmotion.yesincar.dto.valuation.charging;

import lombok.Data;

import java.util.List;

/**
 * 计费方法
 *
 * @author ZhuBin
 * @date 2018/8/14
 */
@Data
public class PriceRule {

    /**
     * 超公里单价（元/公里）
     */
    private Double perKiloPrice;

    /**
     * 超时间单价（元/分钟）
     */
    private Double perMinutePrice;

    /**
     * 分段计时规则
     */
    private List<TimeRule> timeRules;
}
