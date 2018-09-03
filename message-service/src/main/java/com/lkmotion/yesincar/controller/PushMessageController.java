package com.lkmotion.yesincar.controller;

import com.lkmotion.yesincar.constant.JpushConfig;
import com.lkmotion.yesincar.dto.ResponseResult;
import com.lkmotion.yesincar.dto.push.PushRequest;
import com.lkmotion.yesincar.service.JpushService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chaopengfei
 */
@RestController
@RequestMapping("/push")
@Slf4j
public class PushMessageController {

    @Autowired
    private JpushService jpushService;

    @RequestMapping("/message")
    public ResponseResult pushWithAlias(@RequestBody PushRequest pushRequest){

        log.info("极光消息："+JSONObject.fromObject(pushRequest).toString());
        return jpushService.sendSingleJpushToApp(pushRequest,JpushConfig.CHANNEL_MESSAGE);
    }

    @RequestMapping("/notice")
    public ResponseResult noticeWithAlias(@RequestBody PushRequest pushRequest){
        log.info("极光通知："+JSONObject.fromObject(pushRequest).toString());
        return jpushService.sendSingleJpushToApp(pushRequest,JpushConfig.CHANNEL_NOTICE);
    }

}
