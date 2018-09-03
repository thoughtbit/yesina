package com.lkmotion.yesincar.controller;

import com.lkmotion.yesincar.constant.WeixinConfig;
import com.lkmotion.yesincar.dto.ResponseResult;
import com.lkmotion.yesincar.request.PayRequest;
import com.lkmotion.yesincar.request.PayResultRequest;
import com.lkmotion.yesincar.response.WeixinPayResponse;
import com.lkmotion.yesincar.service.WeixinPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author chaopengfei
 * @date 2018/8/16
 */
@RestController
@RequestMapping("/weixinPay")
@Slf4j
public class WeixinPayContorller {

    @Autowired
    private WeixinPayService weixinPayService;

    @PostMapping(value = "/pretreatment")
    public ResponseResult pretreatment(@RequestBody PayRequest payRequest){
        Integer yid = payRequest.getYid();
        Double capital = payRequest.getCapital();
        Double giveFee = payRequest.getGiveFee();
        String source = payRequest.getSource();
        Integer rechargeType = payRequest.getRechargeType();
        Integer orderId = payRequest.getOrderId();
        WeixinPayResponse response = weixinPayService.prePay(yid,capital,giveFee,source,rechargeType,orderId);
        return ResponseResult.success(response);

    }

    /**
     * 微信call back
     *
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/callback", produces = "application/xml; charset=utf-8")
    public String handlePayResult(@RequestBody String reqXml) {

        try {
            log.info("微信回调："+reqXml);
            Boolean flag = weixinPayService.callback(reqXml);
            if (!flag){
                log.info("微信回调失败");
                return WeixinConfig.RESPONSE_FAIL;
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.warn("call back err", e);
            return WeixinConfig.RESPONSE_FAIL;
        }
        return WeixinConfig.RESPONSE_SUCCESS;
    }

    @GetMapping("/payResult")
    public ResponseResult payResult(PayResultRequest payResultRequest){
        return weixinPayService.payResult(payResultRequest);
    }

}
