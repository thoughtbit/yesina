package com.lkmotion.yesincar.dto.valuation.charging;

import lombok.Data;

/**
 * 远途服务费
 *
 * @author ZhuBin
 * @date 2018/8/14
 */
@Data
public class BeyondRule {

    /**
     * 远途起算公里（公里）
     */
    private Double startKilo;

    /**
     * 远途单价（元/公里）
     */
    private Double perKiloPrice;
}
