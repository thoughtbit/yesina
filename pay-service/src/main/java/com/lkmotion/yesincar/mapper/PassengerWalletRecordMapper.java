package com.lkmotion.yesincar.mapper;

import com.lkmotion.yesincar.entity.PassengerWalletRecord;

import java.util.List;

/**
 * @author chaopengfei
 */
public interface PassengerWalletRecordMapper {

    int deleteByPrimaryKey(Integer id);

    int insertSelective(PassengerWalletRecord record);

    PassengerWalletRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PassengerWalletRecord record);

    List<PassengerWalletRecord> selectPaidRecordByOrderId(Integer orderId);
}