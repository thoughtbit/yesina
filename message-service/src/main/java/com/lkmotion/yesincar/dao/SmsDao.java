package com.lkmotion.yesincar.dao;

import com.lkmotion.yesincar.entity.Sms;
import com.lkmotion.yesincar.mapper.SmsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author chaopengfei
 */
@Repository
public class SmsDao {
    @Autowired
    private SmsMapper smsMapper;

    public int insert(Sms sms){
        return smsMapper.insertSelective(sms);
    }
}
