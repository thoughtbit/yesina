package com.lkmotion.yesincar.service;

import java.util.Map;

/**
 * @author chaopengfei
 */
public interface AlipayService {

    String prePay(Integer yid , Double capital, Double giveFee, String source,Integer rechargeType,Integer orderId);

    boolean callback(Map<String, String> params);

    Boolean checkAlipaySign(Map<String, String> params);

}
