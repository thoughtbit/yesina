package com.lkmotion.yesincar.controller;

import com.lkmotion.yesincar.dto.OrderOtherPrice;
import com.lkmotion.yesincar.dto.OrderPrice;
import com.lkmotion.yesincar.dto.ResponseResult;
import com.lkmotion.yesincar.entity.Order;
import com.lkmotion.yesincar.request.OrderDtoRequest;
import com.lkmotion.yesincar.request.OrderRequest;
import com.lkmotion.yesincar.service.OrderGrabService;
import com.lkmotion.yesincar.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 订单管理
 *
 * @author LiHeng
 **/
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderGrabService orderGrabService;

    /**
     * 预估
     * @param request
     * @return
     */
    @RequestMapping(value = "/forecast")
    public ResponseResult forecast(@RequestBody OrderRequest request){
        ResponseResult result = null;
        try {
            result = orderService.forecastOrderCreateUpdate(request);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("订单预估失败",e);
            result= ResponseResult.fail("订单预估失败");
        }
        return  result;
    }

    /**
     * 叫车
     * @param request
     * @return
     */
    @RequestMapping(value = {"/callCar"}, produces = "application/json; charset=utf-8")
    public ResponseResult callCar(@RequestBody OrderRequest request) {
        ResponseResult result = null;
        try {
            result = orderService.callCar(request);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("叫车失败",e);
            result= ResponseResult.fail("叫车失败");
        }
        return result;
    }

    /**
     * 派单、强派、修改订单
     * @param
     * @return
     */
    @RequestMapping(value = {"/updateOrder"}, produces = "application/json; charset=utf-8")
    public ResponseResult updateOrder(@RequestBody OrderDtoRequest orderDtoRequest){
        Order order = new Order();
        ResponseResult result = null;
        BeanUtils.copyProperties(orderDtoRequest,order);
        order.setId(orderDtoRequest.getOrderId());
        try {
            result = orderService.updateOrder(order);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("操作失败",e);
            result= ResponseResult.fail("操作失败");
        }
        return result;
    }

    /**
     * 抢单
     * @param request
     * @return
     */
    @RequestMapping(value = {"/grab"}, produces = "application/json; charset=utf-8")
    public ResponseResult grab(@RequestBody OrderRequest request) {
        ResponseResult<OrderPrice> result = null;
        try {
            result = orderGrabService.grab(request);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("抢单失败",e);
            result= ResponseResult.fail("抢单失败");
        }
        return result;
    }

    /**
     * 其它费用结算
     * @param request
     * @return
     */
    @RequestMapping(value = {"/otherPrice"}, produces = "application/json; charset=utf-8")
    public ResponseResult otherPriceBalance(@RequestBody OrderRequest request){
        ResponseResult<OrderOtherPrice> result = null;
        try {
            result = orderService.otherPriceBalance(request);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("其它费用操作失败",e);
            result= ResponseResult.fail("操作失败");
        }
        return  result;
    }
    /**
     * 订单改派
     * @param request
     * @return
     */
    @RequestMapping(value = {"/reassignment"}, produces = "application/json; charset=utf-8")
    public ResponseResult reassignmentOrder(@RequestBody OrderRequest request){
        ResponseResult result = null;
        try {
            result = orderService.reassignmentOrder(request);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("订单改派失败",e);
            result= ResponseResult.fail("订单改派失败");
        }
        return result;
    }

}
