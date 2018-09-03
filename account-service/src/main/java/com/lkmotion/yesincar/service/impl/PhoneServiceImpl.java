package com.lkmotion.yesincar.service.impl;

import com.lkmotion.yesincar.constant.AccountStatusCode;
import com.lkmotion.yesincar.constatnt.IdentityEnum;
import com.lkmotion.yesincar.dao.DriverInfoDao;
import com.lkmotion.yesincar.dao.PassengerInfoDao;
import com.lkmotion.yesincar.dto.ResponseResult;
import com.lkmotion.yesincar.entity.DriverInfo;
import com.lkmotion.yesincar.entity.PassengerInfo;
import com.lkmotion.yesincar.request.PhoneRequest;
import com.lkmotion.yesincar.util.EncriptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * 司机服务
 *
 * @author LiZhaoTeng
 **/
@Service
public class PhoneServiceImpl {
    @Autowired
    private DriverInfoDao driverInfoDao;
    @Autowired
    private PassengerInfoDao passengerInfoDao;

    public ResponseResult getDecryptById(PhoneRequest request) {
        Integer idType = request.getIdType();
        if (null == idType) {
            return ResponseResult.fail(1, "idtype为空！");
        }
        int length = 32;
        if (null == request.getInfoList()) {
            return ResponseResult.fail(1, "infoList为空！");
        }
        for (int i = 0; i < request.getInfoList().size(); i++) {
            String strPhone = "";
            if (ObjectUtils.nullSafeEquals(idType, IdentityEnum.PASSENGER.getCode())) {
                PassengerInfo passengerInfo = passengerInfoDao.selectByPrimaryKey(request.getInfoList().get(i).getId());
                if (null != passengerInfo && null != passengerInfo.getPhone() && !"".equals(passengerInfo.getPhone()) && length == passengerInfo.getPhone().length()) {
                    strPhone = EncriptUtil.decryptionPhoneNumber(passengerInfo.getPhone());
                }
                request.getInfoList().get(i).setPhone(strPhone);
            }
            if (ObjectUtils.nullSafeEquals(idType, IdentityEnum.DRIVER.getCode())) {
                DriverInfo driverInfo = driverInfoDao.selectByPrimaryKey(request.getInfoList().get(i).getId());
                if (null != driverInfo && null != driverInfo.getPhoneNumber() && !"".equals(driverInfo.getPhoneNumber()) && length == driverInfo.getPhoneNumber().length()) {
                    strPhone = EncriptUtil.decryptionPhoneNumber(driverInfo.getPhoneNumber());
                }

                request.getInfoList().get(i).setPhone(strPhone);
            }
        }
        return ResponseResult.success(request);
    }

    public ResponseResult createEncrypt(PhoneRequest request) {
        if (null == request.getInfoList()) {
            return ResponseResult.fail(AccountStatusCode.PHONE_NUM_EMPTY.getCode(),AccountStatusCode.PHONE_NUM_EMPTY.getValue());
        }
        for (int i = 0; i < request.getInfoList().size(); i++) {
            String strPhone;
            if (null == request.getInfoList().get(i).getPhone()) {
                return ResponseResult.fail(AccountStatusCode.PHONE_NUM_EMPTY.getCode(),AccountStatusCode.PHONE_NUM_EMPTY.getValue());
            }
            strPhone = EncriptUtil.encryptionPhoneNumber(request.getInfoList().get(i).getPhone());
            if("".equals(strPhone)){
                request.getInfoList().get(i).setEncrypt(request.getInfoList().get(i).getPhone());
            }else {
                request.getInfoList().get(i).setEncrypt(strPhone);
            }
        }
        return ResponseResult.success(request);
    }
    public ResponseResult getPhoneByEncryptList(PhoneRequest request) {

        if( null ==request.getInfoList() ){
            return ResponseResult.fail(AccountStatusCode.ENCRYPT_EMPTY.getCode(),AccountStatusCode.ENCRYPT_EMPTY.getValue());
        }
        for (int i = 0 ; i< request.getInfoList().size();i++){
            String strPhone ;
            if( null == request.getInfoList().get(i).getEncrypt()){
                return ResponseResult.fail(AccountStatusCode.ENCRYPT_EMPTY.getCode(),AccountStatusCode.ENCRYPT_EMPTY.getValue());
            }
            strPhone =   EncriptUtil.decryptionPhoneNumber(request.getInfoList().get(i).getEncrypt());
            if("".equals(strPhone)){
                request.getInfoList().get(i).setPhone(request.getInfoList().get(i).getEncrypt());
            }else{
                request.getInfoList().get(i).setPhone(strPhone);
            }
        }
        return ResponseResult.success(request);
    }
}
