package com.lkmotion.yesincar.dao;

import com.lkmotion.yesincar.entity.CarInfo;
import com.lkmotion.yesincar.mapper.CarInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
  *汽车dao
 * @author LiZhaoTeng
 **/
@Repository
public class CarInfoDao  {
    @Autowired
    private CarInfoMapper carInfoMapper;
    public int deleteByPrimaryKey(Integer id) {
        return carInfoMapper.deleteByPrimaryKey(id);
    }
    public int insert(CarInfo record) {
        return carInfoMapper.insert(record);
    }
    public int insertSelective(CarInfo record) {
        return carInfoMapper.insertSelective(record);
    }
    public CarInfo selectByPrimaryKey(Integer id) {
        return carInfoMapper.selectByPrimaryKey(id);
    }
    public int updateByPrimaryKeySelective(CarInfo record) {
        return carInfoMapper.updateByPrimaryKeySelective(record);
    }
    public int updateByPrimaryKey(CarInfo record) {
        return carInfoMapper.updateByPrimaryKey(record);
    }

    public int countCarInfo(Map<String, Object> param) {
        return carInfoMapper.countCarInfo(param);
    }

}
