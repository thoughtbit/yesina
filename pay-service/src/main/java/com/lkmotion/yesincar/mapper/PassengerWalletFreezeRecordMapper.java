package com.lkmotion.yesincar.mapper;

import com.lkmotion.yesincar.entity.PassengerWalletFreezeRecord;

import java.util.Map;

/**
 * @author chaopengfei
 */
public interface PassengerWalletFreezeRecordMapper {
    /**
     * 删除
     * @param id id
     */
    int deleteByPrimaryKey(Integer id);

    int insert(PassengerWalletFreezeRecord record);

    int insertSelective(PassengerWalletFreezeRecord record);

    PassengerWalletFreezeRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PassengerWalletFreezeRecord record);

    int updateByPrimaryKey(PassengerWalletFreezeRecord record);

    PassengerWalletFreezeRecord selectByOrderIdAndYid(Map<String,Object> param);
}