package com.lkmotion.yesincar.service.impl;

import com.lkmotion.yesincar.dao.PassengerAddressDao;
import com.lkmotion.yesincar.dto.ResponseResult;
import com.lkmotion.yesincar.entity.PassengerAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author lizhaoteng
 **/
@Service
public class PassengerAddressServiceImpl {
    @Autowired
    private PassengerAddressDao passengerAddressDao;

    public int deleteByPrimaryKey(Integer id) {
        return passengerAddressDao.deleteByPrimaryKey(id);
    }

    public int insert(PassengerAddress record) {
        return passengerAddressDao.insert(record);
    }

    public int insertSelective(PassengerAddress record) {
        return passengerAddressDao.insertSelective(record);
    }

    public PassengerAddress selectByPrimaryKey(Integer id) {
        return passengerAddressDao.selectByPrimaryKey(id);
    }

    public List<PassengerAddress> selectPassengerAddressList(Integer PassengerInfoId) {
        return passengerAddressDao.selectPassengerAddressList(PassengerInfoId);
    }

    public PassengerAddress selectByAddPassengerInfoId(PassengerAddress passengerInfoId) {
        return passengerAddressDao.selectByAddPassengerInfoId(passengerInfoId);
    }

    public int updateByPrimaryKeySelective(PassengerAddress record) {
        return passengerAddressDao.updateByPrimaryKeySelective(record);
    }

    public ResponseResult updatePassengerAddress(PassengerAddress passengerAddress) {
        int updateOrInsert;

        if (null != passengerAddress.getPassengerInfoId()) {
            PassengerAddress passengerAddressTemp = passengerAddressDao.selectByAddPassengerInfoId(passengerAddress);
            if (null == passengerAddressTemp) {
                passengerAddress.setCreateTime(new Date());
                updateOrInsert = passengerAddressDao.insertSelective(passengerAddress);
            } else {
                passengerAddress.setUpdateTime(new Date());
                updateOrInsert = passengerAddressDao.updatePassengerAddress(passengerAddress);
            }
        } else {
            passengerAddress.setCreateTime(new Date());
            updateOrInsert = passengerAddressDao.insertSelective(passengerAddress);
        }
        if (1 == updateOrInsert) {
            return ResponseResult.success("");
        } else {
            return ResponseResult.fail("修改或添加乘客地址信息失败!");
        }
    }

    public int updateByPrimaryKey(PassengerAddress record) {
        return passengerAddressDao.updateByPrimaryKey(record);
    }
}
