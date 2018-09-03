package com.lkmotion.yesincar.service.impl;

import cn.jiguang.common.ClientConfig;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import com.lkmotion.yesincar.constant.JpushConfig;
import com.lkmotion.yesincar.constant.JpushEnum;
import com.lkmotion.yesincar.constatnt.AudienceEnum;
import com.lkmotion.yesincar.constatnt.IdentityEnum;
import com.lkmotion.yesincar.constatnt.PlatformEnum;
import com.lkmotion.yesincar.dao.PushAccountDao;
import com.lkmotion.yesincar.dao.PushMessageRecordDao;
import com.lkmotion.yesincar.dto.JpushInfo;
import com.lkmotion.yesincar.dto.ResponseResult;
import com.lkmotion.yesincar.dto.push.JpushMessage;
import com.lkmotion.yesincar.dto.push.PushRequest;
import com.lkmotion.yesincar.entity.PushAccount;
import com.lkmotion.yesincar.entity.PushMessageRecord;
import com.lkmotion.yesincar.service.JpushService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chaopengfei
 */
@Service
@Slf4j
public class JpushServiceImpl implements JpushService {

    @Value("${jpush.passenger.master-secret}")
    private String passengerMasterSecret;

    @Value("${jpush.passenger.app-key}")
    private String passengerAppKey;

    @Value("${jpush.largeScreen.master-secret}")
    private String largeScreenMasterSecret;

    @Value("${jpush.largeScreen.app-key}")
    private String largeScreenAppKey;

    @Value("${jpush.driver.master-secret}")
    private String driverMasterSecret;

    @Value("${jpush.driver.app-key}")
    private String driverAppKey;

    @Value("${jpush.carScreen.master-secret}")
    private String carScreenMasterSecret;

    @Value("${jpush.carScreen.app-key}")
    private String carScreenAppKey;

    @Autowired
    private PushMessageRecordDao pushMessageRecordDao;

    @Autowired
    private  PushAccountDao pushAccountDao;

    @Override
    public ResponseResult sendSingleJpushToApp(PushRequest pushRequest,int channelType){
        int acceptIdentity = pushRequest.getAcceptIdentity();
        String acceptId = pushRequest.getAcceptId();

        List<PushAccount> pushAccounts = pushAccountDao.selectByIdentityAndYid(acceptIdentity,acceptId);
        PushAccount pushAccount;
        if(!pushAccounts.isEmpty()){
            pushAccount = pushAccounts.get(0);
        } else {
            return ResponseResult.fail(JpushEnum.PUSH_ACCOUNT_EMPTY.getValue());
        }
        String source = pushAccount.getSource();
        String jpushId = pushAccount.getJpushId();
        Integer audience = pushAccount.getAudience();

        String title = pushRequest.getTitle();
        int messageType = pushRequest.getMessageType();
        String messageBody = pushRequest.getMessageBody();
        JpushMessage jpushMessage = new JpushMessage();
        jpushMessage.setTitle(title);
        jpushMessage.setMessageType(messageType);
        jpushMessage.setMessageBody(messageBody);

        if(channelType == JpushConfig.CHANNEL_MESSAGE){
            return sendSingleMessage(acceptIdentity,source,jpushId,jpushMessage,audience,pushRequest.getSendId(),pushRequest.getSendIdentity());
        }

        if(channelType == JpushConfig.CHANNEL_NOTICE){
            return sendSingleNotice(acceptIdentity,source,jpushId,jpushMessage,audience,pushRequest.getSendId(),pushRequest.getSendIdentity());
        }

        return ResponseResult.fail(JpushEnum.PUSH_CHANNEL_EMPTY.getCode(),JpushEnum.PUSH_CHANNEL_EMPTY.getValue());
    }

    private ResponseResult sendSingleMessage(int pushAccountIdentity , String targetPlatform , String jpushId ,
                                             JpushMessage jpushMessage , int audience ,String sendId,Integer sendIdentity) {
        int sendResult = 0;
        try {
            String appKey = "";
            String masterSecret = "";
            JpushInfo jpushInfo = getJpushInfo(pushAccountIdentity);
            if (null == jpushInfo){
                return ResponseResult.fail(JpushEnum.IDENTITY_EMPTY.getCode(),JpushEnum.IDENTITY_EMPTY.getValue());
            }else {
                appKey = jpushInfo.getAppKey();
                masterSecret = jpushInfo.getMasterSecret();
            }

            JPushClient jpushClient = new JPushClient(masterSecret, appKey, null, ClientConfig.getInstance());
            PushResult pr;
            if(PlatformEnum.IOS.getValue().equalsIgnoreCase(targetPlatform)) {
                if(AudienceEnum.ALIAS.getCode() == audience){
                    pr = jpushClient.sendIosMessageWithAlias(jpushMessage.getTitle(), jpushMessage.getMessageBody(), jpushId);
                }else if (AudienceEnum.REGISTRATION_ID.getCode() == audience){
                    pr = jpushClient.sendIosMessageWithRegistrationID(jpushMessage.getTitle(), jpushMessage.getMessageBody(), jpushId);
                }else{
                    return ResponseResult.fail(JpushEnum.AUDIENCE_ERROR.getCode(),JpushEnum.AUDIENCE_ERROR.getValue());
                }

            }else if (PlatformEnum.ANDROID.getValue().equalsIgnoreCase(targetPlatform)){

                if(AudienceEnum.ALIAS.getCode() == audience){
                    pr = jpushClient.sendAndroidMessageWithAlias(jpushMessage.getTitle(), jpushMessage.getMessageBody(), jpushId);
                }else if (AudienceEnum.REGISTRATION_ID.getCode() == audience){
                    pr = jpushClient.sendAndroidMessageWithRegistrationID(jpushMessage.getTitle(), jpushMessage.getMessageBody(), jpushId);
                }else{
                    return ResponseResult.fail(JpushEnum.AUDIENCE_ERROR.getCode(),JpushEnum.AUDIENCE_ERROR.getValue());
                }
            }else{
                return ResponseResult.fail(JpushEnum.PLATFORM_ERROR.getCode(),JpushEnum.PLATFORM_ERROR.getValue());
            }
            sendResult = 1;
        } catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
            sendResult = 0;
            return ResponseResult.fail(JpushEnum.EXCEPTION.getCode(),JpushEnum.EXCEPTION.getValue());
        }finally {
            insertPushMessageRecord(sendResult,jpushId,jpushMessage.getMessageBody(),jpushMessage.getMessageType(),targetPlatform,sendId,sendIdentity);
        }
        return ResponseResult.fail(JpushEnum.OK.getCode(),JpushEnum.OK.getValue());
    }

    private ResponseResult sendSingleNotice(int pushAccountIdentity , String targetPlatform , String jpushId ,
                                             JpushMessage jpushMessage , int audience ,String sendId,
                                            Integer sendIdentity) {

        //发送结果
        int sendResult = 0;
        try {
            String appKey = "";
            String masterSecret = "";
            JpushInfo jpushInfo = getJpushInfo(pushAccountIdentity);
            if (null == jpushInfo){
                return ResponseResult.fail(JpushEnum.IDENTITY_EMPTY.getCode(),JpushEnum.IDENTITY_EMPTY.getValue());
            }else {
                appKey = jpushInfo.getAppKey();
                masterSecret = jpushInfo.getMasterSecret();
            }
            Map<String,String> map = new HashMap<>(1);
            map.put("messageType",jpushMessage.getMessageType()+"");
            map.put("messageBody",jpushMessage.getMessageBody());

            JPushClient jpushClient = new JPushClient(masterSecret, appKey, null, ClientConfig.getInstance());
            PushResult pr;
            if(PlatformEnum.IOS.getValue().equalsIgnoreCase(targetPlatform)) {
                if(AudienceEnum.ALIAS.getCode() == audience){
                    pr = jpushClient.sendIosNotificationWithAlias(jpushMessage.getTitle(),map,jpushId);
                }else if (AudienceEnum.REGISTRATION_ID.getCode() == audience){
                    pr = jpushClient.sendIosNotificationWithRegistrationID(jpushMessage.getTitle(),map,jpushId);
                }else{
                    return ResponseResult.fail(JpushEnum.AUDIENCE_ERROR.getCode(),JpushEnum.AUDIENCE_ERROR.getValue());
                }

            }else if (PlatformEnum.ANDROID.getValue().equalsIgnoreCase(targetPlatform)){

                if(AudienceEnum.ALIAS.getCode() == audience){
                    pr = jpushClient.sendAndroidNotificationWithAlias(jpushMessage.getTitle(),jpushMessage.getTitle(),map,jpushId);
                }else if (AudienceEnum.REGISTRATION_ID.getCode() == audience){
                    pr = jpushClient.sendAndroidNotificationWithRegistrationID(jpushMessage.getTitle(),jpushMessage.getTitle(),map,jpushId);
                }else{
                    return ResponseResult.fail(JpushEnum.AUDIENCE_ERROR.getCode(),JpushEnum.AUDIENCE_ERROR.getValue());
                }
            }else{
                return ResponseResult.fail(JpushEnum.PLATFORM_ERROR.getCode(),JpushEnum.PLATFORM_ERROR.getValue());
            }
            sendResult = 1;
        } catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
            sendResult = 0;
            return ResponseResult.fail(JpushEnum.EXCEPTION.getCode(),JpushEnum.EXCEPTION.getValue());
        }finally {
            insertPushMessageRecord(sendResult,jpushId,jpushMessage.getMessageBody(),jpushMessage.getMessageType(),targetPlatform,sendId,sendIdentity);
        }
        return ResponseResult.fail(JpushEnum.OK.getCode(),JpushEnum.OK.getValue());
    }

    private JpushInfo getJpushInfo(int pushAccountIdentity){
        JpushInfo jpushInfo = new JpushInfo();
        String appKey = "";
        String masterSecret = "";
        if (pushAccountIdentity == IdentityEnum.PASSENGER.getCode()){
            appKey = passengerAppKey;
            masterSecret = passengerMasterSecret;
        }else if(pushAccountIdentity == IdentityEnum.DRIVER.getCode()){
            appKey = driverAppKey;
            masterSecret = driverMasterSecret;
        }else if(pushAccountIdentity == IdentityEnum.LARGE_SCREEN.getCode()){
            appKey = largeScreenAppKey;
            masterSecret = largeScreenMasterSecret;
        }else if(pushAccountIdentity == IdentityEnum.CAR_SCREEN.getCode()){
            appKey = carScreenAppKey;
            masterSecret = carScreenMasterSecret;
        }else {
            return null;
        }
        jpushInfo.setAppKey(appKey);
        jpushInfo.setMasterSecret(masterSecret);
        return jpushInfo;
    }


    private void insertPushMessageRecord(int sendResult,String jpushId,String messageBody,int messageType,String targetPlatform,
                                         String sendId,int sendIdentity){
        PushMessageRecord pushMessageRecord = new PushMessageRecord();
        pushMessageRecord.setSendResult(sendResult);
        pushMessageRecord.setCreateTime(new Date());
        pushMessageRecord.setJpushId(jpushId);
        pushMessageRecord.setMessageBody(messageBody);
        pushMessageRecord.setMessageType(messageType);
        pushMessageRecord.setSource(targetPlatform);
        pushMessageRecord.setSendId(sendId);
        pushMessageRecord.setSendIdentity(sendIdentity);

        pushMessageRecordDao.insert(pushMessageRecord);

    }
}
