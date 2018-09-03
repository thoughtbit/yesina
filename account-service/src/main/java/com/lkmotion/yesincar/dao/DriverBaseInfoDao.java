package com.lkmotion.yesincar.dao;

import com.lkmotion.yesincar.entity.DriverBaseInfo;
import com.lkmotion.yesincar.mapper.DriverBaseInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 *  司机信息
 * @author LiZhaoTeng
 **/
@Repository
public class DriverBaseInfoDao {
    @Autowired
    private DriverBaseInfoMapper driverBaseInfoMapper;

    public int deleteByPrimaryKey(Integer id) {
        return driverBaseInfoMapper.deleteByPrimaryKey(id);
    }


    public int insert(DriverBaseInfo record) {
        return driverBaseInfoMapper.insert(record);
    }


    public int insertSelective(DriverBaseInfo record) {
        return driverBaseInfoMapper.insertSelective(record);
    }


    public DriverBaseInfo selectByPrimaryKey(Integer id) {
        return driverBaseInfoMapper.selectByPrimaryKey(id);
    }


    public int updateByPrimaryKeySelective(DriverBaseInfo record) {
        return driverBaseInfoMapper.updateByPrimaryKeySelective(record);
    }


    public int updateByPrimaryKey(DriverBaseInfo record) {
        return driverBaseInfoMapper.updateByPrimaryKey(record);
    }
}
