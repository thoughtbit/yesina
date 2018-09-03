package com.lkmotion.yesincar.mapper;

import com.lkmotion.yesincar.entity.PassengerWallet;

import java.util.Map;

/**
 * @author chaopengfei
 */
public interface PassengerWalletMapper {
    /**
     * 删除记录
     * @param id id
     * @return int
     */
    int deleteByPrimaryKey(Integer id);

    int insert(PassengerWallet record);

    int insertSelective(PassengerWallet record);

    PassengerWallet selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PassengerWallet record);

    int updateByPrimaryKey(PassengerWallet record);

    PassengerWallet selectByPassengerInfoId(Integer passengerInfoId);

    int updateBalanceByPassengerInfoId(Map<String,Object> param);

}