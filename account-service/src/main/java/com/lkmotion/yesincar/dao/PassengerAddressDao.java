package com.lkmotion.yesincar.dao;

import com.lkmotion.yesincar.entity.PassengerAddress;
import com.lkmotion.yesincar.mapper.PassengerAddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *  乘客地址
 * @author LiZhaoTeng
 * @date  2018/08/13
 **/
@Repository
public class PassengerAddressDao {
    @Autowired
    private PassengerAddressMapper passengerAdressDao;

    public int deleteByPrimaryKey(Integer id) {
        return passengerAdressDao.deleteByPrimaryKey(id);
    }
    public int insert(PassengerAddress record) {
        return passengerAdressDao.insert(record);
    }
    public int insertSelective(PassengerAddress record) {
        return passengerAdressDao.insertSelective(record);
    }
    public PassengerAddress selectByPrimaryKey(Integer id) {
        return passengerAdressDao.selectByPrimaryKey(id);
    }
    public PassengerAddress selectByPassengerInfoId(Integer passengerInfoId) {
        return passengerAdressDao.selectByPassengerInfoId(passengerInfoId);
    }
    public List<PassengerAddress> selectPassengerAddressList(Integer PassengerInfoId) {
        return passengerAdressDao.selectPassengerAddressList(PassengerInfoId);
    }
    public PassengerAddress selectByAddPassengerInfoId(PassengerAddress passengerInfoId ) {
        return passengerAdressDao.selectByAddPassengerInfoId(passengerInfoId);
    }
    public int updateByPrimaryKeySelective(PassengerAddress record) {
        return passengerAdressDao.updateByPrimaryKeySelective(record);
    }
    public int updatePassengerAddress(PassengerAddress record) {
        return passengerAdressDao.updatePassengerAddress(record);
    }

    public int updateByPrimaryKey(PassengerAddress record) {
        return passengerAdressDao.updateByPrimaryKey(record);
    }
}
