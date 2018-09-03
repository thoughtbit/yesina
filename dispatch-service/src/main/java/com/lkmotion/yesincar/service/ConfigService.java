package com.lkmotion.yesincar.service;

import com.lkmotion.yesincar.entity.*;
import com.lkmotion.yesincar.mapper.*;
import com.lkmotion.yesincar.util.ServiceAddress;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

/**
 * @author dulin
 */
@Service
@Slf4j
public class ConfigService {
    @Autowired
    private CarDispatchDistributeSetMapper carDispatchDistributeSetMapper;
    @Autowired
    private CarDispatchDirectRouteOrderRadiusSetMapper carDispatchDirectRouteOrderRadiusSetMapper;
    @Autowired
    private CarDispatchDistributeIntervalSetMapper carDispatchDistributeIntervalSetMapper;
    @Autowired
    private CarDispatchCapacitySetMapper carDispatchCapacitySetMapper;
    @Autowired
    private CarDispatchTimeThresholdSetMapper carDispatchTimeThresholdSetMapper;
    @Autowired
    private CarDispatchDistributeRadiusSetMapper carDispatchDistributeRadiusSetMapper;
    @Autowired
    private CarDispatchSpecialPeriodSetMapper carDispatchSpecialPeriodSetMapper;
    @Autowired
    private ServiceAddress serviceAddress;

    public String mapServiceUrl() {
        return serviceAddress.get("map");
    }

    public String messageServiceUrl() {
        return serviceAddress.get("message");
    }

    public String orderServiceUrl() {
        return serviceAddress.get("order");
    }

    public int getGoHomeDistance(String cityCode, int serviceTypeId, int type) {
        CarDispatchDirectRouteOrderRadiusSet carDispatchDirectRouteOrderRadiusSet = carDispatchDirectRouteOrderRadiusSetMapper.getCarDispatchDirectRouteOrderRadiusSet(cityCode, serviceTypeId, type);
        if (carDispatchDirectRouteOrderRadiusSet != null) {
            return carDispatchDirectRouteOrderRadiusSet.getDirectRouteOrderRadius();
        }
        return 5;
    }

    public CarDispatchCapacitySet getCarDispatchCapacitySet(String cityCode, int timeType) {
        return carDispatchCapacitySetMapper.getCarDispatchCapacitySet(cityCode, timeType);
    }

    public CarDispatchDistributeRadiusSet getCarDispatchDistributeRadiusSet(String cityCode, int serviceTypeId) {
        return carDispatchDistributeRadiusSetMapper.getCarDispatchDistributeRadiusSet(cityCode, serviceTypeId);
    }

    public CarDispatchDirectRouteOrderRadiusSet getCarDispatchDirectRouteOrderRadiusSet(String cityCode, int serviceTypeId, int type) {
        return carDispatchDirectRouteOrderRadiusSetMapper.getCarDispatchDirectRouteOrderRadiusSet(cityCode, serviceTypeId, type);
    }

    public CarDispatchDistributeIntervalSet getCarDispatchTimeThresholdSet(String cityCode, int serviceTypeId) {
        return carDispatchDistributeIntervalSetMapper.selectByCityCodeAndServiceType(cityCode, serviceTypeId);
    }

    public boolean isOpenForceSendOrder(String city) {
        CarDispatchDistributeSet carDispatchDistributeSet = carDispatchDistributeSetMapper.getOpenedByCityCode(city);
        if (null != carDispatchDistributeSet) {
            return true;
        }
        return false;
    }

    public boolean isSpecial(String cityCode, int serviceCode, long time) {
        CarDispatchSpecialPeriodSet carDispatchSpecialPeriodSet = carDispatchSpecialPeriodSetMapper.getByCityCode(cityCode, serviceCode);
        if (carDispatchSpecialPeriodSet == null) {
            return false;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        JSONArray array = JSONArray.fromObject(carDispatchSpecialPeriodSet.getTimePeriod());
        for (int i = 0; i < array.size(); i++) {
            JSONObject o = array.getJSONObject(i);
            String start = o.getString("start");
            String end = o.getString("end");
            if (isInTimePeriod(hour, minute, start, end)) {
                return true;
            }
        }
        return false;
    }

    public boolean isInTimePeriod(int hour, int minute, String start, String end) {
        double t = Double.valueOf(hour + "." + minute);
        double s = Double.valueOf(start.replace(":", "."));
        double e = Double.valueOf(end.replace(":", "."));
        if (t >= s && t <= e) {
            return true;
        }
        return false;
    }

    public int getForceSendOrderTime(String cityCode, int serviceType) {
        CarDispatchTimeThresholdSet carDispatchTimeThresholdSet = carDispatchTimeThresholdSetMapper.selectByCityAndServiceType(cityCode, serviceType);
        return carDispatchTimeThresholdSet.getTimeThreshold();
    }

}
