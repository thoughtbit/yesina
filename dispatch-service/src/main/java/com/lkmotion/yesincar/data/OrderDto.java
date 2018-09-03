package com.lkmotion.yesincar.data;

import com.lkmotion.yesincar.entity.Order;

/**
 * @author dulin
 * @date 2018/8/30
 */
public class OrderDto extends Order {
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    private Integer orderId;
}
