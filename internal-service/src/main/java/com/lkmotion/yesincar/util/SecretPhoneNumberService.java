package com.lkmotion.yesincar.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dyplsapi.model.v20170525.*;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 功能描述
 * @author Li----Heng
 */
@Service
public class SecretPhoneNumberService {

    private String POOL_KEY="FC100000032650600";
    private String PRODUCT ="Dyplsapi";
    private String DOMAIN = "dyplsapi.aliyuncs.com";
    private String KEY="LTAI9YPxRRWkDwnK";
    private String SECRET="O2AVJkmpQ3Ilg7tiy6xLcGC7e5ti08";

    /**
     * AXB绑定
     *
     * @param aPhone
     *            司机电话
     * @param bPhone
     *            乘客电话
     * @param expiration
     *            绑定关系对应的失效时间-不能早于当前系统时间
     * @return
     * @throws ClientException
     */

    public BindAxbResponse bindAxb(String aPhone, String bPhone, Date expiration) throws ClientException {
        IAcsClient acsClient = newClient();
        // 组装请求对象
        BindAxbRequest request = new BindAxbRequest();
        // 必填:对应的号池Key
        request.setPoolKey(POOL_KEY);
        // 必填:AXB关系中的A号码
        request.setPhoneNoA(aPhone);
        // 必填:AXB关系中的B号码
        request.setPhoneNoB(bPhone);
        // 必填:绑定关系对应的失效时间-不能早于当前系统时间
        request.setExpiration(TimeUtils.getDefaultFormate(expiration));
        // 可选:是否需要录制音频-默认是false
        request.setIsRecordingEnabled(true);
        // 外部业务自定义ID属性
        request.setOutId("yourOutId");

        return acsClient.getAcsResponse(request);
    }

    /**
     * AXN绑定
     *
     * @return
     * @throws ClientException
     */
    public BindAxnResponse bindAxn(String aPhone, String bPhone, Date expiration) throws ClientException {
        IAcsClient acsClient = newClient();

        // 组装请求对象-具体描述见控制台-文档部分内容
        BindAxnRequest request = new BindAxnRequest();
        // 必填:对应的号池Key
        request.setPoolKey(POOL_KEY);
        // 必填:AXN关系中的A号码
        request.setPhoneNoA(aPhone);
        // 可选:AXN中A拨打X的时候转接到的默认的B号码,如果不需要则不设置
        request.setPhoneNoB(bPhone);
        // 必填:绑定关系对应的失效时间-不能早于当前系统时间
        request.setExpiration(TimeUtils.getDefaultFormate(expiration));
        // 可选:是否需要录制音频-默认是false
        request.setIsRecordingEnabled(true);
        // 外部业务自定义ID属性
        request.setOutId("yourOutId");

        return acsClient.getAcsResponse(request);
    }

    /**
     * 更新AXB绑定
     *
     * @param subsId
     *            创建绑定关系API接口所返回的订购关系ID
     * @param secretNo
     *            创建绑定关系API接口所返回的X号码
     * @param expiration
     *            绑定关系对应的失效时间-不能早于当前系统时间
     * @return
     * @throws ClientException
     */
    public UpdateSubscriptionResponse updateSubscription(String subsId, String secretNo, Date expiration)
            throws ClientException {
        IAcsClient acsClient = newClient();
        // 组装请求对象-具体描述见控制台-文档部分内容
        UpdateSubscriptionRequest request = new UpdateSubscriptionRequest();
        // 绑定关系对应的号池Key
        request.setPoolKey(POOL_KEY);
        // 必填:绑定关系ID
        request.setSubsId(subsId);
        // 必填:绑定关系对应的X号码
        request.setPhoneNoX(secretNo);
        // 必填:操作类型指令支持
        request.setOperateType("updateExpire");
        // 必填:绑定关系对应的失效时间-不能早于当前系统时间
        request.setExpiration(TimeUtils.getDefaultFormate(expiration));

        return acsClient.getAcsResponse(request);
    }

    /**
     * 关系解绑
     *
     * @return
     * @throws ClientException
     */
    public UnbindSubscriptionResponse unbind(String subsId, String secretNo) throws ClientException {

        IAcsClient acsClient = newClient();
        // 组装请求对象
        UnbindSubscriptionRequest request = new UnbindSubscriptionRequest();
        // 必填:对应的号池Key
        request.setPoolKey(POOL_KEY);
        // 必填-分配的X小号-对应到绑定接口中返回的secretNo;
        request.setSecretNo(secretNo);
        // 必填-绑定关系对应的ID-对应到绑定接口中返回的subsId;
        request.setSubsId(subsId);

        return acsClient.getAcsResponse(request);
    }

    /**
     * 初始化ascClient
     *
     * @return
     * @throws ClientException
     */
    private IAcsClient newClient() throws ClientException {
        // 可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", KEY, SECRET);

        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", PRODUCT, DOMAIN);
        return new DefaultAcsClient(profile);
    }

}
