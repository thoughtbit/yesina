package com.lkmotion.yesincar.dao;

import com.lkmotion.yesincar.entity.PushMessageRecord;
import com.lkmotion.yesincar.mapper.PushMessageRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author chaopengfei
 */
@Repository
public class PushMessageRecordDao {

    @Autowired
    private PushMessageRecordMapper pushMessageRecordMapper;

    public int insert(PushMessageRecord pushMessageRecord){
        return pushMessageRecordMapper.insertSelective(pushMessageRecord);
    }
}
