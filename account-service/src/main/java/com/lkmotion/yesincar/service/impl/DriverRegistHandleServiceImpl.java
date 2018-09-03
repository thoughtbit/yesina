package com.lkmotion.yesincar.service.impl;

import com.lkmotion.yesincar.constatnt.IdentityEnum;
import com.lkmotion.yesincar.dto.ResponseResult;
import com.lkmotion.yesincar.entity.DriverInfo;
import com.lkmotion.yesincar.request.GetTokenRequest;
import com.lkmotion.yesincar.response.DriverRegistResponse;
import com.lkmotion.yesincar.util.DriverInfoValidator;
import com.lkmotion.yesincar.util.EncriptUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

/**
 * 司机端短信校验
 *
 * @author LiZhaoTeng
 **/
@Service
@Slf4j
public class DriverRegistHandleServiceImpl {
    @Autowired
    private DriverInfoServiceImpl driverInfoService;
    @Autowired
    private DriverInfoCacheServiceImpl driverCacheService;
    @Autowired
    private AuthServiceImpl authService;

    public ResponseResult checkIn(GetTokenRequest getTokenRequest) {

        String phoneNumber = getTokenRequest.getPhoneNum();

        // 查询司机信息
        String strPhoneNum = EncriptUtil.toHexString(EncriptUtil.encrypt(phoneNumber)).toUpperCase();
        DriverInfo driverInfo = driverInfoService.queryDriverInfoByPhoneNum(strPhoneNum);
        ResponseResult errResponse = DriverInfoValidator.hasError(phoneNumber, driverInfo);
        if (null != errResponse) {
            return errResponse;
        }
        // 将司机信息更新至缓存
        driverCacheService.put(phoneNumber, JSONObject.fromObject(driverInfo).toString());

        // 司机登录 生成jwtStr
        String subject = IdentityEnum.DRIVER.getCode() + "_" + phoneNumber + "_" + driverInfo.getId();

        String jwtStr = authService.createToken(subject);
        log.info("司机注册或登录用户-" + phoneNumber + "- access_token:" + jwtStr);
        return createResponse("0", jwtStr, driverInfo.getDriverName(), driverInfo.getGender(), driverInfo.getWorkStatus(), driverInfo.getHeadImg(), driverInfo.getId(), phoneNumber);
    }

    private ResponseResult createResponse(String status, String accessToken, String driverName, Integer gender, Integer checkStatus, String headImg, Integer driverId, String phoneNumber) {
        DriverRegistResponse response = new DriverRegistResponse();
        response.setWorkStatus(status);
        response.setAccessToken(accessToken);
        response.setDriverName(StringUtils.isBlank(driverName) ? "" : driverName);
        response.setPhoneNumber(phoneNumber);
        response.setGerder(null == gender ? 1 : gender);
        // 1已注册但未审核；2审核中；3审核通过；4审核未通过
        response.setCheckStatus(checkStatus);
        response.setHeadImg(StringUtils.isBlank(headImg) ? "" : headImg);
        response.setDriverId(driverId);
        // 极光ID
        String jpushId = IdentityEnum.DRIVER.getCode() + "_" + phoneNumber + "_" + Calendar.getInstance().getTimeInMillis();
        response.setJpushId(jpushId);
        return ResponseResult.success(response);
    }
}
