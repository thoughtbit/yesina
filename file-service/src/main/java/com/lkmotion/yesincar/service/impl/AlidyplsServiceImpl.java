package com.lkmotion.yesincar.service.impl;

import com.lkmotion.yesincar.dto.ResponseResult;
import com.lkmotion.yesincar.entity.CallRecords;
import com.lkmotion.yesincar.entity.SecretVoiceRecords;
import com.lkmotion.yesincar.mapper.CallRecordsMapper;
import com.lkmotion.yesincar.mapper.SecretVoiceRecordsMapper;
import com.lkmotion.yesincar.service.AlidyplsService;
import com.lkmotion.yesincar.utils.AliApiConfig;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 功能描述
 *
 * @author liheng
 * @date 2018/8/21
 */
@Service
public class AlidyplsServiceImpl implements AlidyplsService {
    @Autowired
    private SecretVoiceRecordsMapper secretVoiceRecordsMapper;
    @Autowired
    private CallRecordsMapper callRecordsMapper;
    @Autowired
    private AliApiConfig aLiConfig;

    @Override
    public ResponseResult callRecords(String json) throws Exception {
        JSONObject jsonObject = new JSONObject();
        JSONArray backBodyJson = JSONArray.fromObject(json);
        for(int i=0;i<backBodyJson.size();i++){
            JSONObject job = backBodyJson.getJSONObject(i);
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(job.get("start_time") != null && job.get("release_time") != null) {
                Date d1 = format.parse(job.get("start_time").toString());
                Date d2 = format.parse(job.get("release_time").toString());
                if(!DateUtils.isSameInstant(d1, d2)) {
                    CallRecords callRecord = new CallRecords();
                    callRecord.setId(Integer.parseInt(job.get("id").toString()));
                    callRecord.setPoolKey(aLiConfig.getPoolKey());
                    callRecord.setSubId(job.get("sub_id").toString());
                    callRecord.setCallId(job.get("call_id").toString());
                    callRecord.setCallType(job.get("call_type").toString());
                    callRecord.setPhoneNo(job.get("phone_no").toString());
                    callRecord.setSecretNo(job.get("secret_no").toString());
                    callRecord.setPeerNo(job.get("peer_no").toString());
                    callRecord.setReleaseDir(job.get("release_dir").toString());
                    callRecord.setReleaseCause(Integer.parseInt(job.get("release_cause").toString()));
                    callRecord.setStartTime(format.parse(job.get("start_time").toString()));
                    callRecord.setReleaseTime(format.parse(job.get("release_time").toString()));
                    if(job.get("call_time") != null) {
                        callRecord.setCallTime(format.parse(job.get("call_time").toString()));
                    }
                    if(job.get("ring_time") != null) {
                        callRecord.setRingTime(format.parse(job.get("ring_time").toString()));
                    }
                    //插入通话记录
                    callRecord.setCreateTime(new Date());
                    callRecordsMapper.insertSelective(callRecord);

                    //插入录音
                    SecretVoiceRecords secretVoiceRecords = new SecretVoiceRecords();
                    secretVoiceRecords.setCallId(callRecord.getCallId());
                    secretVoiceRecords.setSubsId(callRecord.getSubId());
                    secretVoiceRecords.setCallTime(callRecord.getCallTime());
                    secretVoiceRecords.setCreateTime(new Date());
                    secretVoiceRecordsMapper.insertSelective(secretVoiceRecords);
                    jsonObject.put("conten","插入成功");
                    return ResponseResult.success(jsonObject);
                }else {
                    return ResponseResult.fail("解析失败");
                }
            }
            return ResponseResult.fail("json格式错误");
        }
        return ResponseResult.success(jsonObject);
    }
}
