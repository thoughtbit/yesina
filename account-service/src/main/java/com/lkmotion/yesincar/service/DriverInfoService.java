package com.lkmotion.yesincar.service;

import com.lkmotion.yesincar.dto.ResponseResult;
import com.lkmotion.yesincar.dto.driver.DriverBaseInfoView;
import com.lkmotion.yesincar.entity.DriverInfo;

/**
 * @author LiZhaoTeng
 **/
public interface DriverInfoService {
    public DriverInfo queryDriverInfoByPhoneNum(String phoneNum);
    public int updateDriverInfoByPhoneNum(DriverInfo driverInfo);
    public int updateCsWorkStatusByDriverId(Integer driverId,Integer csWorkStatus);
    public ResponseResult changeDriverBaseInfo(DriverBaseInfoView view, int type) ;
}
