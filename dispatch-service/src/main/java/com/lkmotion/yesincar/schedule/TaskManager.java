package com.lkmotion.yesincar.schedule;

import com.lkmotion.yesincar.constatnt.BusinessInterfaceStatus;
import com.lkmotion.yesincar.consts.Const;
import com.lkmotion.yesincar.context.TaskStore;
import com.lkmotion.yesincar.dto.ResponseResult;
import com.lkmotion.yesincar.entity.Order;
import com.lkmotion.yesincar.entity.OrderRulePrice;
import com.lkmotion.yesincar.service.ConfigService;
import com.lkmotion.yesincar.service.DispatchService;
import com.lkmotion.yesincar.task.ITask;
import com.lkmotion.yesincar.task.TaskCondition;
import com.lkmotion.yesincar.task.impl.OrderTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author dulin
 */
@Service
@Slf4j
public class TaskManager {
    private static final String ORDER_ID_IS_NULL = "订单ID为空";
    private static final String ORDER_START_TIME_IS_NULL = "订单开始时间为空";
    private static final String TASK_CONDITIONS_IS_NULL = "任务为空";

    @Autowired
    private TaskStore taskStore;
    @Autowired
    private DispatchService dispatchService;
    @Autowired
    private ConfigService configService;

    @Async
    public ResponseResult dispatch(int orderId) {
        //派单任务，定时执行
        OrderTask orderTask = new OrderTask();
        orderTask.setOrderId(orderId);
        Order order = dispatchService.getOrderById(orderId);
        if (null == order) {
            return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), ORDER_ID_IS_NULL);
        }
        //serviceTypeId,cityCode
        OrderRulePrice orderRulePrice = dispatchService.getOrderRulePrice(orderId);
        int type = -1;
        if (null == order.getOrderStartTime()) {
            return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), ORDER_START_TIME_IS_NULL);
        }
        List<TaskCondition> taskConditions = null;
        if (order.getStatus() == Const.ORDER_STATUS_ORDER_START) {
            //小于30分钟，强派
            if (order.getOrderStartTime().getTime() - System.currentTimeMillis() <
                    TimeUnit.MINUTES.toMillis(configService.getForceSendOrderTime(orderRulePrice.getCityCode(), Const.SERVICE_TYPE_SHISHI))) {
                type = Const.SERVICE_TYPE_ID_FORCE;
                log.info("dispatch------强派");
                //轮数
                int round = 1;
                if (order.getIsFakeSuccess() == 1) {
                    log.info("dispatch------加成功强派");
                    round = 3;
                }
                //派单规则，一轮一个task，多轮
                taskConditions = dispatchService.getForceTaskCondition(orderRulePrice.getCityCode(), Const.SERVICE_TYPE_SHISHI, round);
            } else {
                //特殊时段
                boolean isSpecial = dispatchService.isSpecial(orderRulePrice.getCityCode(), orderRulePrice.getServiceTypeId(), order.getOrderStartTime().getTime());
                if (isSpecial) {
                    type = Const.SERVICE_TYPE_ID_SPECIAL;
                    log.info("dispatch------预约单，特殊时段");
                    taskConditions = dispatchService.getSpecialCondition(orderRulePrice.getCityCode(), orderRulePrice.getServiceTypeId());
                } else {
                    type = Const.SERVICE_TYPE_ID_NORMAL;
                    log.info("dispatch------预约单，普通时段");
                    taskConditions = dispatchService.getNormalCondition(orderRulePrice.getCityCode(), orderRulePrice.getServiceTypeId());
                }
            }
        }
        /*else if (order.getStatus() == Const.ORDER_STATUS_RE_RESERVED) {
            type = Const.SERVICE_TYPE_ID_FORCE;
            taskConditions = dispatchService.getForceTaskCondition(orderRulePrice.getCityCode(), Const.SERVICE_TYPE_ID_FORCE);
        }*/
        if (taskConditions == null) {
            return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), TASK_CONDITIONS_IS_NULL);
        }
        orderTask.setType(type);
        orderTask.setTaskConditions(taskConditions);
        int status = orderTask.execute(System.currentTimeMillis());
        if (status != -1) {
            taskStore.addTask(orderTask.getTaskId(), orderTask);
        }
        return ResponseResult.success("派单成功");
    }

    @Async
    public void retry(ITask task) {
        int status = task.execute(System.currentTimeMillis());
        if (status != -1) {
            taskStore.addTask(task.getTaskId(), task);
        }
    }

}
