package com.lkmotion.yesincar.service;

import java.math.BigDecimal;

/**
 * 计价服务
 *
 * @author ZhuBin
 * @date 2018/8/14
 */
public interface ValuationService {

    /**
     * 计算预估价格
     *
     * @param orderId 订单id
     * @return 预估价格
     * @throws Exception 异常信息
     */
    BigDecimal calcForecastPrice(Integer orderId) throws Exception;

    /**
     * 结算订单价格
     *
     * @param orderId 订单id
     * @return 价格
     * @throws Exception 异常信息
     */
    BigDecimal calcSettlementPrice(Integer orderId) throws Exception;

}
