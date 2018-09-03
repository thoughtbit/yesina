package com.lkmotion.yesincar.mapper;

import com.lkmotion.yesincar.entity.Sms;

/**
 * @author chaopengfei
 */
public interface SmsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Sms record);

    int insertSelective(Sms record);

    Sms selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Sms record);

    int updateByPrimaryKey(Sms record);
}