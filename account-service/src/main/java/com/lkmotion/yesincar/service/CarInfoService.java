package com.lkmotion.yesincar.service;

import com.lkmotion.yesincar.entity.CarInfo;

import java.util.Map;

/**
 * @author lizhaoteng
 **/
public interface CarInfoService  {
    int deleteByPrimaryKey(Integer id);

    int insert(CarInfo record);

    int insertSelective(CarInfo record);

    CarInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CarInfo record);

    int updateByPrimaryKey(CarInfo record);

    int countCarInfo(Map<String, Object> param);
}
