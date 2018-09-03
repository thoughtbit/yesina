package com.lkmotion.yesincar.dto.valuation.charging;

import lombok.Data;

import java.util.Date;

/**
 * 夜间服务费
 *
 * @author ZhuBin
 * @date 2018/8/14
 */
@Data
public class NightRule {

    /**
     * 夜间时间段 - 开始
     */
    private Date start;

    /**
     * 夜间时间段 - 结束
     */
    private Date end;

    /**
     * 超公里加收单价（元/公里）
     */
    private Double perKiloPrice;

    /**
     * 超时间加收单价（元/分钟）
     */
    private Double perMinutePrice;
}
