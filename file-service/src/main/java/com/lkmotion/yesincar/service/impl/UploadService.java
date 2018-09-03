package com.lkmotion.yesincar.service.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.lkmotion.yesincar.constatnt.BusinessInterfaceStatus;
import com.lkmotion.yesincar.dto.ResponseResult;
import com.lkmotion.yesincar.utils.OssApiConfig;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

/**
 * 上传文件
 * @author LiZhaoTeng
 **/
@Service
public class UploadService {
    @Autowired
    private OssApiConfig ossApiConfig;

    public ResponseResult uploadFiles(byte[] input, String folder, String fileName) {
        String fileUrl = "";
        ByteArrayInputStream byteArrayInput = null;
        try {
            // 文件为空错误
            if (null == input) {
                return ResponseResult.fail(1);
            }
            byte[] decodeInput = input;
            int length = decodeInput.length;
            if (length == 0) {
                // 上传文件为空文件错误
                return ResponseResult.fail(1);
            }
            // Client采用原生的HttpClient请求，自身设置了超时机制，不用shutdown
            // 默认连接超时50秒
            // 默认Socket超时50秒
            // 默认最大连接数1024
            OSSClient client = new OSSClient(ossApiConfig.getEndpoint(), ossApiConfig.getAccessId(), ossApiConfig.getAccessKey());

            String key = (folder == null ? "" : (folder + "/")) + fileName;

            // 判断文件是否存在，文件存在直接返回文件URL
            if (client.doesObjectExist(ossApiConfig.getBucketLkmotion(), key)) {
                fileUrl = ossApiConfig.getEndpointUpload() + "/" + key;
            } else {
                ObjectMetadata metadata = new ObjectMetadata();

                // 采用非chunk方式传输数据包
                metadata.setContentLength(length);

                /* 文件类型设定 */
                byteArrayInput = new ByteArrayInputStream(decodeInput);
                client.putObject(ossApiConfig.getBucketLkmotion(), key, byteArrayInput, metadata);

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("result", 0);
                jsonObject.put("key", key);
                jsonObject.put("url", ossApiConfig.getEndpointUpload() + "/" + key);

                fileUrl = ossApiConfig.getEndpointUpload() + "/" + key;
            }
            client.shutdown();
            return ResponseResult.success(fileUrl);
        } finally {
            if (null != byteArrayInput) {
                try {
                    byteArrayInput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * @param targetFile   需要上传OSS文件服务器上生成的目标文件路径
     * @param fileNamePath 当前生成文件路径
     */
    public ResponseResult uploadFileToOss(String targetFile, File fileNamePath) {
        JSONObject jsonObject = new JSONObject();
        ResponseResult responseResult = new ResponseResult();
        OSSClient ossClient = new OSSClient(ossApiConfig.getEndpoint(), ossApiConfig.getAccessId(), ossApiConfig.getAccessKey());
        boolean exists = ossClient.doesBucketExist(ossApiConfig.getBucketLkmotion());
        if (!exists) {
            jsonObject.put("status","1");
            return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(),"更新失败");
        } else {
            jsonObject.put("status","0");
            if (fileNamePath.exists()) {
                boolean found = ossClient.doesObjectExist(ossApiConfig.getBucketLkmotion(), targetFile);
                if (found) {
                    ossClient.deleteObject(ossApiConfig.getBucketLkmotion(), targetFile);
                }
                ossClient.putObject(ossApiConfig.getBucketLkmotion(), targetFile, fileNamePath);
                ossClient.shutdown();
                String fileUrl = ossApiConfig.getEndpointUpload() + "/" + targetFile;
                jsonObject.put("fileUrl",fileUrl);
                responseResult.setData(jsonObject.toString());

            }
        }
        return responseResult;
    }
}
