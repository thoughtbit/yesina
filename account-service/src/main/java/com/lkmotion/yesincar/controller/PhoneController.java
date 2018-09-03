package com.lkmotion.yesincar.controller;

import com.lkmotion.yesincar.dto.ResponseResult;
import com.lkmotion.yesincar.request.PhoneRequest;
import com.lkmotion.yesincar.service.impl.PhoneServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * BOSS司机端
 *
 * @author liZhaoTeng
 **/
@RestController
@RequestMapping("/phone")
public class PhoneController {

    @Autowired
    private PhoneServiceImpl phoneService;

    /**
     * 根据ID解密手机号
     */
    @RequestMapping(value = {"/getPhoneList"}, produces = "application/json; charset=utf-8", method = RequestMethod.POST)
    public ResponseResult getPhoneByIdList(@RequestBody PhoneRequest request) {

        return phoneService.getDecryptById(request);
    }

    /**
     * 加密手机号
     */
    @RequestMapping(value = {"/createEncrypt"}, produces = "application/json; charset=utf-8", method = RequestMethod.POST)
    public ResponseResult createEncrypt(@RequestBody PhoneRequest request) {

        return phoneService.createEncrypt(request);
    }
    /**
     * 根据加密手机号解密
     */
    @RequestMapping(value = {"/getPhoneByEncryptList"}, produces = "application/json; charset=utf-8", method = RequestMethod.POST)
    public ResponseResult getPhoneByEncryptList(@RequestBody PhoneRequest request) {

        return phoneService.getPhoneByEncryptList(request);
    }
}
