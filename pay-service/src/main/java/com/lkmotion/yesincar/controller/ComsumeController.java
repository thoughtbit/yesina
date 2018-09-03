package com.lkmotion.yesincar.controller;

import com.lkmotion.yesincar.dto.ResponseResult;
import com.lkmotion.yesincar.request.FreezeRequest;
import com.lkmotion.yesincar.service.FreezeService;
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
@RequestMapping("/consume")
public class ComsumeController {

    @Autowired
    private RefundService refundService;

    @Autowired
    private FreezeService freezeService;

    /**
     * 冻结
     * @param freezeRequest
     * @return
     */
    @PostMapping("/freeze")
    public ResponseResult freeze(@RequestBody FreezeRequest freezeRequest){

        Integer yid = freezeRequest.getYid();
        Integer orderId = freezeRequest.getOrderId();
        Double price = freezeRequest.getPrice();

        return freezeService.freeze(yid,orderId,price);

    }

    /**
     * 解冻
     * @param freezeRequest
     * @return
     */
    @PostMapping("/unFreeze")
    public ResponseResult unFreeze(@RequestBody FreezeRequest freezeRequest){

        Integer yid = freezeRequest.getYid();
        Integer orderId = freezeRequest.getOrderId();
        Double price = freezeRequest.getPrice();

        return freezeService.unFreeze(yid,orderId);
    }

    /**
     * 扣款
     * @param freezeRequest
     * @return
     */
    @PostMapping("/pay")
    public ResponseResult pay(@RequestBody FreezeRequest freezeRequest){

        Integer yid = freezeRequest.getYid();
        Integer orderId = freezeRequest.getOrderId();
        Double price = freezeRequest.getPrice();

        return freezeService.pay(yid,orderId,price);
    }
}
