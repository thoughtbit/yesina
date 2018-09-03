package com.lkmotion.yesincar.service;

import com.lkmotion.yesincar.dto.OrderOtherPrice;
import com.lkmotion.yesincar.dto.OrderPrice;
import com.lkmotion.yesincar.dto.ResponseResult;
import com.lkmotion.yesincar.entity.Order;
import com.lkmotion.yesincar.request.OrderRequest;

/**
 * 功能描述
 *
 * @author liheng
 * @date 2018/8/25
 */

public interface OrderService {

    /**
     * 预估订单创建与修改
     * @param request
     * @return
     */
    ResponseResult<OrderPrice> forecastOrderCreateUpdate(OrderRequest request) throws Exception;

    /**
     * 叫车
     * @param request
     * @return
     */
    ResponseResult callCar(OrderRequest request) throws Exception;

    /**
     * 修改订单
     * @param order
     * @return
     */
    ResponseResult updateOrder(Order order) throws Exception;

    /**
     * 其它费用
     * @param request
     * @return
     */
    ResponseResult<OrderOtherPrice> otherPriceBalance(OrderRequest request) throws Exception;

    /**
     * 订单改派
     * @param request
     * @return
     */
    ResponseResult reassignmentOrder(OrderRequest request) throws Exception;

}
