package com.lkmotion.yesincar.service.impl;

import com.lkmotion.yesincar.dao.CarInfoDao;
import com.lkmotion.yesincar.entity.CarInfo;
import com.lkmotion.yesincar.service.CarInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author lizhaoteng
 **/
@Service
public class CarInfoServiceImpl implements CarInfoService {
    @Autowired
    private CarInfoDao carInfoDao;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return carInfoDao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(CarInfo record) {
        return carInfoDao.insert(record);
    }

    @Override
    public int insertSelective(CarInfo record) {
        return carInfoDao.insertSelective(record);
    }

    @Override
    public CarInfo selectByPrimaryKey(Integer id) {
        return carInfoDao.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(CarInfo record) {
        return carInfoDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(CarInfo record) {
        return carInfoDao.updateByPrimaryKey(record);
    }

    @Override
    public int countCarInfo(Map<String, Object> param) {
        return carInfoDao.countCarInfo(param);
    }
}
