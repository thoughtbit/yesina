package com.lkmotion.yesincar.mapper;

import com.lkmotion.yesincar.dto.push.PushLoopMessageDto;
import com.lkmotion.yesincar.entity.PushLoopMessage;

import java.util.List;

/**
 * @author lizhaoteng
 */
public interface PushLoopMessageMapper {

    int deleteByPrimaryKey(Long id);

    int insertSelective(PushLoopMessage record);

    int insertBatch(List<PushLoopMessage> list);

    PushLoopMessage selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PushLoopMessage record);

    List<PushLoopMessageDto> selectUnreadMessageListByIdentityAndAcceptId(PushLoopMessage pushLoopMessage);

    int updateReadById(List<Integer> ids);

}
