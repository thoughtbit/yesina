package com.lkmotion.yesincar.data;

import com.lkmotion.yesincar.dto.map.Vehicle;
import com.lkmotion.yesincar.entity.CarInfo;
import com.lkmotion.yesincar.entity.DriverInfo;
import lombok.Data;

/**
 * @author dulin
 */
@Data
public class DriverData {
    private Vehicle amapVehicle;
    private DriverInfo driverInfo;
    private double homeDistance;
    private CarInfo carInfo;
    private int isFollowing;
    private int timeSort;
}
