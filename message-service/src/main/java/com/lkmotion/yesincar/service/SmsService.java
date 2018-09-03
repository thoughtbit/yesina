package com.lkmotion.yesincar.service;

import com.lkmotion.yesincar.dto.SmsSendRequest;

/**
 * @author chaopengfei
 */
public interface SmsService {

    int sendSms(SmsSendRequest request);

}
