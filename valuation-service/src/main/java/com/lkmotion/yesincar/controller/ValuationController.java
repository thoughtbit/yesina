package com.lkmotion.yesincar.controller;

import com.lkmotion.yesincar.constatnt.BusinessInterfaceStatus;
import com.lkmotion.yesincar.dto.ResponseResult;
import com.lkmotion.yesincar.dto.valuation.PriceResult;
import com.lkmotion.yesincar.service.ValuationService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * 计价规则控制器
 *
 * @author ZhuBin
 * @date 2018/8/14
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/valuation")
public class ValuationController {

    private static final String ERR_EMPTY_ORDER_ID = "订单ID为空";
    private static final String ERR_CALC_FORECAST_PRICE = "订单预估价格计算错误";
    private static final String ERR_CALC_SETTLEMENT_PRICE = "订单结算价格计算";

    @NonNull
    private ValuationService valuationService;

    /**
     * 订单预估价格计算
     *
     * @param orderId 订单ID
     * @return 预估价格
     */
    @GetMapping({"/forecast", "/forecast/{orderId}"})
    public ResponseResult forecast(@PathVariable(value = "orderId", required = false) String orderId) {
        if (StringUtils.isEmpty(orderId)) {
            log.error(ERR_EMPTY_ORDER_ID);
            return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), ERR_EMPTY_ORDER_ID);
        }

        BigDecimal price;
        try {
            int id = NumberUtils.parseNumber(orderId, Integer.class);
            price = valuationService.calcForecastPrice(id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("{}：orderId={}", ERR_CALC_FORECAST_PRICE, orderId, e);
            return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), ERR_CALC_FORECAST_PRICE);
        }

        return ResponseResult.success(new PriceResult(Optional.ofNullable(price).orElse(BigDecimal.ZERO).doubleValue()));
    }

    /**
     * 订单结算价格计算
     *
     * @param orderId 订单ID
     * @return 结算价格
     */
    @GetMapping({"/settlement", "/settlement/{orderId}"})
    public ResponseResult settlement(@PathVariable(value = "orderId", required = false) String orderId) {
        if (StringUtils.isEmpty(orderId)) {
            log.error(ERR_EMPTY_ORDER_ID);
            return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), ERR_EMPTY_ORDER_ID);
        }

        BigDecimal price;
        try {
            int id = NumberUtils.parseNumber(orderId, Integer.class);
            price = valuationService.calcSettlementPrice(id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("{}：orderId={}", ERR_CALC_SETTLEMENT_PRICE, orderId, e);
            return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), ERR_CALC_SETTLEMENT_PRICE);
        }

        return ResponseResult.success(new PriceResult(Optional.ofNullable(price).orElse(BigDecimal.ZERO).doubleValue()));
    }

}
