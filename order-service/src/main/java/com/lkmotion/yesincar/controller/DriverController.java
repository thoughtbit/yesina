package com.lkmotion.yesincar.controller;

import com.lkmotion.yesincar.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 司机控制器
 *
 * @author liheng
 * @date 2018/8/28
 */
@RestController
@RequestMapping("driver")
public class DriverController {
    @Autowired
    private DriverService driverService;



}
