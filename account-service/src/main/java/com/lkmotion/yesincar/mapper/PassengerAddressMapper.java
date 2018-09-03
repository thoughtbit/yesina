package com.lkmotion.yesincar.mapper;

import com.lkmotion.yesincar.entity.PassengerAddress;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lizhaoteng
 */
@Service

public interface PassengerAddressMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(PassengerAddress record);

    int insertSelective(PassengerAddress record);

    PassengerAddress selectByPrimaryKey(Integer id);

    PassengerAddress selectByPassengerInfoId(Integer passengerInfoId);

    List<PassengerAddress> selectPassengerAddressList(Integer PassengerInfoId);

    PassengerAddress selectByAddPassengerInfoId(PassengerAddress passengerInfoId);

    int updateByPrimaryKeySelective(PassengerAddress record);

    int updatePassengerAddress(PassengerAddress record);

    int updateByPrimaryKey(PassengerAddress record);
}
