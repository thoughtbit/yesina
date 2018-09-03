package com.lkmotion.yesincar.dto.valuation.charging;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.util.ObjectUtils;

/**
 * 基础计费
 *
 * @author ZhuBin
 * @date 2018/8/14
 */
@Data
public class BasicRule {
    /**
     * 基础价
     */
    private Double lowestPrice;

    /**
     * 起步价
     */
    private Double basePrice;

    /**
     * 包含公里数（公里）
     */
    private Double kilos;

    /**
     * 包含时长数（分钟）
     */
    private Double minutes;

    /**
     * 是否采用基础套餐的计费规则
     *
     * @return 采用基础套餐的计费规则为true，否则为false
     */
    @JsonIgnore
    public boolean isBasicCharging() {
        return !(ObjectUtils.nullSafeEquals(kilos, 0) && ObjectUtils.nullSafeEquals(minutes, 0));
    }
}
