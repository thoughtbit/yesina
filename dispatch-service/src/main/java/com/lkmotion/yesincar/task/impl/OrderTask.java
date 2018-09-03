package com.lkmotion.yesincar.task.impl;

import com.aliyuncs.dyplsapi.model.v20170525.BindAxbResponse;
import com.lkmotion.yesincar.constatnt.IdentityEnum;
import com.lkmotion.yesincar.consts.Const;
import com.lkmotion.yesincar.consts.MessageType;
import com.lkmotion.yesincar.data.DriverData;
import com.lkmotion.yesincar.data.OrderDto;
import com.lkmotion.yesincar.dto.map.Vehicle;
import com.lkmotion.yesincar.dto.map.request.OrderRequest;
import com.lkmotion.yesincar.dto.push.PushLoopBatchRequest;
import com.lkmotion.yesincar.dto.push.PushRequest;
import com.lkmotion.yesincar.entity.CarInfo;
import com.lkmotion.yesincar.entity.DriverInfo;
import com.lkmotion.yesincar.entity.Order;
import com.lkmotion.yesincar.entity.OrderRulePrice;
import com.lkmotion.yesincar.lock.RedisLock;
import com.lkmotion.yesincar.service.DispatchService;
import com.lkmotion.yesincar.task.ITask;
import com.lkmotion.yesincar.task.TaskCondition;
import com.lkmotion.yesincar.util.DateUtils;
import com.lkmotion.yesincar.util.EncriptUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author dulin
 */
@Data
@Slf4j
public class OrderTask implements ITask {
    private int orderId;
    private long nextExecuteTime;
    private int type;
    private int round;
    private static final int PIRED = 20;
    private List<TaskCondition> taskConditions = new ArrayList<>();
    private int status;
    private List<Integer> usedDriverId = new ArrayList<>();

    @Autowired
    private DispatchService dispatchService;

    @Override
    public int getTaskId() {
        return orderId;
    }

    /**
     * 下次执行时间
     * @return
     */
    @Override
    public boolean isTime() {
        return System.currentTimeMillis() > nextExecuteTime;
    }

    @Override
    public int execute(long current) {
        if (current < nextExecuteTime) {
            return status;
        }
        Order order = DispatchService.ins().getOrderById(orderId);
        OrderRulePrice orderRulePrice = DispatchService.ins().getOrderRulePrice(orderId);
        if (order == null || orderRulePrice == null) {
            status = STATUS_END;
            return status;
        }
        //判断是否已有司机接单
        if (order.getStatus() != Const.ORDER_STATUS_RE_RESERVED && order.getStatus() != Const.ORDER_STATUS_ORDER_START) {
            status = STATUS_END;
            return status;
        }
        if (round > taskConditions.size() - 1) {
            status = STATUS_END;
            //推送乘客，没人接单，或者加成功
            log.info("派单结束 = " + orderId);
            taskEnd(order, orderRulePrice);
            return status;
        }
        TaskCondition taskCondition = taskConditions.get(round);
        if (type == Const.SERVICE_TYPE_ID_FORCE) {
            forceSendOrder(order, orderRulePrice, taskCondition);
        } else if (type == Const.SERVICE_TYPE_ID_SPECIAL) {
            specialSendOrder(order, orderRulePrice, taskCondition);
        } else if (type == Const.SERVICE_TYPE_ID_NORMAL) {
            specialSendOrder(order, orderRulePrice, taskCondition);
        }
        log.info("派单，第 = " + round + "轮 nextTime = " + taskCondition.getNextTime());
        round++;
        nextExecuteTime = current + TimeUnit.SECONDS.toMillis(taskCondition.getNextTime());
        return status;
    }

    public void taskEnd(Order order, OrderRulePrice orderRulePrice) {
        if (type == Const.SERVICE_TYPE_ID_NORMAL) {
            if (DispatchService.ins().hasDriver(orderRulePrice.getCityCode(), order.getOrderStartTime(), orderRulePrice.getCarLevelId())) {
                OrderDto updateOrder = new OrderDto();
                updateOrder.setOrderId(order.getId());
                updateOrder.setId(order.getId());
                updateOrder.setIsFakeSuccess(1);
                DispatchService.ins().updateOrder(updateOrder);
            }
        } else if (type == Const.SERVICE_TYPE_ID_SPECIAL) {

        } else if (type == Const.SERVICE_TYPE_ID_FORCE) {

        }
    }

    public void forceSendOrder(Order order, OrderRulePrice orderRulePrice, TaskCondition taskCondition) {
        String orderKey = Const.REDIS_KEY_ORDER + order.getId();
        try {
            RedisLock.ins().lock(orderKey);
            Order newOrder = DispatchService.ins().getOrderById(order.getId());
            if (newOrder.getStatus() != Const.ORDER_STATUS_ORDER_START) {
                return;
            }
            for (Integer distance : taskCondition.getDistanceList()) {
                List<DriverData> list = DispatchService.ins().getCarByOrder(order, taskCondition, distance, usedDriverId);
                if (list == null) {
                    status = STATUS_END;
                    return;
                }
                log.info("司机数量 = " + list.size());
                for (DriverData data : list) {
                    Date startTime = new Date(order.getOrderStartTime().getTime() - TimeUnit.MINUTES.toMillis(taskCondition.getFreeTimeBefor()));
                    Date endTime = new Date(order.getOrderStartTime().getTime() + TimeUnit.MINUTES.toMillis(taskCondition.getFreeTimeAfter()));
                    String redisKey = Const.REDIS_KEY_DRIVER + data.getDriverInfo().getId();
                    DriverInfo driverInfo = data.getDriverInfo();
                    Vehicle amapVehicle = data.getAmapVehicle();
                    try {
                        RedisLock.ins().lock(redisKey);
                        int count = DispatchService.ins().countDriverOrder(data.getDriverInfo().getId(), startTime, endTime);
                        if (count > 0) {
                            continue;
                        }
                        String otherPhone = order.getPassengerPhone();
                        if (order.getOtherPhone() != null) {
                            otherPhone = order.getOtherPhone();
                        }
                        BindAxbResponse bindAxbResponse = DispatchService.ins().bindAxb(EncriptUtil.decryptionPhoneNumber(data.getDriverInfo().getPhoneNumber()), EncriptUtil.decryptionPhoneNumber(otherPhone), order.getOrderStartTime().getTime() + TimeUnit.DAYS.toMillis(1));
                        OrderDto updateOrder = new OrderDto();
                        if (bindAxbResponse != null) {
                            updateOrder.setMappingNumber(bindAxbResponse.getSecretBindDTO().getSecretNo());
                            updateOrder.setMappingId(bindAxbResponse.getSecretBindDTO().getSubsId());
                        }
                        updateOrder.setOrderId(orderId);
                        updateOrder.setId(orderId);
                        updateOrder.setDriverId(data.getDriverInfo().getId());
                        updateOrder.setDriverPhone(data.getDriverInfo().getPhoneNumber());
                        updateOrder.setStatus(Const.ORDER_STATUS_DRIVER_ACCEPT);
                        updateOrder.setDriverStatus(Const.ORDER_DRIVER_STATUS_ACCEPT);
                        updateOrder.setCarId(Integer.parseInt(data.getAmapVehicle().getVehicleId()));
                        updateOrder.setIsFollowing(data.getIsFollowing());
                        updateOrder.setDriverGrabTime(new Date());

                        //派单
                        boolean success = DispatchService.ins().updateOrder(updateOrder);
                        if (success) {
                            //更新高德
                            OrderRequest orderRequest = new OrderRequest();
                            orderRequest.setOrderId(order.getId() + "");
                            orderRequest.setCustomerDeviceId(order.getDeviceCode());
                            orderRequest.setType(order.getServiceType());
                            orderRequest.setStatus(2);
                            orderRequest.setOrderCity(orderRulePrice.getCityCode());
                            orderRequest.setVehicleId(order.getCarId() + "");
                            DispatchService.ins().updateAmapOrder(orderRequest);
                            status = STATUS_END;
                            //推送
                            String timeDesc = DateUtils.getDayString(order.getOrderStartTime());
                            JSONObject msg = new JSONObject();
                            String messagePhoneNum = order.getPassengerPhone();
                            if (order.getOtherPhone() != null) {
                                messagePhoneNum = order.getOtherPhone();
                            }

                            String content = timeDesc + ",乘客尾号" + EncriptUtil.decryptionPhoneNumber(messagePhoneNum) + ",从" + order.getStartAddress() + "到" + order.getEndAddress() + "预计行程" + orderRulePrice.getTotalDistance() + "公里" + orderRulePrice.getTotalTime() + "元,请您合理安排接送时间";
                            msg.put("content", content);
                            msg.put("messageType", 4004);
                            msg.put("orderId", order.getId());
                            double startAddressDistance = DispatchService.ins().calDistance(data.getAmapVehicle().getLongitude(), data.getAmapVehicle().getLatitude(), order.getStartLongitude(), order.getStartLatitude());
                            msg.put("startAddressDistance", startAddressDistance);
                            msg.put("totalPrice", orderRulePrice.getTotalPrice());
                            msg.put("totalDistance", orderRulePrice.getTotalDistance());
                            msg.put("startTime", timeDesc);
                            msg.put("startAddress", order.getStartAddress());
                            msg.put("endAddress", order.getEndAddress());
                            msg.put("isFollowing", data.getIsFollowing());
                            msg.put("serviceType", change(orderRulePrice.getServiceTypeId()));
                            {
                                PushRequest pushRequest = new PushRequest();
                                pushRequest.setSendId(order.getPassengerInfoId() + "");
                                pushRequest.setSendIdentity(IdentityEnum.PASSENGER.getCode());
                                pushRequest.setAcceptIdentity(IdentityEnum.DRIVER.getCode());
                                pushRequest.setAcceptId(data.getDriverInfo().getId() + "");
                                pushRequest.setMessageType(MessageType.ORDER_SEND_ORDER);
                                pushRequest.setTitle("派单");
                                pushRequest.setMessageBody(msg.toString());
                                DispatchService.ins().pushMessage(pushRequest);
                            }

                            //向乘客推送消息
                            msg = new JSONObject();
                            msg.put("orderId", orderId);
                            try {
                                CarInfo carInfo = data.getCarInfo();

                                msg.put("plateNumber",carInfo.getPlateNumber());
                                msg.put("brand", "大众");
                                msg.put("color", "白色");
                                String driverPhoneNumber = driverInfo.getPhoneNumber();
                                msg.put("driverName",driverInfo.getDriverName() );
                                msg.put("driverPhoneNum", EncriptUtil.decryptionPhoneNumber(driverPhoneNumber));

                                msg.put("driverHeadImg", driverInfo.getHeadImg());
                                msg.put("mappingNumber", EncriptUtil.decryptionPhoneNumber(driverPhoneNumber));
                                msg.put("avgGrade", 0);
                                msg.put("carImg", carInfo.getCarImg());
                                msg.put("driverLng", amapVehicle.getLongitude());
                                msg.put("driverLat", amapVehicle.getLatitude());

                            }catch (Exception e){
                                log.info("强派向乘客推送消息，组装消息异常");
                            }finally {
                                PushRequest pushRequest = new PushRequest();
                                pushRequest.setSendId(order.getDriverId() + "");
                                pushRequest.setSendIdentity(IdentityEnum.DRIVER.getCode());
                                pushRequest.setAcceptIdentity(IdentityEnum.PASSENGER.getCode());
                                pushRequest.setAcceptId(order.getPassengerInfoId() + "");
                                pushRequest.setMessageType(MessageType.PASSENGER_SEND_ORDER);
                                pushRequest.setTitle("派单");
                                pushRequest.setMessageBody(msg.toString());
                                DispatchService.ins().pushMessage(pushRequest);
                            }

                            //短信司机
                            String phone = EncriptUtil.decryptionPhoneNumber(data.getDriverInfo().getPhoneNumber());
                            Map<String, Object> smsMap = new HashMap<>();
                            smsMap.put("type", getTypeDesc(orderRulePrice.getServiceTypeId(), data.getIsFollowing()));
                            smsMap.put("time", DateUtils.formatDate(order.getOrderStartTime(), DateUtils.YYYYMMDDHHMMSS));
                            String passengerPhone = order.getOtherPhone() == null ? order.getPassengerPhone() : order.getOtherPhone();
                            passengerPhone = EncriptUtil.decryptionPhoneNumber(passengerPhone);
                            smsMap.put("phone", StringUtils.substring(passengerPhone, passengerPhone.length() - 4));
                            smsMap.put("start", order.getStartAddress());
                            smsMap.put("end", order.getEndAddress());
                            DispatchService.ins().sendSmsMessage(phone, Const.SMS_FORCE_DISPATCH_DRIVER, smsMap);
                            return;
                        }
                    } finally {
                        log.info("unlock key = " + redisKey);
                        RedisLock.ins().unlock(redisKey);
                    }
                }
            }
        } finally {
            RedisLock.ins().unlock(orderKey);
        }
    }

    public int change(int type) {
        if (type == Const.SERVICE_TYPE_SHISHI) {
            return Const.SERVICE_TYPE_ID_FORCE;
        } else if (type == Const.SERVICE_TYPE_YUYUE) {
            return Const.SERVICE_TYPE_ID_NORMAL;
        }
        return -1;
    }

    private String getTypeDesc(int serviceType, int isFollowing) {
        String s = "";
        if (isFollowing == 1) {
            s = "顺风单";
        } else {
            if (serviceType == Const.SERVICE_TYPE_YUYUE) {
                s = "预约派单";
            } else if (serviceType == Const.SERVICE_TYPE_SHISHI) {
                s = "实时派单";
            }
        }
        return s;

    }

    public void specialSendOrder(Order order, OrderRulePrice orderRulePrice, TaskCondition taskCondition) {
        List<DriverData> list = DispatchService.ins().getCarByOrder(order, taskCondition, taskCondition.getDistance(), usedDriverId);
        if (list == null) {
            status = STATUS_END;
            return;
        }
        log.info("司机数量 = " + list.size());
        //推送
        JSONObject messageBody = new JSONObject();
        messageBody.put("orderId", orderId);
        messageBody.put("startTime", order.getOrderStartTime().getTime());
        messageBody.put("startAddress", order.getStartAddress());
        messageBody.put("endAddress", order.getEndAddress());
        messageBody.put("forecastPrice", orderRulePrice.getTotalPrice());
        messageBody.put("forecastDistance", orderRulePrice.getTotalDistance());
        List<String> driverList = new ArrayList<>();
        List<String> carScreenList = new ArrayList<>();
        int count = 0;
        for (DriverData data : list) {
            usedDriverId.add(data.getDriverInfo().getId());
            driverList.add(data.getDriverInfo().getId() + "");
            carScreenList.add(data.getCarInfo().getLargeScreenDeviceCode());
            count++;
            if (count >= taskCondition.getDriverNum()) {
                break;
            }
        }
        if (driverList.size() > 0) {
            PushLoopBatchRequest request1 = new PushLoopBatchRequest(IdentityEnum.DRIVER.getCode(), driverList, MessageType.DRIVER_RESERVED, messageBody.toString(), order.getPassengerInfoId() + "", Const.IDENTITY_PASSENGER);
            DispatchService.ins().loopMessageBatch(request1);
        }
        if (carScreenList.size() > 0) {
            PushLoopBatchRequest request2 = new PushLoopBatchRequest(IdentityEnum.CAR_SCREEN.getCode(), carScreenList, MessageType.CAR_SCREEN_RESERVED, messageBody.toString(), order.getPassengerInfoId() + "", Const.IDENTITY_PASSENGER);
            DispatchService.ins().loopMessageBatch(request2);
        }
    }

    private static final int STATUS_END = -1;

}
