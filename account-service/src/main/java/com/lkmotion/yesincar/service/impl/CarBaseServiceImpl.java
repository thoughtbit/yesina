package com.lkmotion.yesincar.service.impl;

import com.lkmotion.yesincar.constant.AccountStatusCode;
import com.lkmotion.yesincar.dao.CarBaseInfoDao;
import com.lkmotion.yesincar.dao.CarInfoDao;
import com.lkmotion.yesincar.dto.ResponseResult;
import com.lkmotion.yesincar.dto.car.CarBaseInfoView;
import com.lkmotion.yesincar.entity.CarBaseInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 汽车服务
 *
 * @author LiZhaoTeng
 **/
@Service
@Slf4j
public class CarBaseServiceImpl {
    @Autowired
    private CarInfoDao carInfoDao;
    @Autowired
    private CarBaseInfoDao carBaseInfoDao;

    public int updateCarBaseInfoView(CarBaseInfo carBaseInfo) {
        return carBaseInfoDao.updateByPrimaryKeySelective(carBaseInfo);
    }

    public ResponseResult addCarBaseInfo(CarBaseInfoView carBaseInfoView) {
        Map<String, Object> param = new HashMap<>(16);
        param.put("carPlateNumber", carBaseInfoView.getCarInfo().getPlateNumber());
        if (carInfoDao.countCarInfo(param) > 0) {
            return ResponseResult.fail(AccountStatusCode.DUPLICATE_PLATE_NUMBER.getCode(),AccountStatusCode.DUPLICATE_PLATE_NUMBER.getValue(), carBaseInfoView.getCarInfo().getPlateNumber());
        }

        carBaseInfoView.getCarInfo().setUpdateTime(new Date());
        carBaseInfoView.getCarBaseInfo().setRegisterTime(new Date());
        carBaseInfoView.getCarInfo().setCreateTime(new Date());
        int carInfoCode = carInfoDao.insertSelective(carBaseInfoView.getCarInfo());
        if (1 != carInfoCode) {
            return ResponseResult.fail("添加carInfo失败");
        }
        carBaseInfoView.getCarBaseInfo().setId(carBaseInfoView.getCarInfo().getId());
        carBaseInfoView.getCarBaseInfo().setCreateTime(new Date());
        int carBaseInfoCode = carBaseInfoDao.insertSelective(carBaseInfoView.getCarBaseInfo());
        if (1 != carBaseInfoCode) {
            return ResponseResult.fail("添加carBaseInfo失败");
        }
        return ResponseResult.success(carBaseInfoView);
    }
}
