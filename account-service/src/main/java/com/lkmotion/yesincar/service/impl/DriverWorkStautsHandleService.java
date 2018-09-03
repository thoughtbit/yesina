package com.lkmotion.yesincar.service.impl;

import com.lkmotion.yesincar.constant.AccountStatusCode;
import com.lkmotion.yesincar.dao.DriverInfoDao;
import com.lkmotion.yesincar.dto.ResponseResult;
import com.lkmotion.yesincar.entity.DriverInfo;
import com.lkmotion.yesincar.request.DriverWorkStatusRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lizhaoteng
 **/
@Service
public class DriverWorkStautsHandleService {
    @Autowired
    private DriverInfoDao driverInfoDao;
    @Autowired
    private DriverInfoServiceImpl driverInfoService;

    public ResponseResult changeWorkStatus(DriverWorkStatusRequest driverWorkStatusRequest) {
        Integer id = driverWorkStatusRequest.getId();
        DriverInfo driverInfo = driverInfoDao.selectByPrimaryKey(id);
        if (null == driverInfo) {
            return ResponseResult.fail(AccountStatusCode.DRIVER_EMPTY.getCode(), AccountStatusCode.DRIVER_EMPTY.getValue());
        }
        //审核通过后，如果没有车辆，则提示
        Integer carId = driverInfo.getCarId();
        if (null == carId) {
            return ResponseResult.fail(AccountStatusCode.DRIVER_NO_CAR.getCode(), AccountStatusCode.DRIVER_NO_CAR.getValue());
        }

        if(null != driverWorkStatusRequest.getCsWorkStatus() ){
            Integer csWorkStatus = driverWorkStatusRequest.getCsWorkStatus();
            driverInfo.setCsWorkStatus(csWorkStatus);
        }
        if(null !=  driverWorkStatusRequest.getWorkStatus()){
            Integer workStatus = driverWorkStatusRequest.getWorkStatus();
            driverInfo.setWorkStatus(workStatus);
        }
        if(null != driverWorkStatusRequest.getIsFollowing()){
           Integer isFollowing =  driverWorkStatusRequest.getIsFollowing();
           driverInfo.setIsFollowing(isFollowing);
        }
        //改变司机状态
        int update = driverInfoService.updateDriverInfoByPhoneNum(driverInfo);
        if (1 != update) {
            return ResponseResult.fail("修改司机状态失败!");
        }
        return ResponseResult.success("");
    }
}
