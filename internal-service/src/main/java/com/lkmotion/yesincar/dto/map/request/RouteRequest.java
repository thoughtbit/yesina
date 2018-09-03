package com.lkmotion.yesincar.dto.map.request;

import lombok.Data;

/**
 * @Author: chaopengfei
 */
@Data
public class RouteRequest {

    private String vehicleId;

    private String city;

    private Long startTime;

    private Long endTime;

}
