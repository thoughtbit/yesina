package com.lkmotion.yesincar.service;

/**
 * @author lizhaoteng
 **/
public interface AmapCarService {
    public String uploadCar(String carId , String vehicleLng , String vehicleLat , String city , String state , String vehicleType , String orderID,
                            Double speed, Double accuracy , Double direction, Double height , Long vehicleTimeMillis, Integer locationType);
}
