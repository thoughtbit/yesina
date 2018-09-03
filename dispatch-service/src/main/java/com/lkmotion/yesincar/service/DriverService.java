package com.lkmotion.yesincar.service;

import com.lkmotion.yesincar.entity.DriverInfo;
import com.lkmotion.yesincar.mapper.DriverInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dulin
 */
@Service
public class DriverService {
    @Autowired
    private DriverInfoMapper driverInfoMapper;

    public DriverInfo getDriverById(int id) {
        return driverInfoMapper.selectByPrimaryKey(id);
    }

    public DriverInfo getDriverByCarId(int carId) {
        return driverInfoMapper.selectByCarId(carId);
    }

    public void updateDriverInfo(DriverInfo driverInfo) {
        driverInfoMapper.updateByPrimaryKeySelective(driverInfo);
    }

    public int getWorkDriverCount(String city, int carType) {
        return driverInfoMapper.countWorkDriver(city, carType);
    }
}
