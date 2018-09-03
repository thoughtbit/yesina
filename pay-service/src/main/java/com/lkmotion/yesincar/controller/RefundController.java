package com.lkmotion.yesincar.controller;

import com.lkmotion.yesincar.dto.ResponseResult;
import com.lkmotion.yesincar.request.RefundRequest;
import com.lkmotion.yesincar.service.RefundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chaopengfei
 * @date 2018/8/21
 */
@RestController
@RequestMapping("/refund")
public class RefundController {

    @Autowired
    private RefundService refundService;

    /**
     * 订单退款
     * @param refundRequest
     * @return
     */
    @PostMapping("/order")
    public ResponseResult refund(@RequestBody RefundRequest refundRequest){

        Integer yid = refundRequest.getYid();
        Integer orderId = refundRequest.getOrderId();
        Double refundPrice = refundRequest.getRefundPrice();

        refundService.refund(yid,orderId,refundPrice);

        return ResponseResult.success("");
    }
}
