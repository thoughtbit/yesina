package com.lkmotion.yesincar.service.impl;

import com.lkmotion.yesincar.dto.government.SupervisionData;
import com.lkmotion.yesincar.entity.*;
import com.lkmotion.yesincar.exception.ParameterException;
import com.lkmotion.yesincar.service.SupervisionService;
import com.lkmotion.yesincar.task.AbstractSupervisionTask;
import com.lkmotion.yesincar.task.baseinfo.BaseInfoDriverTask;
import com.lkmotion.yesincar.task.operate.OperateArriveTask;
import com.lkmotion.yesincar.task.operate.OperateDepartTask;
import com.lkmotion.yesincar.task.operate.OperateOnlineOfflineTask;
import com.lkmotion.yesincar.task.order.OrderTask;
import com.lkmotion.yesincar.task.rated.RatedDriverTask;
import com.lkmotion.yesincar.task.rated.RatedPassengerTask;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 监管上报服务
 *
 * @author ZhuBin
 * @date 2018/8/23
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SupervisionServiceImpl implements SupervisionService {

    @NonNull
    private OrderTask orderTask;

    @NonNull
    private BaseInfoDriverTask baseInfoDriverTask;

    @NonNull
    private OperateDepartTask operateDepartTask;

    @NonNull
    private OperateArriveTask operateArriveTask;

    @NonNull
    private OperateOnlineOfflineTask operateOnlineOfflineTask;

    @NonNull
    private RatedPassengerTask ratedPassengerTask;

    @NonNull
    private RatedDriverTask ratedDriverTask;

    /**
     * 分发上报任务
     *
     * @param data 上报对象DTO
     */
    @Override
    public void dispatch(SupervisionData data) throws Exception {
        log.info("分发上报任务dispatch：{}", data);

        List<AbstractSupervisionTask> tasks = new ArrayList<>();
        Class cls = Class.forName(data.getClassName());

        if (Order.class == cls) {
            tasks.add(orderTask);
            tasks.add(operateDepartTask);
        } else if (DriverBaseInfo.class == cls || DriverInfo.class == cls) {
            tasks.add(baseInfoDriverTask);
        } else if (OrderRulePrice.class == cls) {
            tasks.add(operateArriveTask);
        } else if (DriverWorkTime.class == cls) {
            tasks.add(operateOnlineOfflineTask);
        } else if (EvaluateDriver.class == cls) {
            tasks.add(ratedPassengerTask);
        } else if (DriverRate.class == cls) {
            tasks.add(ratedDriverTask);
        }

        if (!tasks.isEmpty()) {
            doTask(tasks, data.getOperationType(), data.getId());
        }
    }

    /**
     * 根据操作确定上报操作
     *
     * @param tasks         任务实例
     * @param operationType 操作类型
     * @param id            上报对象id
     */
    private void doTask(List<AbstractSupervisionTask> tasks, SupervisionData.OperationType operationType, Integer id) {
        switch (operationType) {
            case Insert:
                tasks.forEach(t -> {
                    if (t.insert(id)) {
                        t.send();
                    }
                });
                break;

            case Update:
                tasks.forEach(t -> {
                    if (t.update(id)) {
                        t.send();
                    }
                });
                break;

            case Delete:
                tasks.forEach(t -> {
                    if (t.delete(id)) {
                        t.send();
                    }
                });
                break;

            default:
                throw new ParameterException("未知操作");
        }
    }
}
