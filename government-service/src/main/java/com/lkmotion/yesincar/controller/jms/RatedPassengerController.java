package com.lkmotion.yesincar.controller.jms;

import com.lkmotion.yesincar.constatnt.BusinessInterfaceStatus;
import com.lkmotion.yesincar.controller.AbstractJmsController;
import com.lkmotion.yesincar.dto.ResponseResult;
import com.lkmotion.yesincar.dto.government.SupervisionData;
import com.lkmotion.yesincar.entity.EvaluateDriver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 乘客评价控制器
 *
 * @author ZhuBin
 * @date 2018/9/1
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class RatedPassengerController extends AbstractJmsController {

    private static final String ERR_EMPTY_ORDER_ID = "订单ID为空";

    /**
     * 乘客评价信息
     *
     * @param orderId 订单ID
     * @return ResponseResult
     */
    @GetMapping("/ratedPassenger/{orderId}")
    public ResponseResult rate(@PathVariable String orderId) {
        if (StringUtils.isEmpty(orderId)) {
            log.error(ERR_EMPTY_ORDER_ID);
            return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), ERR_EMPTY_ORDER_ID);
        }

        try {
            triggerListener(EvaluateDriver.class, orderId, SupervisionData.OperationType.Update);
            return ResponseResult.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("触发错误", e);
            return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), e.getMessage());
        }
    }
}
