package com.lkmotion.yesincar.dao;

import com.lkmotion.yesincar.entity.DriverInfo;
import com.lkmotion.yesincar.mapper.DriverInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 *  司机端
 * @author LiZhaoTeng
 * @date  2018/08/08
 **/

@Repository
public class DriverInfoDao   {
    @Autowired
    private  DriverInfoMapper driverInfoMapper;

    public int deleteByPrimaryKey(Integer id) {
        return driverInfoMapper.deleteByPrimaryKey(id);
    }

    public int insert(DriverInfo record) {
        return driverInfoMapper.insert(record);
    }

    public int insertSelective(DriverInfo record) {
        return driverInfoMapper.insertSelective(record);
    }

    public DriverInfo selectByPrimaryKey(Integer id) {
        return driverInfoMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(DriverInfo record) {
        return driverInfoMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(DriverInfo record) {
        return driverInfoMapper.updateByPrimaryKey(record);
    }

    public DriverInfo queryDriverInfoByPhoneNum(String phoneNum) {
        return driverInfoMapper.queryDriverInfoByPhoneNum(phoneNum);
    }


    public int updateDriverInfoByPhoneNum(DriverInfo driverInfo) {
       return  driverInfoMapper.updateDriverInfoByPhoneNum(driverInfo);
    }


    public int updateCsWorkStatusByDriverId(Map<String, Object> param) {
        return driverInfoMapper.updateCsWorkStatusByDriverId(param);
    }
    public int queryDriverInfoByCarId(Integer carId){
        return  driverInfoMapper.queryDriverInfoByCarId(carId);
    }
}
