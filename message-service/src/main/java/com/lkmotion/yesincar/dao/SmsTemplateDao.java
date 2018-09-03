package com.lkmotion.yesincar.dao;

import com.lkmotion.yesincar.entity.SmsTemplate;
import com.lkmotion.yesincar.mapper.SmsTemplateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author chaopengfei
 */
@Repository
public class SmsTemplateDao {

    @Autowired
    private SmsTemplateMapper smsTemplateMapper;

    public SmsTemplate findByTemplateId(String templateId) {
        return smsTemplateMapper.selectByTemplateId(templateId);
    }
}
