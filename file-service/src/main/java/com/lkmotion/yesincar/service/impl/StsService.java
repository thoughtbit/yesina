package com.lkmotion.yesincar.service.impl;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.lkmotion.yesincar.util.OssConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述
 *
 * @author liheng
 * @date 2018/8/22
 */
@Service
public class StsService {

    @Autowired
    private OssConfig ossConfig;
    public static final String REGION_CN_HANGZHOU = "cn-hangzhou";

    /**
     * 获取阿里云oss sts临时权限
     * @param roleSessionName
     * 			临时Token的会话名称
     * @return	令牌
     * @throws ClientException
     */
    public AssumeRoleResponse assumeRole(String roleSessionName) throws ClientException {
        IClientProfile profile = DefaultProfile.getProfile(REGION_CN_HANGZHOU, "LTAIM3gjTsWs3bcC",
                "d8sMY6xUK4mJqF31YbKU1sWMeEP4Qw");
        DefaultAcsClient client = new DefaultAcsClient(profile);
        // 创建一个 AssumeRoleRequest 并设置请求参数
        final AssumeRoleRequest request = new AssumeRoleRequest();
        request.setVersion(null);
        request.setMethod(MethodType.POST);
        // 此处必须为 HTTPS
        request.setProtocol(ProtocolType.HTTPS);
        // RoleArn 需要在 RAM 控制台上获取
        request.setRoleArn("acs:ram::1141917368151612:role/yesincar-test-source-write");
        // RoleSessionName 是临时Token的会话名称，自己指定用于标识你的用户，主要用于审计，或者用于区分Token颁发给谁
        // 但是注意RoleSessionName的长度和规则，不要有空格，只能有'-' '_' 字母和数字等字符
        request.setRoleSessionName(roleSessionName);
        // 授权策略
        request.setPolicy(null);
        // 设置token时间
        long sessionTime = 900;
        request.setDurationSeconds(sessionTime);
        // 发起请求，并得到response
        return client.getAcsResponse(request);
    }

    public String readJson(String path) {
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        // 返回值,使用StringBuffer
        StringBuffer data = new StringBuffer();
        //
        try {
            inputStream = new ClassPathResource(path).getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);
            // 每次读取文件的缓存
            String temp = null;
            while ((temp = reader.readLine()) != null) {
                data.append(temp);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭文件流
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return data.toString();
    }


}