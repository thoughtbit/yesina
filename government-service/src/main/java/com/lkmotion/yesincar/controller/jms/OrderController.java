package com.lkmotion.yesincar.controller.jms;

import com.lkmotion.yesincar.constatnt.BusinessInterfaceStatus;
import com.lkmotion.yesincar.controller.AbstractJmsController;
import com.lkmotion.yesincar.dto.ResponseResult;
import com.lkmotion.yesincar.dto.government.SupervisionData;
import com.lkmotion.yesincar.entity.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单控制器
 *
 * @author ZhuBin
 * @date 2018/8/29
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderController extends AbstractJmsController {

    private static final String ERR_EMPTY_ORDER_ID = "订单ID为空";

    /**
     * 订单取消
     *
     * @param orderId 订单ID
     * @return ResponseResult
     */
    @GetMapping("/order/cancel/{orderId}")
    public ResponseResult cancel(@PathVariable String orderId) {
        if (StringUtils.isEmpty(orderId)) {
            log.error(ERR_EMPTY_ORDER_ID);
            return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), ERR_EMPTY_ORDER_ID);
        }

        try {
            triggerListener(Order.class, orderId, SupervisionData.OperationType.Update);
            return ResponseResult.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("触发错误", e);
            return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), e.getMessage());
        }
    }
}
