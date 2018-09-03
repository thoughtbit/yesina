package com.lkmotion.yesincar.controller;

import com.lkmotion.yesincar.constant.AccountStatusCode;
import com.lkmotion.yesincar.constatnt.IdentityEnum;
import com.lkmotion.yesincar.dto.ResponseResult;
import com.lkmotion.yesincar.request.GetTokenRequest;
import com.lkmotion.yesincar.request.PassengerInfoRequest;
import com.lkmotion.yesincar.service.impl.PassengerAddressServiceImpl;
import com.lkmotion.yesincar.service.impl.PassengerInfoServiceImpl;
import com.lkmotion.yesincar.service.impl.PassengerRegistHandleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lizhaoteng
 */
@RestController
@RequestMapping("/passenger")
public class PassengerController {
    @Autowired
    private PassengerRegistHandleService passengerRegistHandleService;
    @Autowired
    private PassengerInfoServiceImpl passengerInfoService;
    @Autowired
    private PassengerAddressServiceImpl passengerAddressService;

    /**
     * 乘客登录
     */
    @RequestMapping(value = {"/regist"}, produces = "application/json; charset=utf-8", method = RequestMethod.POST)
    public ResponseResult getVerificationCode(@RequestBody GetTokenRequest request) {
        try {
            //参数校验
            String phoneNum = request.getPhoneNum();
            if (StringUtils.isBlank(phoneNum)) {
                return ResponseResult.fail(AccountStatusCode.PHONE_NUM_EMPTY.getCode(), AccountStatusCode.PHONE_NUM_EMPTY.getValue(), phoneNum);
            }
            int num = 11;
            if (phoneNum.length() != num) {
                return ResponseResult.fail(AccountStatusCode.PHONE_NUM_DIDIT.getCode(), AccountStatusCode.PHONE_NUM_DIDIT.getValue(), phoneNum);
            }
            String regex;
            regex = "^((13[0-9])|(92[0-9])|(98[0-9])|(14[5,6,7,8,9])|(15([0-3]|[5-9]))|((16)([124567]))|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[0-9]))\\d{8}$";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phoneNum);
            boolean isMatch = m.matches();
            if (!isMatch) {
                return ResponseResult.fail(AccountStatusCode.PHONE_NUM_ERROR.getCode(), AccountStatusCode.PHONE_NUM_ERROR.getValue(), phoneNum);
            }
            request.setIdentityStatus(IdentityEnum.PASSENGER.getCode());
            return passengerRegistHandleService.handle(request);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.fail(1, "操作异常", request.getPhoneNum());
        }

    }

    /**
     * 查询乘客详情
     */
    @RequestMapping(value = {"/passengerInfo"}, produces = "application/json; charset=utf-8", method = RequestMethod.POST)
    public ResponseResult getPassengerInfo(@RequestBody GetTokenRequest request) {

        return ResponseResult.success(passengerInfoService.getPassengerInfoView(request));
    }

    /**
     * 修改乘客信息
     */
    @RequestMapping(value = {"/updatePassengerInfo"}, produces = "application/json; charset=utf-8", method = RequestMethod.POST)
    public ResponseResult updatePassengerInfo(@RequestBody PassengerInfoRequest request) {

        if (null != request.getId()) {
            if (null != request.getData()) {
                if (null != request.getData().getPassengerAdress()) {
                    request.getData().getPassengerAdress().setPassengerInfoId(request.getId());
                }
                if (null != request.getData().getPassengerInfo()) {
                    request.getData().getPassengerInfo().setId(request.getId());
                }
            }
        }
        ResponseResult passengerAddressResult = null;
        ResponseResult passengerInfoResult = null;
        if (null != request.getData()) {
            if (null != request.getData().getPassengerAdress()) {
                passengerAddressResult = passengerAddressService.updatePassengerAddress(request.getData().getPassengerAdress());
            }
            if (null != request.getData().getPassengerInfo()) {
                passengerInfoResult = passengerInfoService.updatePassengerInfo(request.getData().getPassengerInfo());
            }
        }
        String infoData = "";
        String addressInfodata = "";
        int code = 0;
        if (null != passengerInfoResult) {
            if (null != passengerInfoResult.getData()) {
                infoData = (String) passengerInfoResult.getData();
                code = passengerInfoResult.getCode();
            }
        }
        if (null != passengerAddressResult) {
            if (null != passengerAddressResult.getData()) {
                addressInfodata = (String) passengerAddressResult.getData();
                code = passengerAddressResult.getCode();
            }
        }
        if(0 == code){
            return ResponseResult.success("");
        }else {
            return ResponseResult.fail(infoData + addressInfodata);
        }
    }

}
