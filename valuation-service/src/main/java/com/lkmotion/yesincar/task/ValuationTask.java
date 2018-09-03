package com.lkmotion.yesincar.task;

import com.lkmotion.yesincar.constatnt.ChargingCategoryEnum;
import com.lkmotion.yesincar.dao.DynamicDiscountRuleDao;
import com.lkmotion.yesincar.dao.OrderRulePriceDao;
import com.lkmotion.yesincar.dao.OrderRulePriceDetailDao;
import com.lkmotion.yesincar.dto.DriveMeter;
import com.lkmotion.yesincar.dto.TimeMeter;
import com.lkmotion.yesincar.dto.valuation.charging.KeyRule;
import com.lkmotion.yesincar.dto.valuation.charging.Rule;
import com.lkmotion.yesincar.dto.valuation.discount.DiscountCondition;
import com.lkmotion.yesincar.dto.valuation.discount.DiscountPrice;
import com.lkmotion.yesincar.entity.Order;
import com.lkmotion.yesincar.entity.OrderRulePrice;
import com.lkmotion.yesincar.entity.OrderRulePriceDetail;
import com.lkmotion.yesincar.util.TimeSlice;
import com.lkmotion.yesincar.util.UnitConverter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * 计价服务异步任务
 *
 * @author ZhuBin
 * @date 2018/8/14
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class ValuationTask {

    @NonNull
    private OrderRulePriceDao orderRulePriceDao;

    @NonNull
    private OrderRulePriceDetailDao orderRulePriceDetailDao;

    @NonNull
    private DynamicDiscountRuleDao dynamicDiscountRuleDao;

    /**
     * 计算基本价格
     *
     * @param driveMeter 行驶信息
     * @return 基本价格
     */
    @Async
    public CompletableFuture<OrderRulePrice> calcPrice(DriveMeter driveMeter) {
        OrderRulePrice rulePrice = new OrderRulePrice();

        Rule rule = driveMeter.getRule();
        Order order = driveMeter.getOrder();

        //key信息
        rulePrice.setOrderId(order.getId());
        rulePrice.setCategory(driveMeter.getChargingCategoryEnum().getCodeAsString());
        rulePrice.setTotalDistance(UnitConverter.meterToKilo(driveMeter.getTotalDistance()));
        rulePrice.setTotalTime(UnitConverter.secondToMinute(driveMeter.getTotalTime()));
        rulePrice.setCityCode(rule.getKeyRule().getCityCode());
        rulePrice.setCityName(rule.getKeyRule().getCityName());
        rulePrice.setServiceTypeId(rule.getKeyRule().getServiceTypeId());
        rulePrice.setServiceTypeName(rule.getKeyRule().getServiceTypeName());
        rulePrice.setChannelId(rule.getKeyRule().getChannelId());
        rulePrice.setChannelName(rule.getKeyRule().getChannelName());
        rulePrice.setCarLevelId(rule.getKeyRule().getCarLevelId());
        rulePrice.setCarLevelName(rule.getKeyRule().getCarLevelName());

        //基础价格
        rulePrice.setBasePrice(rule.getBasicRule().getBasePrice());
        rulePrice.setBaseKilo(rule.getBasicRule().getKilos());
        rulePrice.setBaseMinute(rule.getBasicRule().getMinutes());
        rulePrice.setLowestPrice(rule.getBasicRule().getLowestPrice());
        rulePrice.setPerKiloPrice(rule.getPriceRule().getPerKiloPrice());
        rulePrice.setPerMinutePrice(rule.getPriceRule().getPerMinutePrice());

        //夜间价格
        rulePrice.setNightStart(rule.getNightRule().getStart());
        rulePrice.setNightEnd(rule.getNightRule().getEnd());
        rulePrice.setNightPerKiloPrice(rule.getNightRule().getPerKiloPrice());
        rulePrice.setNightPerMinutePrice(rule.getNightRule().getPerMinutePrice());

        //计算夜间价格
        TimeMeter.TimePriceUnit unit = generateTimePriceUnit(driveMeter);
        unit.setStart(UnitConverter.dateToLocalTime(rulePrice.getNightStart()));
        unit.setEnd(UnitConverter.dateToLocalTime(rulePrice.getNightEnd()));
        unit.setPerMeterPrice(UnitConverter.kiloToMeterPrice(rulePrice.getNightPerKiloPrice()));
        unit.setPerSecondPrice(UnitConverter.minuteToSecondPrice(rulePrice.getNightPerMinutePrice()));
        TimeMeter.TimePriceResult result = TimeMeter.measure(generateTimeSlice(driveMeter), unit);
        rulePrice.setNightTime(UnitConverter.secondToMinute(result.getDuration()));
        rulePrice.setNightDistance(UnitConverter.meterToKilo(result.getDistance()));
        rulePrice.setNightPrice(result.getDistancePrice().add(result.getTimePrice()).doubleValue());

        //远途价格
        rulePrice.setBeyondStartKilo(rule.getBeyondRule().getStartKilo());
        rulePrice.setBeyondPerKiloPrice(rule.getBeyondRule().getPerKiloPrice());
        rulePrice.setBeyondDistance(Math.max(0, rulePrice.getTotalDistance() - rulePrice.getBeyondStartKilo()));
        rulePrice.setBeyondPrice(rulePrice.getBeyondDistance() * rulePrice.getBeyondPerKiloPrice());

        return new AsyncResult<>(rulePrice).completable();
    }

    /**
     * 计算分段计时价格
     *
     * @param driveMeter 行驶信息
     * @return 分段计时价格
     */
    @Async
    public CompletableFuture<List<OrderRulePriceDetail>> calcPriceDetail(DriveMeter driveMeter) {
        List<OrderRulePriceDetail> details;

        //行驶开始到结束的时间片
        TimeSlice totalSlice = generateTimeSlice(driveMeter);

        //根据时间段计算价格
        details = driveMeter.getRule().getPriceRule().getTimeRules().parallelStream().map(r -> {
            OrderRulePriceDetail detail = new OrderRulePriceDetail();
            detail.setOrderId(driveMeter.getOrder().getId());
            detail.setCategory(driveMeter.getChargingCategoryEnum().getCodeAsString());
            detail.setStartHour(r.getStart());
            detail.setEndHour(r.getEnd());
            detail.setPerKiloPrice(r.getPerKiloPrice());
            detail.setPerMinutePrice(r.getPerMinutePrice());

            //设置计算用参数
            TimeMeter.TimePriceUnit unit = generateTimePriceUnit(driveMeter);
            unit.setStart(LocalTime.of(detail.getStartHour(), 0));
            unit.setEnd(LocalTime.of(detail.getEndHour(), 0));
            unit.setPerMeterPrice(UnitConverter.kiloToMeterPrice(detail.getPerKiloPrice()));
            unit.setPerSecondPrice(UnitConverter.minuteToSecondPrice(detail.getPerMinutePrice()));

            //获取计算结果
            TimeMeter.TimePriceResult result = TimeMeter.measure(totalSlice, unit);
            detail.setDuration(UnitConverter.secondToMinute(result.getDuration()));
            detail.setTimePrice(result.getTimePrice().doubleValue());
            detail.setDistance(UnitConverter.meterToKilo(result.getDistance()));
            detail.setDistancePrice(result.getDistancePrice().doubleValue());

            return detail;
        }).collect(Collectors.toList());

        return new AsyncResult<>(details).completable();
    }

    /**
     * 计算动态调价
     *
     * @param driveMeter 行驶信息
     * @return 动态调价的结果
     */
    public DiscountPrice calcDiscount(DriveMeter driveMeter) {
        KeyRule keyRule = driveMeter.getRule().getKeyRule();
        LocalDateTime start = UnitConverter.dateToLocalDateTime(driveMeter.getOrder().getOrderStartTime());

        DiscountCondition condition = new DiscountCondition();
        condition.setCityCode(keyRule.getCityCode());
        condition.setServiceTypeId(keyRule.getServiceTypeId());
        condition.setCarLevelId(keyRule.getCarLevelId());
        condition.setTotalDistance(driveMeter.getTotalDistance());
        condition.setStartHour(start.getHour());
        condition.setStartDate(start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        condition.setStartWeekDay(start.getDayOfWeek().getValue());
        return dynamicDiscountRuleDao.findDiscountByCondition(condition);
    }

    /**
     * 更新数据库
     *
     * @param master  OrderRulePrice实例
     * @param details OrderRulePriceDetail的List实例
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateToDb(ChargingCategoryEnum chargingCategory, OrderRulePrice master, List<OrderRulePriceDetail> details) {
        int orderId = master.getOrderId();
        orderRulePriceDao.deleteByOrderIdAndCategory(orderId, chargingCategory);
        orderRulePriceDetailDao.deleteByOrderIdAndCategory(orderId, chargingCategory);

        //添加创建时间
        Date now = new Date();
        master.setCreateTime(now);

        orderRulePriceDao.insert(master);
        if (details != null && !details.isEmpty()) {
            details.forEach(detail -> detail.setCreateTime(now));
            orderRulePriceDetailDao.insert(details);
        }
    }

    /**
     * 行驶开始到结束的时间片
     *
     * @param driveMeter 行驶信息
     * @return 时间片
     */
    private TimeSlice generateTimeSlice(DriveMeter driveMeter) {
        TimeSlice totalSlice = new TimeSlice();
        totalSlice.setX(driveMeter.getStartDateTime());
        totalSlice.setY(totalSlice.getX().plusSeconds((long) Math.ceil(driveMeter.getTotalTime())));
        return totalSlice;
    }

    /**
     * 生成计算用参数
     *
     * @param driveMeter 行驶信息
     * @return 计算用参数实例
     */
    private TimeMeter.TimePriceUnit generateTimePriceUnit(DriveMeter driveMeter) {
        TimeMeter.TimePriceUnit unit;
        if (ChargingCategoryEnum.Forecast == driveMeter.getChargingCategoryEnum()) {
            BigDecimal speed = BigDecimal.valueOf(driveMeter.getTotalDistance()).divide(BigDecimal.valueOf(driveMeter.getTotalTime()), 5, RoundingMode.DOWN);
            unit = TimeMeter.TimePriceUnit.instanceByForecast(speed.doubleValue());
        } else {
            int carId = driveMeter.getOrder().getCarId();
            String cityCode = driveMeter.getRule().getKeyRule().getCityCode();
            unit = TimeMeter.TimePriceUnit.instanceBySettlement(driveMeter.getRequestTask(), carId, cityCode);
        }
        return unit;
    }

}
