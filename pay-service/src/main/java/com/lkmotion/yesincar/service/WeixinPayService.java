package com.lkmotion.yesincar.service;

import com.lkmotion.yesincar.dto.ResponseResult;
import com.lkmotion.yesincar.request.PayResultRequest;
import com.lkmotion.yesincar.response.WeixinPayResponse;

/**
 * @author chaopengfei
 * @date 2018/8/17
 */
public interface WeixinPayService {

    WeixinPayResponse prePay(Integer yid , Double capital, Double giveFee, String source, Integer rechargeType, Integer orderId);

    Boolean callback(String reqXml);

    ResponseResult payResult(PayResultRequest payResultRequest);
}
