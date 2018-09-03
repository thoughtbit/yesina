package com.lkmotion.yesincar.dao;

import com.lkmotion.yesincar.entity.PassengerWalletRecord;
import com.lkmotion.yesincar.mapper.PassengerWalletRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author chaopengfei
 */
@Repository
public class PassengerWalletRecordDao {

    @Autowired
    private PassengerWalletRecordMapper passengerWalletRecordMapper;

    public int insertSelective(PassengerWalletRecord record){
        return passengerWalletRecordMapper.insertSelective(record);
    }

    public int updateByPrimaryKeySelective(PassengerWalletRecord record){
        return passengerWalletRecordMapper.updateByPrimaryKeySelective(record);
    }

    public PassengerWalletRecord selectByPrimaryKey(Integer id){
        return passengerWalletRecordMapper.selectByPrimaryKey(id);
    }

    public List<PassengerWalletRecord> selectPaidRecordByOrderId(Integer orderId){
        return passengerWalletRecordMapper.selectPaidRecordByOrderId(orderId);
    }
}
