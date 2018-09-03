package com.lkmotion.yesincar.controller;

import com.lkmotion.yesincar.dto.ResponseResult;
import com.lkmotion.yesincar.dto.map.request.DistanceRequest;
import com.lkmotion.yesincar.service.DistanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chaopengfei
 * @date 2018/8/20
 */
@RestController
public class DistanceController {

    @Autowired
    private DistanceService distanceService;

    @GetMapping(value = "/distance")
    public ResponseResult distance(DistanceRequest distanceRequest) {
        return distanceService.distance(distanceRequest);
    }
}
