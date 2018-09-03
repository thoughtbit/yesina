package com.lkmotion.yesincar.service;

/**
 * @author chaopengfei
 * @date 2018/8/21
 */
public interface RefundService {

    void refund(Integer yid ,Integer orderId,Double refundPrice);
}
