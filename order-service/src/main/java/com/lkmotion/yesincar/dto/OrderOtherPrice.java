package com.lkmotion.yesincar.dto;

import lombok.Data;

/**
 * 功能描述
 *
 * @author liheng
 * @date 2018/8/25
 */
@Data
public class OrderOtherPrice {
    private Integer orderId;
    private Integer passengerId;
    private Double totalPrice;
}
