package com.lkmotion.yesincar.service;

import com.lkmotion.yesincar.entity.PassengerAddress;

import java.util.List;

/**
 * @author lizhaoteng
 **/
public interface PassengerAddressService  {

    int deleteByPrimaryKey(Integer id);

    int insert(PassengerAddress record);

    int insertSelective(PassengerAddress record);

    PassengerAddress selectByPrimaryKey(Integer id);

    List<PassengerAddress> selectPassengerAddressList(Integer PassengerInfoId);

    PassengerAddress selectByAddPassengerInfoId(PassengerAddress passengerInfoId);

    int updateByPrimaryKeySelective(PassengerAddress record);

    int updatePassengerAddress(PassengerAddress record);

    int updateByPrimaryKey(PassengerAddress record);
}
