package com.lkmotion.yesincar.mapper;

import com.lkmotion.yesincar.entity.SmsTemplate;

/**
 * @author chaopengfei
 */
public interface SmsTemplateMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(SmsTemplate record);

    int insertSelective(SmsTemplate record);

    SmsTemplate selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SmsTemplate record);

    int updateByPrimaryKey(SmsTemplate record);

    SmsTemplate selectByTemplateId(String templeId);
}