package com.lkmotion.yesincar.controller;

import com.lkmotion.yesincar.dto.ResponseResult;
import com.lkmotion.yesincar.dto.map.Distance;
import com.lkmotion.yesincar.dto.map.Points;
import com.lkmotion.yesincar.dto.map.request.RouteRequest;
import com.lkmotion.yesincar.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author chaopengfei
 * @date 2018/8/20
 */
@RestController
@RequestMapping("/route")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @GetMapping(value = "distance")
    public ResponseResult distance(RouteRequest routeRequest) {

        String vehicleId = routeRequest.getVehicleId();
        String city = routeRequest.getCity();
        Long startTime = routeRequest.getStartTime();
        Long endTime = routeRequest.getEndTime();
        Distance distance = routeService.getRoute(vehicleId, city, startTime, endTime);

        return ResponseResult.success(distance);
    }

    @GetMapping(value = "points")
    public ResponseResult points(RouteRequest routeRequest) {

        String vehicleId = routeRequest.getVehicleId();
        String city = routeRequest.getCity();
        Long startTime = routeRequest.getStartTime();
        Long endTime = routeRequest.getEndTime();
        Points amapPointsResponse = routeService.getPointsAllPage(vehicleId, city, startTime, endTime);

        return ResponseResult.success(amapPointsResponse);
    }


}
