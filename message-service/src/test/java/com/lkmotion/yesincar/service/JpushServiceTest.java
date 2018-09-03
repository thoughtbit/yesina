package com.lkmotion.yesincar.service;

import com.lkmotion.yesincar.dto.push.JpushMessage;
import com.lkmotion.yesincar.entity.PushAccount;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: chaopengfei
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JpushServiceTest {

    @Autowired
    private JpushService jpushService;

    @Test
    public void sendSingleMessage(){
        PushAccount pushAccount = new PushAccount();
        pushAccount.setIdentityStatus(1);
        pushAccount.setJpushId("aaa");

        JpushMessage jpushMessage = new JpushMessage();
        jpushMessage.setTitle("title");
        jpushMessage.setMessageType(101);
        jpushMessage.setMessageBody("我是中国额");
        // jpushService.sendSingleMessage(pushAccount,jpushMessage);
    }
}
