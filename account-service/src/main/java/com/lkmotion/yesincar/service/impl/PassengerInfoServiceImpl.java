package com.lkmotion.yesincar.service.impl;

import com.lkmotion.yesincar.dao.PassengerAddressDao;
import com.lkmotion.yesincar.dao.PassengerInfoDao;
import com.lkmotion.yesincar.dto.ResponseResult;
import com.lkmotion.yesincar.entity.PassengerAddress;
import com.lkmotion.yesincar.entity.PassengerInfo;
import com.lkmotion.yesincar.request.GetTokenRequest;
import com.lkmotion.yesincar.service.PassengerInfoService;
import com.lkmotion.yesincar.util.EncriptUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 乘客端
 *
 * @author LiZhaoTeng
 **/
@Service
@Slf4j
public class PassengerInfoServiceImpl implements PassengerInfoService {
    @Autowired
    private PassengerInfoDao passengerInfoDao;
    @Autowired
    private PassengerAddressDao passengerAdressDao;

    public PassengerInfo queryPassengerInfoById(Integer passengerId) {
        return passengerInfoDao.queryPassengerInfoById(passengerId);

    }

    @Override
    public PassengerInfo queryPassengerInfoByPhoneNum(String phoneNum) {
        return passengerInfoDao.queryPassengerInfoByPhoneNum(phoneNum);
    }

    @Override
    public void updatePassengerInfoLoginTime(Integer passengerId) {
        passengerInfoDao.updatePassengerInfoLoginTime(passengerId);
    }

    @Override
    public void insertPassengerInfo(PassengerInfo passengerInfo) {
        passengerInfoDao.insertSelective(passengerInfo);
    }

    public HashMap<String, Object> getPassengerInfoView(GetTokenRequest getTokenRequest) {
        HashMap<String, Object> view = new HashMap<>();
        PassengerInfo passengerInfo = passengerInfoDao.selectByPrimaryKey(getTokenRequest.getId());
        PassengerAddress passengerAdress = new PassengerAddress();
        passengerAdress.setPassengerInfoId(getTokenRequest.getId());
        List<PassengerAddress> passengerAddressList = new ArrayList<>();
        if (null != getTokenRequest.getType()) {
            passengerAdress.setType(getTokenRequest.getType());
            passengerAdress = passengerAdressDao.selectByAddPassengerInfoId(passengerAdress);
        } else {
            passengerAddressList = passengerAdressDao.selectPassengerAddressList(getTokenRequest.getId());
        }

        if (null != passengerInfo) {
            int length = 32;
            String decrypt;
            if (length == passengerInfo.getPhone().length() && !"".equals(passengerInfo.getPhone()) && null != passengerInfo.getPhone()) {
                decrypt = EncriptUtil.decrypt(passengerInfo.getPhone());
                passengerInfo.setPhone(decrypt);
            }
            view.put("passengerInfo", passengerInfo);
        }
        if (null != passengerAddressList && 0 != passengerAddressList.size()) {
            view.put("passengerAddressList", passengerAddressList);
        }
        if (null != passengerAdress && null != getTokenRequest.getType()) {
            view.put("passengerAdress", passengerAdress);
        }

        return view;
    }

    public ResponseResult updatePassengerInfo(PassengerInfo passengerInfo) {
        //乘客信息
        if (null != passengerInfo && null != passengerInfo.getPhone() && !"".equals(passengerInfo.getPhone())) {
            String decrypt = EncriptUtil.encryptionPhoneNumber(passengerInfo.getPhone());
            passengerInfo.setPhone(decrypt);
        }
        int updateOrInsert;

        if (null != passengerInfo && null != passengerInfo.getId()) {
            passengerInfo.setUpdateTime(new Date());
            updateOrInsert = passengerInfoDao.updateByPrimaryKeySelective(passengerInfo);
        } else {
            passengerInfo.setCreateTime(new Date());
            updateOrInsert = passengerInfoDao.insertSelective(passengerInfo);
        }

        if (1 == updateOrInsert) {
            return ResponseResult.success("");
        } else {
            return ResponseResult.fail("修改或添加乘客信息失败!");
        }

    }
}
