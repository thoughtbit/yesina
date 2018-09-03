package com.lkmotion.yesincar.service.impl;

import com.lkmotion.yesincar.constatnt.ChargingCategoryEnum;
import com.lkmotion.yesincar.dao.OrderDao;
import com.lkmotion.yesincar.dto.DriveMeter;
import com.lkmotion.yesincar.dto.valuation.charging.Rule;
import com.lkmotion.yesincar.dto.valuation.discount.DiscountPrice;
import com.lkmotion.yesincar.entity.Order;
import com.lkmotion.yesincar.entity.OrderRulePrice;
import com.lkmotion.yesincar.entity.OrderRulePriceDetail;
import com.lkmotion.yesincar.service.ValuationService;
import com.lkmotion.yesincar.task.ValuationRequestTask;
import com.lkmotion.yesincar.task.ValuationTask;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 计价服务
 *
 * @author ZhuBin
 * @date 2018/8/14
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ValuationServiceImpl implements ValuationService {

    @NonNull
    private OrderDao orderDao;

    @NonNull
    private ValuationRequestTask requestTask;

    @NonNull
    private ValuationTask valuationTask;

    @Override
    public BigDecimal calcForecastPrice(Integer orderId) throws Exception {
        return calcPrice(orderId, ChargingCategoryEnum.Forecast);
    }

    @Override
    public BigDecimal calcSettlementPrice(Integer orderId) throws Exception {
        return calcPrice(orderId, ChargingCategoryEnum.Settlement);
    }

    /**
     * 计算价格
     *
     * @param orderId          订单ID
     * @param chargingCategory 计价规则种类
     * @return 价格
     * @throws Exception 异常
     */
    private BigDecimal calcPrice(Integer orderId, ChargingCategoryEnum chargingCategory) throws Exception {
        Order order = orderDao.selectByOrderId(orderId);
        Rule rule = requestTask.requestRule(orderId);

        //定义驾驶参数
        DriveMeter driveMeter = new DriveMeter(chargingCategory);
        driveMeter.setOrder(order).setRule(rule).setRequestTask(requestTask);
        if (ChargingCategoryEnum.Forecast == chargingCategory) {
            driveMeter.setRoute(requestTask.requestRoute(driveMeter));
        } else {
            driveMeter.setDistance(requestTask.requestDistance(driveMeter));
        }

        //分段计价任务
        CompletableFuture<List<OrderRulePriceDetail>> calcPriceDetailFuture = valuationTask.calcPriceDetail(driveMeter);

        //基础计价任务
        CompletableFuture<OrderRulePrice> calcPriceFuture = valuationTask.calcPrice(driveMeter);

        //计算最终价格
        BigDecimal price = calcPriceDetailFuture.thenCombine(calcPriceFuture, (details, master) -> {
            //是否采用基础套餐的计费规则
            if (rule.getBasicRule().isBasicCharging()) {
                master.setRestDistance(0D);
                master.setRestDistancePrice(0D);
                master.setRestDuration(0D);
                master.setRestDurationPrice(0D);

                master.setPath(Math.max(0, master.getTotalDistance() - master.getBaseKilo()));
                master.setPathPrice(master.getPerKiloPrice() * master.getPath());
                master.setDuration(Math.max(0, master.getTotalTime() - master.getBaseMinute()));
                master.setDurationPrice(master.getPerMinutePrice() * master.getDuration());
            } else {
                //计算时间段外的价格
                master.setRestDistance(Math.max(0, master.getTotalDistance() - details.stream().mapToDouble(OrderRulePriceDetail::getDistance).sum()));
                master.setRestDistancePrice(master.getRestDistance() * master.getPerKiloPrice());
                master.setRestDuration(Math.max(0, master.getTotalTime() - details.stream().mapToDouble(OrderRulePriceDetail::getDuration).sum()));
                master.setRestDurationPrice(master.getRestDuration() * master.getPerMinutePrice());

                master.setPath(master.getTotalDistance());
                master.setPathPrice(master.getRestDistancePrice() + details.stream().mapToDouble(OrderRulePriceDetail::getDistancePrice).sum());
                master.setDuration(master.getTotalTime());
                master.setDurationPrice(master.getRestDurationPrice() + details.stream().mapToDouble(OrderRulePriceDetail::getTimePrice).sum());
            }

            //计算价格合计
            double totalPrice = master.getBasePrice() + master.getNightPrice() + master.getBeyondPrice() + master.getPathPrice() + master.getDurationPrice();

            //最低消费补足
            master.setSupplementPrice(0D);
            if (totalPrice < master.getLowestPrice()) {
                master.setSupplementPrice(Math.max(0, master.getLowestPrice() - totalPrice));
                totalPrice = master.getLowestPrice();
            }

            //动态调价
            master.setDynamicDiscount(0D);
            master.setDynamicDiscountRate(0D);
            DiscountPrice discount = valuationTask.calcDiscount(driveMeter);
            if (null != discount) {
                master.setDynamicDiscountRate(discount.getDiscount());
                master.setDynamicDiscount(Math.min(discount.getDiscountMaxPrice(), discount.getDiscount() * totalPrice));
            }

            //计算最终价格
            totalPrice = Math.max(0, totalPrice - master.getDynamicDiscount());
            master.setTotalPrice(totalPrice);

            return BigDecimal.valueOf(master.getTotalPrice());
        }).join();

        //更新DB
        OrderRulePrice rulePrice = calcPriceFuture.join();
        List<OrderRulePriceDetail> details = calcPriceDetailFuture.join();
        valuationTask.updateToDb(chargingCategory, rulePrice, details);

        return price.setScale(2, RoundingMode.DOWN);
    }

}
