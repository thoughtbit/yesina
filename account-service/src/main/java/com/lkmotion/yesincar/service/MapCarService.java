package com.lkmotion.yesincar.service;

import com.lkmotion.yesincar.response.MapDistanceResponse;

/**
 * @author lizhaoteng
 **/
public interface MapCarService  {

    public String uploadCarInfo(String carId , String vehicleLng , String vehicleLat , String city , String state , String vehicleType , String orderID, Double speed, Double accuracy , Double direction, Double height, Long vehicleTimeMillis, Integer locationType);

    public MapDistanceResponse getHistoryRoute(String carId, String city , Long startTime, Long endTime);
}
