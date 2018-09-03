package com.lkmotion.yesincar.controller;

import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.lkmotion.yesincar.dto.ResponseResult;
import com.lkmotion.yesincar.service.impl.StsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述
 *
 * @author liheng
 * @date 2018/8/22
 */
@RestController
@RequestMapping("/sts")
public class StsController {
    @Autowired
    private StsService stsService;

    @GetMapping({"/authorization", "/authorization/{tokenName}"})
    public ResponseResult getALiYunOSSToken(@PathVariable(value = "tokenName", required = false) String tokenName){
        try {
            // 获取临时授权token
            AssumeRoleResponse assumeRoleResponse = stsService.assumeRole(tokenName);
            // 构造返回参数
            Map<String,String> map = new HashMap<String,String>();
            // 根节点
            map.put("endPoint", "https://oss-cn-hangzhou.aliyuncs.com/");
              // 空间名称
            map.put("bucketName", "yesincar-test-source");
            // 账号ID
            map.put("accessKeyId", assumeRoleResponse.getCredentials().getAccessKeyId());
            // 密码
            map.put("accessKeySecret", assumeRoleResponse.getCredentials().getAccessKeySecret());
            // token
            map.put("securityToken", assumeRoleResponse.getCredentials().getSecurityToken());
            // 有效时间
            map.put("expiration", assumeRoleResponse.getCredentials().getExpiration());
            //目录
            map.put("path","/lkmotion/"+tokenName+"/");

            return ResponseResult.success(map);
        } catch (ClientException e) {
            e.printStackTrace();
            return ResponseResult.fail(1,"获取阿里oss token失败，服务器内部错误！错误码：" + e.getErrCode() + ";错误信息：" + e.getErrMsg());
        }

    }

}
