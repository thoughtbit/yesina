package com.lkmotion.yesincar.controller;

import com.lkmotion.yesincar.constant.AccountStatusCode;
import com.lkmotion.yesincar.constatnt.IdentityEnum;
import com.lkmotion.yesincar.dto.ResponseResult;
import com.lkmotion.yesincar.request.CheckTokenRequest;
import com.lkmotion.yesincar.request.CreateTokenRequest;
import com.lkmotion.yesincar.request.GetTokenRequest;
import com.lkmotion.yesincar.service.impl.AuthServiceImpl;
import com.lkmotion.yesincar.service.impl.PassengerRegistHandleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * token检验
 *
 * @author LiZhaoTeng
 * @date 2018/08/10
 **/
@RestController
@RequestMapping("/auth")
public class AuthTokenController {
    @Autowired
    private AuthServiceImpl authService;
    @Autowired
    private PassengerRegistHandleService passengerRegistHandleService;

    /**
     * 鉴权
     */
    @RequestMapping(value = {"/checkToken"}, produces = "application/json; charset=utf-8", method = RequestMethod.POST)
    public ResponseResult checkToken(@RequestBody CheckTokenRequest request) {
        if (null == request.getToken()) {
            return ResponseResult.fail(1, AccountStatusCode.TOKEN_NOT_EMPTY.getValue());
        }
        String s = authService.checkToken(request.getToken());
        String one = "1";
        String two = "2";
        if (ObjectUtils.nullSafeEquals(one, s)) {
            return ResponseResult.fail(1, "Token已失效");
        } else if (two.equals(s)) {
            return ResponseResult.fail(1, "服务器异常");
        } else {
            return ResponseResult.success("");
        }
    }

    /**
     * 生成token
     */
    @RequestMapping(value = {"/createToken"}, produces = "application/json; charset=utf-8", method = RequestMethod.POST)
    public ResponseResult createToken(@RequestBody CreateTokenRequest request) {

        //身份_电话号码_ID
        String subject = request.getType() + "_" + request.getPhoneNum() + "_" + request.getId();
        return ResponseResult.success(authService.createToken(subject));
    }

    /**
     * 登出
     */
    @RequestMapping(value = {"/checkOut"}, produces = "application/json; charset=utf-8", method = RequestMethod.POST)
    public ResponseResult checkOut(@RequestBody GetTokenRequest request) throws Exception {
        String strToken = request.getToken();

        if (String.valueOf(IdentityEnum.PASSENGER.getCode()).equals(request.getEquipType())) {
            //参数校验
            if (null == strToken) {
                return ResponseResult.fail(AccountStatusCode.TOKEN_NOT_EMPTY.getCode(), AccountStatusCode.TOKEN_NOT_EMPTY.getValue());
            }
            return passengerRegistHandleService.checkOut(request);

        }
        if (String.valueOf(IdentityEnum.DRIVER.getCode()).equals(request.getEquipType())) {
            // 参数校验
            if (null == strToken) {
                return ResponseResult.fail(AccountStatusCode.TOKEN_NOT_EMPTY.getCode(), AccountStatusCode.TOKEN_NOT_EMPTY.getValue());
            }
            return passengerRegistHandleService.checkOut(request);
        }
        if (String.valueOf(IdentityEnum.CAR_SCREEN.getCode()).equals(request.getEquipType())) {
            if (null == strToken) {
                return ResponseResult.fail(AccountStatusCode.TOKEN_NOT_EMPTY.getCode(), AccountStatusCode.TOKEN_NOT_EMPTY.getValue());
            }
            return passengerRegistHandleService.checkOut(request);

        }
        if (String.valueOf(IdentityEnum.LARGE_SCREEN.getCode()).equals(request.getEquipType())) {
            if (null == strToken) {
                return ResponseResult.fail(AccountStatusCode.TOKEN_NOT_EMPTY.getCode(), AccountStatusCode.TOKEN_NOT_EMPTY.getValue());
            }
            return passengerRegistHandleService.checkOut(request);
        }
        return ResponseResult.success("");
    }
}
