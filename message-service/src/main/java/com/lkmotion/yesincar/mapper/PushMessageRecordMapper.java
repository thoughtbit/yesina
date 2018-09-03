package com.lkmotion.yesincar.mapper;

import com.lkmotion.yesincar.entity.PushMessageRecord;

/**
 * @author chaopengfei
 */
public interface PushMessageRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(PushMessageRecord record);

    PushMessageRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PushMessageRecord record);

}