package com.lkmotion.yesincar.mapper;

import com.lkmotion.yesincar.dto.order.OrderCancelDto;
import com.lkmotion.yesincar.dto.order.OrderDto;

/**
 * 订单Mapper
 *
 * @author ZhuBin
 * @date 2018/8/24
 */
public interface OrderMapper {

    /**
     * 根据主键查找Order
     *
     * @param id 订单主键
     * @return 订单DTO
     */
    OrderDto selectByOrderId(Integer id);

    /**
     * 查找派单成功的订单
     *
     * @param id 订单主键
     * @return 订单DTO
     */
    OrderDto selectBeginningOrder(Integer id);

    /**
     * 查找订单取消信息
     *
     * @param id 订单主键
     * @return 订单取消DTO
     */
    OrderCancelDto selectCancelDetail(Integer id);

}
