package com.lkmotion.yesincar.service;

import com.lkmotion.yesincar.dto.push.PushLoopBatchRequest;
import com.lkmotion.yesincar.dto.push.PushLoopMessageDto;
import com.lkmotion.yesincar.entity.PushLoopMessage;

import java.util.List;

/**
 * @author chaopengfei
 */
public interface PushLoopService {

    /**
     * 插入单条消息
     * @param pushLoopMessage
     * @return
     */
    int insert(PushLoopMessage pushLoopMessage);

    /**
     * 插入批量消息
     * @param pushLoopBatchRequest
     * @return
     */
    int insertBatch(PushLoopBatchRequest pushLoopBatchRequest);

    /**
     * 根据接受者身份，接受者Id查询消息
     * @param acceptIdentity
     * @param acceptId
     * @return
     */
    public List<PushLoopMessageDto> selectUnreadMessageListByIdentityAndAcceptId(Integer acceptIdentity, String acceptId);
}
