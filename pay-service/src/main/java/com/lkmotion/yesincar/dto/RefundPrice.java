package com.lkmotion.yesincar.dto;

import lombok.Data;

/**
 * @author chaopengfei
 * @date 2018/8/21
 */
@Data
public class RefundPrice {
    Double refundCapital;
    Double refundGiveFee;

    public RefundPrice(Double refundCapital, Double refundGiveFee) {
        this.refundCapital = refundCapital;
        this.refundGiveFee = refundGiveFee;
    }
}
