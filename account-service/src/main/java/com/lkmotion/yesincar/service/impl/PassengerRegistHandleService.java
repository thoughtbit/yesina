package com.lkmotion.yesincar.service.impl;

import com.lkmotion.yesincar.constatnt.IdentityEnum;
import com.lkmotion.yesincar.dto.ResponseResult;
import com.lkmotion.yesincar.entity.PassengerInfo;
import com.lkmotion.yesincar.request.GetTokenRequest;
import com.lkmotion.yesincar.response.PassengerRegistResponse;
import com.lkmotion.yesincar.util.EncriptUtil;
import com.lkmotion.yesincar.util.JWTUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * @author lizhaoteng
 */
@Service
@Slf4j
public class PassengerRegistHandleService {

    @Autowired
    private PassengerInfoServiceImpl passengerInfoService;
    @Autowired
    private AuthServiceImpl authService;

    public ResponseResult handle(GetTokenRequest getTokenRequest) {

        String phoneNumber = getTokenRequest.getPhoneNum();
        log.info("乘客手机号：" + phoneNumber);
        // 查询乘客信息
        String strPhoneNum = EncriptUtil.toHexString(EncriptUtil.encrypt(phoneNumber)).toUpperCase();
        log.info("加密后手机号：" + strPhoneNum);
        PassengerInfo passengerInfo = passengerInfoService.queryPassengerInfoByPhoneNum(strPhoneNum);
        log.info("根据手机号查询，乘客信息。" + passengerInfo);
        PassengerInfo passengerInfoTmp = new PassengerInfo();
        int passengerId;
        if (null == passengerInfo) {
            // 若无乘客信息，记录新乘客信息
            passengerInfoTmp.setPhone(strPhoneNum);
            passengerInfoTmp.setRegisterTime(new Date());
            passengerInfoTmp.setBalance(new BigDecimal(0));
            passengerInfoTmp.setCreateTime(new Date());
            log.info("新增乘客手机号：" + passengerInfoTmp.getPhone());
            passengerInfoService.insertPassengerInfo(passengerInfoTmp);
            passengerId = passengerInfoTmp.getId();
            log.info("乘客注册或登录 - " + phoneNumber + " - 校验注册状态 - 用户未注册，已插入新用户记录");
        } else {
            log.info("乘客注册或登录 - " + phoneNumber + " - 校验注册状态 - 用户已注册");
            //若乘客登录或者注册过了，更新登录时间
            passengerId = passengerInfo.getId();
            passengerInfoService.updatePassengerInfoLoginTime(passengerId);
        }

        //乘客登录 生成jwtStr
        String subject = IdentityEnum.PASSENGER.getCode() + "_" + phoneNumber + "_" + passengerId;
        log.info("token:" + subject);
        String jwtStr = authService.createToken(subject);
        log.info("乘客注册或登录用户-" + phoneNumber + "- access_token:" + jwtStr);
        //多终端互踢

        passengerInfo = passengerInfoService.queryPassengerInfoByPhoneNum(strPhoneNum);

        return createResponse("0", jwtStr, passengerInfo.getPassengerName(), passengerInfo.getGender(), passengerInfo.getBalance(), phoneNumber, passengerInfo.getHeadImg(), passengerId);
    }

    /**
     * 封装响应协议
     */
    private ResponseResult createResponse(String status, String accessToken, String passengerName, Byte sex, BigDecimal balance, String phoneNum, String headImg, Integer id) {
        PassengerRegistResponse response = new PassengerRegistResponse();
        response.setStatus(status);
        response.setAccessToken(accessToken);
        response.setPassengerName(StringUtils.isBlank(passengerName) ? "" : passengerName);
        response.setGender(sex == null ? 0 : sex);
        response.setId(id);
        response.setBalance(balance);
        response.setPhoneNum(phoneNum);
        response.setHeadImg(StringUtils.isBlank(headImg) ? "" : headImg);
        //极光ID
        String jpushId = IdentityEnum.PASSENGER.getCode() + "_" + phoneNum + "_" + Calendar.getInstance().getTimeInMillis();
        response.setJpushId(jpushId);
        return ResponseResult.success(response);
    }

    public ResponseResult checkOut(GetTokenRequest request) throws Exception {
        String strToken = request.getToken();
        Claims claims = JWTUtil.parseJWT(strToken);
        String subject = claims.getSubject();
        authService.deleteToken(subject);
        return ResponseResult.success("");
    }
}
