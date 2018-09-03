package com.lkmotion.yesincar.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lkmotion.yesincar.dao.OrderRuleMirrorDao;
import com.lkmotion.yesincar.dto.DriveMeter;
import com.lkmotion.yesincar.dto.ResponseResult;
import com.lkmotion.yesincar.dto.map.Distance;
import com.lkmotion.yesincar.dto.map.Route;
import com.lkmotion.yesincar.dto.valuation.charging.Rule;
import com.lkmotion.yesincar.entity.OrderRuleMirror;
import com.lkmotion.yesincar.util.RestTemplateHepler;
import com.lkmotion.yesincar.util.ServiceAddress;
import com.lkmotion.yesincar.util.UnitConverter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 计价服务请求任务
 *
 * @author ZhuBin
 * @date 2018/8/14
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class ValuationRequestTask {

    @NonNull
    private OrderRuleMirrorDao orderRuleMirrorDao;

    @NonNull
    private RestTemplate restTemplate;

    @NonNull
    private ServiceAddress serviceAddress;

    /**
     * 将Json解析为Rule
     *
     * @param orderId 订单id
     * @return rule实例
     * @throws Exception 异常
     */
    public Rule requestRule(Integer orderId) throws Exception {
        Rule rule;
        try {
            OrderRuleMirror orderRuleMirror = orderRuleMirrorDao.selectByOrderId(orderId);
            String ruleJson = orderRuleMirror.getRule();
            log.info("orderId={}, RuleJson={}", orderId, ruleJson);

            ObjectMapper objectMapper = new ObjectMapper();
            rule = objectMapper.readValue(ruleJson, Rule.class);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("orderId={}, 解析RuleJson错误:", orderId, e);
            throw e;
        }

        return rule;
    }

    /**
     * 获取路途长度和行驶时间
     *
     * @param driveMeter 行驶信息
     * @return 行驶信息
     * @throws Exception 异常
     */
    public Route requestRoute(DriveMeter driveMeter) throws Exception {
        Route route;
        try {
            Map<String, Object> map = new HashMap<>(4);
            map.put("originLongitude", driveMeter.getOrder().getStartLongitude());
            map.put("originLatitude", driveMeter.getOrder().getStartLatitude());
            map.put("destinationLongitude", driveMeter.getOrder().getEndLongitude());
            map.put("destinationLatitude", driveMeter.getOrder().getEndLatitude());
            log.info("orderId={}, Request Route={}", driveMeter.getOrder().getId(), map);

            String param = String.join("&", map.keySet().stream().map(k -> k + "={" + k + "}").collect(Collectors.toList()));
            String url = serviceAddress.getMapAddress() + "/distance?" + param;
            ResponseResult result = restTemplate.getForObject(url, ResponseResult.class, map);
            log.info("调用接口Route返回{}", result);
            route = RestTemplateHepler.parse(result, Route.class);

            if (null == route.getDuration() || null == route.getDistance()) {
                throw new Exception("Route内容为空：" + route);
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("orderId={}, 调用接口Route错误:", driveMeter.getOrder().getId(), e);
            throw e;
        }

        return route;
    }

    /**
     * 获取路途长度
     *
     * @param driveMeter 行驶信息
     * @return 行驶信息
     * @throws Exception 异常
     */
    public Distance requestDistance(DriveMeter driveMeter) throws Exception {
        try {
            int carId = driveMeter.getOrder().getCarId();
            String cityCode = driveMeter.getRule().getKeyRule().getCityCode();
            LocalDateTime startTime = UnitConverter.dateToLocalDateTime(driveMeter.getOrder().getReceivePassengerTime());
            LocalDateTime endTime = UnitConverter.dateToLocalDateTime(driveMeter.getOrder().getPassengerGetoffTime());
            log.info("orderId={}", driveMeter.getOrder().getId());

            return requestDistance(carId, cityCode, startTime, endTime);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("orderId={}, 调用接口Distance错误:", driveMeter.getOrder().getId(), e);
            throw e;
        }
    }

    /**
     * 获取路途长度
     *
     * @param carId     车辆ID
     * @param cityCode  城市编码
     * @param startTime 开始时间点
     * @param endTime   结束世界点
     * @return 距离
     * @throws Exception 异常
     */
    public Distance requestDistance(int carId, String cityCode, LocalDateTime startTime, LocalDateTime endTime) throws Exception {
        Distance distance;
        try {
            Map<String, Object> map = new HashMap<>(4);
            map.put("vehicleId", carId);
            map.put("city", cityCode);
            map.put("startTime", startTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
            map.put("endTime", endTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
            log.info("Request Distance={}", map);

            String param = String.join("&", map.keySet().stream().map(k -> k + "={" + k + "}").collect(Collectors.toList()));
            String url = serviceAddress.getMapAddress() + "/route/distance?" + param;
            ResponseResult result = restTemplate.getForObject(url, ResponseResult.class, map);
            distance = RestTemplateHepler.parse(result, Distance.class);

            if (null == distance.getDistance()) {
                throw new Exception("distance内容为空：" + distance);
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("调用接口Distance错误:", e);
            throw e;
        }

        return distance;
    }

}
