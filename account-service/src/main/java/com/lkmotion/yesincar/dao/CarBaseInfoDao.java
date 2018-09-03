package com.lkmotion.yesincar.dao;

import com.lkmotion.yesincar.entity.CarBaseInfo;
import com.lkmotion.yesincar.mapper.CarBaseInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author LiZhaoTeng
 **/
@Repository
public class CarBaseInfoDao  {
    @Autowired
    private CarBaseInfoMapper carBaseInfoMapper;

    public int deleteByPrimaryKey(Integer id) {
        return carBaseInfoMapper.deleteByPrimaryKey(id);
    }
    public int insert(CarBaseInfo record) {
        return carBaseInfoMapper.insert(record);
    }
    public int insertSelective(CarBaseInfo record) {
        return carBaseInfoMapper.insertSelective(record);
    }
    public CarBaseInfo selectByPrimaryKey(Integer id) {
        return carBaseInfoMapper.selectByPrimaryKey(id);
    }
    public int updateByPrimaryKeySelective(CarBaseInfo record) {
        return carBaseInfoMapper.updateByPrimaryKeySelective(record);
    }
    public int updateByPrimaryKey(CarBaseInfo record) {
        return carBaseInfoMapper.updateByPrimaryKey(record);
    }
}
