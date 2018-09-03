package com.lkmotion.yesincar.controller;

import com.lkmotion.yesincar.db.RedisDb;
import com.lkmotion.yesincar.dto.ResponseResult;
import com.lkmotion.yesincar.request.DispatchOrderRequest;
import com.lkmotion.yesincar.schedule.TaskManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dulin
 */
@RestController
@Slf4j
@RequestMapping("/dispatch")
public class OrderController {
    @Autowired
    private TaskManager taskManager;
    @Autowired
    private RedisDb redisDb;

    @RequestMapping("/")
    public String home() {
        return "dispatch";
    }

    @ResponseBody
    @RequestMapping(value = "/dispatchOrder", produces = "application/json; charset=utf-8")
    public ResponseResult dispatchOrder(@RequestBody DispatchOrderRequest request) throws InterruptedException {
        int orderId = request.getOrderId();
        taskManager.dispatch(orderId);
        return ResponseResult.success("success");
    }

}
