package com.lkmotion.yesincar.dto;

import com.lkmotion.yesincar.constatnt.ChargingCategoryEnum;
import com.lkmotion.yesincar.dto.map.Distance;
import com.lkmotion.yesincar.dto.map.Route;
import com.lkmotion.yesincar.dto.valuation.charging.Rule;
import com.lkmotion.yesincar.entity.Order;
import com.lkmotion.yesincar.task.ValuationRequestTask;
import com.lkmotion.yesincar.util.UnitConverter;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

/**
 * 行驶计价相关的请求参数
 *
 * @author ZhuBin
 * @date 2018/8/14
 */
@Data
@RequiredArgsConstructor
@Accessors(chain = true)
public class DriveMeter {

    /**
     * 订单
     */
    private Order order;

    /**
     * 计价规则
     */
    private Rule rule;

    /**
     * 预估时（forecast）距离测量结果
     *
     * @see ChargingCategoryEnum
     */
    private Route route;

    /**
     * 结算时（settlement）轨迹里程
     *
     * @see ChargingCategoryEnum
     */
    private Distance distance;

    /**
     * 计价服务请求任务实体类
     */
    private ValuationRequestTask requestTask;

    /**
     * 计价规则种类枚举
     */
    @NonNull
    private ChargingCategoryEnum chargingCategoryEnum;

    /**
     * 返回行驶距离（米）
     *
     * @return 行驶距离
     */
    public double getTotalDistance() {
        Double meters = ChargingCategoryEnum.Forecast == chargingCategoryEnum ? route.getDistance() : distance.getDistance();
        return Optional.ofNullable(meters).orElse(0D);
    }

    /**
     * 返回行驶时间（秒）
     *
     * @return 行驶时间
     */
    public double getTotalTime() {
        double seconds = 0;

        if (ChargingCategoryEnum.Forecast == chargingCategoryEnum) {
            seconds = route.getDuration();
        } else {
            seconds = order.getPassengerGetoffTime().getTime() - order.getReceivePassengerTime().getTime();
        }

        return seconds;
    }

    /**
     * 返回订单开始时间
     *
     * @return 订单开始时间
     */
    public LocalDateTime getStartDateTime() {
        Date startDate = ChargingCategoryEnum.Forecast == chargingCategoryEnum ? order.getOrderStartTime() : order.getReceivePassengerTime();
        return UnitConverter.dateToLocalDateTime(startDate);
    }

}
