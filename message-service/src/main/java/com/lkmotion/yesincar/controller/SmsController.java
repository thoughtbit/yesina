package com.lkmotion.yesincar.controller;

import com.lkmotion.yesincar.dto.ResponseResult;
import com.lkmotion.yesincar.dto.SmsSendRequest;
import com.lkmotion.yesincar.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chaopengfei
 */
@RestController
@RequestMapping("/sms")
public class SmsController {

    @Autowired
    private SmsService smsService;

    @RequestMapping(value = "/send",method = RequestMethod.POST)
    public ResponseResult send(@RequestBody SmsSendRequest smsSendRequest){
        smsService.sendSms(smsSendRequest);
        return  ResponseResult.success("");
    }
}
