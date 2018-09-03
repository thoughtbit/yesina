package com.lkmotion.yesincar.dao;

import com.lkmotion.yesincar.entity.PassengerInfo;
import com.lkmotion.yesincar.mapper.PassengerInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 乘客端
 *
 * @author LiZhaoTeng
 * @date 2018/08/08
 **/
@Repository
public class PassengerInfoDao {
    @Autowired
    private PassengerInfoMapper passengerInfoDao;

    public PassengerInfo queryPassengerInfoById(Integer passengerId) {
        return passengerInfoDao.selectByPrimaryKey(passengerId);
    }

    public PassengerInfo queryPassengerInfoByPhoneNum(String phoneNum) {
        return passengerInfoDao.queryPassengerInfoByPhoneNum(phoneNum);
    }

    public int deleteByPrimaryKey(Integer id) {
        return passengerInfoDao.deleteByPrimaryKey(id);
    }

    public int insert(PassengerInfo record) {
        return passengerInfoDao.insert(record);
    }

    public int insertSelective(PassengerInfo record) {
        return passengerInfoDao.insertSelective(record);
    }

    public PassengerInfo selectByPrimaryKey(Integer id) {
        return passengerInfoDao.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(PassengerInfo record) {
        return passengerInfoDao.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(PassengerInfo record) {
        return passengerInfoDao.updateByPrimaryKey(record);
    }

    public void updatePassengerInfoLoginTime(Integer passengerId) {
        passengerInfoDao.updatePassengerInfoLoginTime(passengerId);
    }
}
