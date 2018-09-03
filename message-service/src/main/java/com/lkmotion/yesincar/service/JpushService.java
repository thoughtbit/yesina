package com.lkmotion.yesincar.service;

import com.lkmotion.yesincar.dto.ResponseResult;
import com.lkmotion.yesincar.dto.push.PushRequest;

/**
 * @author chaopengfei
 */
public interface JpushService {

    ResponseResult sendSingleJpushToApp(PushRequest pushRequest,int channelType);

}
