package com.lkmotion.yesincar.controller;

import com.lkmotion.yesincar.context.ServerContext;
import com.lkmotion.yesincar.message.Message;
import com.lkmotion.yesincar.proto.MessageProto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dulin
 * @date 2018/8/21
 */
@RestController
@Slf4j
public class MessageController {
    @Autowired
    private ServerContext serverContext;

    @ResponseBody
    @RequestMapping(value = "/test", produces = "application/json; charset=utf-8")
    public String test(@RequestBody Message message) {
        MessageProto.RequestProto.Builder b = MessageProto.RequestProto.newBuilder();
        b.setCode(1);
        b.setMessage(message.getContent());
        serverContext.sendAll(b.build());
        return "1";
    }
}
