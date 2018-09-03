package com.lkmotion.yesincar.task;

import com.lkmotion.yesincar.constatnt.BusinessInterfaceStatus;
import com.lkmotion.yesincar.dao.ChargeRuleDao;
import com.lkmotion.yesincar.dto.ResponseResult;
import com.lkmotion.yesincar.dto.map.Route;
import com.lkmotion.yesincar.dto.valuation.PriceResult;
import com.lkmotion.yesincar.dto.valuation.charging.*;
import com.lkmotion.yesincar.entity.CarLevel;
import com.lkmotion.yesincar.entity.ChargeRule;
import com.lkmotion.yesincar.entity.ChargeRuleDetail;
import com.lkmotion.yesincar.mapper.CarLevelMapper;
import com.lkmotion.yesincar.request.OrderRequest;
import com.lkmotion.yesincar.util.RestTemplateHepler;
import com.lkmotion.yesincar.utils.Distance;
import com.lkmotion.yesincar.utils.ServicesConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 功能描述
 *
 * @author liheng
 * @date 2018/9/1
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class OtherInterfaceTask {

    private static final String TOO_FAR_AWAY = "距离太远";
    private static final String TOO_CLOSE = "距离太近";

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ServicesConfig servicesConfig;
    @Autowired
    private ChargeRuleDao chargeRuleDao;
    @Autowired
    private CarLevelMapper carLevelMapper;

    /**
     * 获取路途长度和行驶时间
     * @param orderRequest
     * @return
     * @throws Exception
     */
    public ResponseResult requestRoute(OrderRequest orderRequest) throws Exception {
        Route route;
        Map<String, Object> map = new HashMap<>(4);
        map.put("originLongitude", orderRequest.getStartLongitude());
        map.put("originLatitude", orderRequest.getStartLatitude());
        map.put("destinationLongitude", orderRequest.getEndLongitude());
        map.put("destinationLatitude", orderRequest.getEndLatitude());

        String param = String.join("&", map.keySet().stream().map(k -> k + "={" + k + "}").collect(Collectors.toList()));
        String url = servicesConfig.getMapAddress() + "/distance?" + param;
        log.info("Request Distance={}", map);
        try{
            ResponseResult responseResult = restTemplate.getForObject(url, ResponseResult.class, map);
            route = RestTemplateHepler.parse(responseResult, Route.class);
            log.info("测量距离返回={}",route);

            if (null == route.getDuration() || null == route.getDistance()) {
                return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), "测量距离失败");
            }
            if (route.getDistance() <= 0) {
                return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), TOO_CLOSE);
            }
            if (route.getDistance() > Distance.DISTANCE.getCode()) {
                return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), TOO_FAR_AWAY);
            }
        }catch (Exception e){
            log.error("调用接口Distance错误:", e);
            return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), "测量距离失败");
        }
        return ResponseResult.success(route);
    }

    /**
     * 计价规则
     * @param orderRequest
     * @return
     */
    public Rule getOrderChargeRule(OrderRequest orderRequest) {
        Rule rule = new Rule();

        HashMap<String, Object> parameterMap = new HashMap<>(4);
        parameterMap.put("cityCode", orderRequest.getCityCode());
        parameterMap.put("serviceTypeId", orderRequest.getServiceTypeId());
        parameterMap.put("channelId", orderRequest.getChannelId());
        parameterMap.put("carLevelId", orderRequest.getCarLevelId());

        CarLevel carLevel = carLevelMapper.selectByPrimaryKey(orderRequest.getCarLevelId());
        List<ChargeRule> chargeRuleList = chargeRuleDao.selectByPrimaryKey(parameterMap);
        int ruleSize = chargeRuleList.size();
        if (ruleSize == 0 || ruleSize > 1) {
            return null;
        }
        ChargeRule chargeRule = chargeRuleList.get(0);
        rule.setId(chargeRule.getId());

        KeyRule keyRule = new KeyRule();
        keyRule.setCityCode(orderRequest.getCityCode());
        keyRule.setCityName(orderRequest.getCityName());
        keyRule.setServiceTypeId(orderRequest.getServiceTypeId());
        keyRule.setServiceTypeName(orderRequest.getServiceTypeName());
        keyRule.setChannelId(orderRequest.getChannelId());
        keyRule.setChannelName(orderRequest.getChannelName());
        keyRule.setCarLevelId(orderRequest.getCarLevelId());
        keyRule.setCarLevelName(carLevel.getLabel());
        rule.setKeyRule(keyRule);

        BasicRule basicRule = new BasicRule();
        basicRule.setLowestPrice(chargeRule.getLowestPrice());
        basicRule.setBasePrice(chargeRule.getBasePrice());
        rule.setBasicRule(basicRule);

        //
        PriceRule priceRule = new PriceRule();
        List<TimeRule> timeRules = new ArrayList<>();
        priceRule.setPerKiloPrice(chargeRule.getPerKiloPrice());
        priceRule.setPerMinutePrice(chargeRule.getPerMinutePrice());
        priceRule.setTimeRules(timeRules);
        rule.setPriceRule(priceRule);

        if (!ObjectUtils.nullSafeEquals(chargeRule.getBaseKilo(),0)  && !ObjectUtils.nullSafeEquals(chargeRule.getBaseMinutes(),0)) {
            basicRule.setKilos(chargeRule.getBaseKilo());
            basicRule.setMinutes(chargeRule.getBaseMinutes());
        }else{
            List<ChargeRuleDetail> chargeRuleDetailList = chargeRuleDao.chargeRuleDetailList(chargeRule.getId());
            for (ChargeRuleDetail chargeRuleDetail : chargeRuleDetailList) {
                TimeRule timeRule = new TimeRule();
                timeRule.setStart(chargeRuleDetail.getStart());
                timeRule.setEnd(chargeRuleDetail.getEnd() + 1);
                timeRule.setPerKiloPrice(chargeRuleDetail.getPerKiloPrice());
                timeRule.setPerMinutePrice(chargeRuleDetail.getPerMinutePrice());
                timeRules.add(timeRule);
            }
        }

        //
        BeyondRule beyondRule = new BeyondRule();
        beyondRule.setStartKilo(chargeRule.getBeyondStartKilo());
        beyondRule.setPerKiloPrice(chargeRule.getBeyondPerKiloPrice());
        rule.setBeyondRule(beyondRule);

        NightRule nightRule = new NightRule();
        nightRule.setStart(chargeRule.getNightStart());
        nightRule.setEnd(chargeRule.getNightEnd());
        nightRule.setPerKiloPrice(chargeRule.getNightPerKiloPrice());
        nightRule.setPerMinutePrice(chargeRule.getNightPerMinutePrice());
        rule.setNightRule(nightRule);
        return rule;
    }

    /**
     * 获取预估价格
     *
     * @param orderId
     * @return
     */
    public PriceResult getOrderPrice(int orderId) throws Exception{
        PriceResult priceResult = null;
        log.info("orderId={}", orderId);
        String url = servicesConfig.getValuation();
        try {
            ResponseResult responseResult = restTemplate.getForObject(url + "/valuation/forecast/" + orderId, ResponseResult.class);
            priceResult = RestTemplateHepler.parse(responseResult, PriceResult.class);
            log.info("预估价格返回数据={}",priceResult);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("调用接口Distance错误:", e);
            throw e;
        }
        return priceResult;
    }


}
