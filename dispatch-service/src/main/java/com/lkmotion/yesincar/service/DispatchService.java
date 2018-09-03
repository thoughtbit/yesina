package com.lkmotion.yesincar.service;

import com.aliyuncs.dyplsapi.model.v20170525.BindAxbResponse;
import com.aliyuncs.exceptions.ClientException;
import com.lkmotion.yesincar.config.Cache;
import com.lkmotion.yesincar.consts.Const;
import com.lkmotion.yesincar.data.DriverData;
import com.lkmotion.yesincar.dto.ResponseResult;
import com.lkmotion.yesincar.dto.map.Dispatch;
import com.lkmotion.yesincar.dto.map.Vehicle;
import com.lkmotion.yesincar.dto.map.request.DistanceRequest;
import com.lkmotion.yesincar.dto.map.request.OrderRequest;
import com.lkmotion.yesincar.dto.push.PushLoopBatchRequest;
import com.lkmotion.yesincar.dto.push.PushRequest;
import com.lkmotion.yesincar.entity.*;
import com.lkmotion.yesincar.mapper.CarInfoMapper;
import com.lkmotion.yesincar.mapper.DriverBaseInfoMapper;
import com.lkmotion.yesincar.mapper.OrderMapper;
import com.lkmotion.yesincar.mapper.OrderRulePriceMapper;
import com.lkmotion.yesincar.request.DispatchRequest;
import com.lkmotion.yesincar.task.TaskCondition;
import com.lkmotion.yesincar.util.SecretPhoneNumberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author dulin
 */
@Slf4j
public class DispatchService {
    @Autowired
    private DriverService driverService;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderRulePriceMapper orderRulePriceMapper;
    @Autowired
    private ConfigService configService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private HttpService httpService;
    @Autowired
    private CarInfoMapper carInfoMapper;
    @Autowired
    private SecretPhoneNumberService secretPhoneNumberService;
    @Autowired
    private DriverBaseInfoMapper driverBaseInfoMapper;

    @Cache
    public String test2() {
        return "1233";
    }

    public void pushMessage(PushRequest pushRequest) {
        httpService.pushMsg(pushRequest);
    }

    public void updateAmapOrder(OrderRequest orderRequest) {
        httpService.updateAmapOrder(orderRequest);
    }

    public boolean isSpecial(String cityCode, int serviceTypeId, long time) {
        return configService.isSpecial(cityCode, serviceTypeId, time);
    }

    public void loopMessageBatch(PushLoopBatchRequest pushLoopBatchRequest) {
        httpService.loopMessageBatch(pushLoopBatchRequest);
    }

    public BindAxbResponse bindAxb(String phone1, String phone2, long expireTime) {

        try {
            return secretPhoneNumberService.bindAxb(phone1, phone2, new Date(expireTime));
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }

    public double calDistance(String long1, String lat1, String long2, String lat2) {
        DistanceRequest distanceRequest = new DistanceRequest();
        distanceRequest.setOriginLongitude(long1);
        distanceRequest.setOriginLatitude(lat1);
        distanceRequest.setDestinationLongitude(long2);
        distanceRequest.setDestinationLatitude(lat2);
        return httpService.calDistance(distanceRequest);
    }

    public boolean updateOrder(Order order) {
        return httpService.updateOrder(order);
    }

    public List<TaskCondition> getForceTaskCondition(String cityCode, int serviceTypeId, int round) {
        CarDispatchDistributeIntervalSet carDispatchTimeThresholdSet = configService.getCarDispatchTimeThresholdSet(cityCode, serviceTypeId);

        if (carDispatchTimeThresholdSet == null) {
            return null;
        }
        List<TaskCondition> taskConditions = new ArrayList<>();
        for (int n = 0; n < round; n++) {
            for (int i = 0; i < 6; i++) {
                List<Integer> distanceList = new ArrayList<>();
                distanceList.add(2);
                distanceList.add(4);
                distanceList.add(6);
                TaskCondition taskCondition = new TaskCondition(carDispatchTimeThresholdSet.getCarServiceBeforeInterval(), carDispatchTimeThresholdSet.getCarServiceAfterInterval(), 0, 20, Integer.MAX_VALUE, distanceList, Const.COMPARE_TYPE_1);
                taskConditions.add(taskCondition);
            }
        }
        return taskConditions;
    }

    public List<TaskCondition> getSpecialCondition(String cityCode, int serviceTypeId) {
        List<TaskCondition> taskConditions = new ArrayList<>();

        CarDispatchDistributeIntervalSet carDispatchTimeThresholdSet = configService.getCarDispatchTimeThresholdSet(cityCode, serviceTypeId);
        // CarDispatchDirectRouteOrderRadiusSet carDispatchDirectRouteOrderRadiusSet = configService.getCarDispatchDirectRouteOrderRadiusSet(cityCode, serviceTypeId);
        CarDispatchDistributeRadiusSet carDispatchDistributeRadiusSet = configService.getCarDispatchDistributeRadiusSet(cityCode, serviceTypeId);
        if (null == carDispatchDistributeRadiusSet) {
            return null;
        }
        TaskCondition taskCondition1 = new TaskCondition(carDispatchTimeThresholdSet.getCarServiceBeforeInterval(), carDispatchTimeThresholdSet.getCarServiceAfterInterval(), carDispatchDistributeRadiusSet.getMinRadius(), 20, carDispatchDistributeRadiusSet.getMinRadiusFirstPushDriverCount(), null, Const.COMPARE_TYPE_2);
        TaskCondition taskCondition2 = new TaskCondition(carDispatchTimeThresholdSet.getCarServiceBeforeInterval(), carDispatchTimeThresholdSet.getCarServiceAfterInterval(), carDispatchDistributeRadiusSet.getMaxRadius(), 20, Integer.MAX_VALUE, null, Const.COMPARE_TYPE_2);
        TaskCondition taskCondition3 = new TaskCondition(carDispatchTimeThresholdSet.getCarServiceBeforeInterval(), carDispatchTimeThresholdSet.getCarServiceAfterInterval(), carDispatchDistributeRadiusSet.getMinRadius(), 20, carDispatchDistributeRadiusSet.getMinRadiusFirstPushDriverCount(), null, 0);
        TaskCondition taskCondition4 = new TaskCondition(carDispatchTimeThresholdSet.getCarServiceBeforeInterval(), carDispatchTimeThresholdSet.getCarServiceAfterInterval(), carDispatchDistributeRadiusSet.getMaxRadius(), 20, Integer.MAX_VALUE, null, 0);
        taskConditions.add(taskCondition1);
        taskConditions.add(taskCondition2);
        taskConditions.add(taskCondition3);
        taskConditions.add(taskCondition4);
        return taskConditions;
    }

    public List<TaskCondition> getNormalCondition(String cityCode, int serviceTypeId) {
        List<TaskCondition> taskConditions = new ArrayList<>();

        CarDispatchDistributeIntervalSet carDispatchTimeThresholdSet = configService.getCarDispatchTimeThresholdSet(cityCode, serviceTypeId);
        // CarDispatchDirectRouteOrderRadiusSet carDispatchDirectRouteOrderRadiusSet = configService.getCarDispatchDirectRouteOrderRadiusSet(cityCode, serviceTypeId);
        CarDispatchDistributeRadiusSet carDispatchDistributeRadiusSet = configService.getCarDispatchDistributeRadiusSet(cityCode, serviceTypeId);
        if (null == carDispatchDistributeRadiusSet) {
            return null;
        }
        TaskCondition taskCondition1 = new TaskCondition(carDispatchTimeThresholdSet.getCarServiceBeforeInterval(), carDispatchTimeThresholdSet.getCarServiceAfterInterval(), carDispatchDistributeRadiusSet.getMinRadius(), 20, carDispatchDistributeRadiusSet.getMinRadiusFirstPushDriverCount(), null, 0);
        TaskCondition taskCondition2 = new TaskCondition(carDispatchTimeThresholdSet.getCarServiceBeforeInterval(), carDispatchTimeThresholdSet.getCarServiceAfterInterval(), carDispatchDistributeRadiusSet.getMaxRadius(), 20, Integer.MAX_VALUE, null, 0);
        TaskCondition taskCondition3 = new TaskCondition(carDispatchTimeThresholdSet.getCarServiceBeforeInterval(), carDispatchTimeThresholdSet.getCarServiceAfterInterval(), carDispatchDistributeRadiusSet.getMinRadius(), 20, carDispatchDistributeRadiusSet.getMinRadiusFirstPushDriverCount(), null, 0);
        TaskCondition taskCondition4 = new TaskCondition(carDispatchTimeThresholdSet.getCarServiceBeforeInterval(), carDispatchTimeThresholdSet.getCarServiceAfterInterval(), carDispatchDistributeRadiusSet.getMaxRadius(), 20, Integer.MAX_VALUE, null, 0);
        taskConditions.add(taskCondition1);
        taskConditions.add(taskCondition2);
        taskConditions.add(taskCondition3);
        taskConditions.add(taskCondition4);
        return taskConditions;
    }

    public void sendSmsMessage(String phone, String code, Map<String, Object> map) {
        httpService.sendSms(phone, code, map);
    }

    public int countDriverOrder(int id, Date startTime, Date endTime) {
        return orderMapper.countByBetweenTime(id, startTime, endTime);
    }

    public List<DriverData> getCarByOrder(Order order, TaskCondition taskCondition, int distance, List<Integer> usedIds) {
        OrderRulePrice orderRulePrice = orderRulePriceMapper.selectByOrderId(order.getId());
        if (orderRulePrice == null) {
            log.info("orderRulePrice null");
            return null;
        }
        Timestamp startTime = new Timestamp(order.getOrderStartTime().getTime() - TimeUnit.MINUTES.toMillis(taskCondition.getFreeTimeBefor()));
        Timestamp endTime = new Timestamp(order.getOrderStartTime().getTime() + TimeUnit.MINUTES.toMillis(taskCondition.getFreeTimeAfter()));
        DispatchRequest dispatchRequest = new DispatchRequest(order.getId() + "", order.getDeviceCode(), (int) order.getOrderType(), (int) orderRulePrice.getCarLevelId(), orderRulePrice.getCityCode(), order.getStartTime().getTime(), order.getOrderStartTime().getTime(), order.getStartAddress(), order.getStartLongitude(), order.getStartLatitude(), order.getEndAddress(), order.getEndLongitude(), order.getEndLatitude(), distance, 200);
        List<DriverData> list = new ArrayList<>();
        ResponseResult<Dispatch> response = httpService.dispatch(dispatchRequest);
        int timeSort = 0;
        if (null == response.getData()) {
            return list;
        }
        if (response.getData().getCount() == 0) {
            return list;
        }
        for (Vehicle data : response.getData().getVehicles()) {
            int carId = Integer.parseInt(data.getVehicleId());
            DriverInfo driverInfo = driverService.getDriverByCarId(carId);
            CarInfo carInfo = getCarInfoById(carId);
            if (null == carInfo) {
                log.info("carInfo null carId = " + carId);
                continue;
            }
            if (driverInfo == null) {
                log.info("driverInfo null driverId = " + driverInfo.getId());
                continue;
            }
            if (usedIds.contains(driverInfo.getId())) {
                continue;
            }
            DriverBaseInfo driverBaseInfo = driverBaseInfoMapper.selectByPrimaryKey(driverInfo.getId());
            if (driverBaseInfo == null) {
                log.info("driverBase info null driverId=" + driverInfo.getId());
                // continue;
            }
            if (!carInfo.getCarLevelId().equals(orderRulePrice.getCarLevelId())) {
                log.info("车辆级别不同 driverId = " + driverInfo.getId());
                continue;
            }
            int workStatus = 0;
            if (driverInfo.getWorkStatus() != null) {
                workStatus = driverInfo.getWorkStatus();
            }
            int csWorkStatus = 0;
            if (driverInfo.getCsWorkStatus() != null) {
                csWorkStatus = driverInfo.getCsWorkStatus();
            }
            if (workStatus != Const.DRIVER_WORK_STATUS_GET_ORDER && workStatus != Const.DRIVER_WORK_STATUS_WORK && csWorkStatus != Const.DRIVER_CS_WORK_STATUS_WORK) {
                log.info("司机工作状态=" + workStatus + "  车机工作状态=" + csWorkStatus);
                continue;
            }
            int orderCount = orderMapper.countByBetweenTime(driverInfo.getId(), startTime, endTime);
            if (orderCount > 0) {
                log.info("司机订单数=" + orderCount);
                continue;
            }
            DriverData driverData = new DriverData();
            Integer isOpenOnTheWay = driverInfo.getIsFollowing();
            double homeDistance = Integer.MAX_VALUE;
            if (taskCondition.getCompareType() > 0 && driverBaseInfo != null && driverBaseInfo.getAddressLatitude() != null && !driverBaseInfo.getAddressLatitude().isEmpty() && driverBaseInfo.getAddressLongitude() != null && !driverBaseInfo.getAddressLongitude().isEmpty()) {
                DistanceRequest distanceRequest = new DistanceRequest();
                distanceRequest.setDestinationLatitude(driverBaseInfo.getAddressLatitude());
                distanceRequest.setDestinationLongitude(driverBaseInfo.getAddressLongitude());
                if (taskCondition.getCompareType() == Const.COMPARE_TYPE_2) {
                    distanceRequest.setOriginLatitude(order.getStartLatitude());
                    distanceRequest.setOriginLongitude(order.getStartLongitude());
                    homeDistance = httpService.calDistance(distanceRequest);
                    int compareDistance = configService.getGoHomeDistance(orderRulePrice.getCityCode(), orderRulePrice.getServiceTypeId(), taskCondition.getCompareType());
                    if (homeDistance > compareDistance) {
                        homeDistance = Integer.MAX_VALUE;
                    }
                } else if (taskCondition.getCompareType() == Const.COMPARE_TYPE_1) {
                    if (isOpenOnTheWay != null && isOpenOnTheWay == 1) {
                        distanceRequest.setOriginLatitude(order.getEndLatitude());
                        distanceRequest.setOriginLongitude(order.getEndLongitude());
                        homeDistance = httpService.calDistance(distanceRequest);
                        int compareDistance = configService.getGoHomeDistance(orderRulePrice.getCityCode(), orderRulePrice.getServiceTypeId(), taskCondition.getCompareType());
                        if (homeDistance > compareDistance) {
                            homeDistance = Integer.MAX_VALUE;
                        } else {
                            driverData.setIsFollowing(1);
                        }
                    }
                }
            }
            driverData.setTimeSort(timeSort++);
            driverData.setHomeDistance(homeDistance);
            driverData.setAmapVehicle(data);
            driverData.setDriverInfo(driverInfo);
            driverData.setCarInfo(carInfo);
            list.add(driverData);
        }
        list.sort(new Comparator<DriverData>() {
            @Override
            public int compare(DriverData o1, DriverData o2) {
                int r = o1.getTimeSort() - o2.getTimeSort();
                int n = (int) (o1.getHomeDistance() - o2.getHomeDistance());
                return n == 0 ? n : r;
            }
        });
        return list;
    }

    public boolean hasDriver(String city, Date time, int carType) {
        int count = driverService.getWorkDriverCount(city, carType);
        int timeType = getDateTimeType(time);
        CarDispatchCapacitySet carDispatchCapacitySet = configService.getCarDispatchCapacitySet(city, 1);
        if (carDispatchCapacitySet == null) {
            //TODO
            carDispatchCapacitySet = new CarDispatchCapacitySet();
            carDispatchCapacitySet.setSpareDriverCount(10);
        }
        if (count > carDispatchCapacitySet.getSpareDriverCount()) {
            return true;
        }
        return false;
    }

    public int getDateTimeType(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int h = calendar.get(Calendar.HOUR_OF_DAY);
        if (h >= Const.DAY_TIME_START_NUM && h < Const.DAY_TIME_END_NUM) {
            return Const.DAY_TIME;
        } else {
            return Const.NIGHT;
        }
    }

    public void test(int id) {
        DriverInfo driverInfo = driverService.getDriverById(id);
        log.info(driverInfo.toString());
    }

    public Order getOrderById(int id) {
        return orderMapper.selectByPrimaryKey(id);
    }

    public OrderRulePrice getOrderRulePrice(int orderId) {
        return orderRulePriceMapper.selectByOrderId(orderId);
    }

    public void updateDriverInfo(DriverInfo updateDriverInfo) {
        driverService.updateDriverInfo(updateDriverInfo);
    }

    public CarInfo getCarInfoById(int id) {
        return carInfoMapper.selectByPrimaryKey(id);
    }

    private static class LazyHodler {
        private static DispatchService ins = new DispatchService();
    }

    public static DispatchService ins() {
        return LazyHodler.ins;
    }
}
