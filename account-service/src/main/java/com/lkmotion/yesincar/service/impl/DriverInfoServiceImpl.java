package com.lkmotion.yesincar.service.impl;

import com.lkmotion.yesincar.constant.AccountStatusCode;
import com.lkmotion.yesincar.dao.DriverBaseInfoDao;
import com.lkmotion.yesincar.dao.DriverInfoDao;
import com.lkmotion.yesincar.dao.DriverLicenceInfoDao;
import com.lkmotion.yesincar.dto.ResponseResult;
import com.lkmotion.yesincar.dto.driver.DriverBaseInfoView;
import com.lkmotion.yesincar.entity.DriverBaseInfo;
import com.lkmotion.yesincar.entity.DriverInfo;
import com.lkmotion.yesincar.entity.DriverLicenceInfo;
import com.lkmotion.yesincar.request.UpdateDriverAddressRequest;
import com.lkmotion.yesincar.service.DriverInfoCacheService;
import com.lkmotion.yesincar.service.DriverInfoService;
import com.lkmotion.yesincar.util.EncriptUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 司机服务
 *
 * @author LiZhaoTeng
 **/
@Service
public class DriverInfoServiceImpl implements DriverInfoService {
    @Autowired
    private DriverInfoDao driverInfoDao;
    @Autowired
    private DriverInfoCacheService driverInfoCacheService;
    @Autowired
    private DriverLicenceInfoDao driverLicenceInfoDao;
    @Autowired
    private DriverBaseInfoDao driverBaseInfoDao;

    @Override
    public DriverInfo queryDriverInfoByPhoneNum(String phoneNum) {
        return driverInfoDao.queryDriverInfoByPhoneNum(phoneNum);
    }

    @Override
    public int updateDriverInfoByPhoneNum(DriverInfo driverInfo) {
        int code = driverInfoDao.updateDriverInfoByPhoneNum(driverInfo);
        //改变司机缓存中状态
        driverInfoCacheService.put(driverInfo.getPhoneNumber(), JSONObject.fromObject(driverInfo).toString());
        return code;
    }

    @Override
    public int updateCsWorkStatusByDriverId(Integer driverId, Integer csWorkStatus) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("driverId", driverId);
        param.put("csWorkStatus", csWorkStatus);
        return driverInfoDao.updateCsWorkStatusByDriverId(param);
    }

    @Override
    public ResponseResult changeDriverBaseInfo(DriverBaseInfoView driverBaseInfoView, int type) {
        driverBaseInfoView.getDriverInfo().getCarId();
        DriverInfo driverInfo = new DriverInfo();
        DriverLicenceInfo driverLicenceInfo = new DriverLicenceInfo();
        DriverBaseInfo driverBaseInfo = new DriverBaseInfo();

        if (null != driverBaseInfoView.getDriverInfo()) {
            BeanUtils.copyProperties(driverBaseInfoView.getDriverInfo(), driverInfo);
        } else {
            return ResponseResult.fail(1, "driverInfo为空！");
        }
        // 一个司机对应一辆车
        if (driverInfoDao.queryDriverInfoByCarId(driverInfo.getCarId()) > 0) {
            return ResponseResult.fail(AccountStatusCode.VEHICLE_REPEAT.getCode(), AccountStatusCode.VEHICLE_REPEAT.getValue(), driverInfo.getCarId().toString());
        }
        if (null != driverBaseInfoView.getDriverLicenceInfo()) {
            BeanUtils.copyProperties(driverBaseInfoView.getDriverLicenceInfo(), driverLicenceInfo);
        } else {
            return ResponseResult.fail(1, "driverLicenceInfo为空！");
        }
        if (null != driverBaseInfoView.getDriverBaseInfo()) {
            BeanUtils.copyProperties(driverBaseInfoView.getDriverBaseInfo(), driverBaseInfo);
        } else {
            return ResponseResult.fail(1, "driverBaseInfo为空！");
        }
        //添加
        int add = 1;
        //修改
        int update = 2;
        if (add == type) {
            String phone = EncriptUtil.toHexString(EncriptUtil.encrypt(driverBaseInfoView.getDriverInfo().getPhoneNumber())).toUpperCase();
            if (null != driverInfoDao.queryDriverInfoByPhoneNum(phone)) {
                return ResponseResult.fail(AccountStatusCode.PHONE_NUM_REPEAT.getCode(),AccountStatusCode.PHONE_NUM_REPEAT.getValue(), driverBaseInfoView.getDriverInfo().getPhoneNumber());
            }
            driverInfo.setUpdateTime(new Date());
            driverInfo.setRegisterTime(new Date());
            //司机录入时账户为0
            driverInfo.setBalance(new BigDecimal(0));
            String strPhoneNum = driverInfo.getPhoneNumber();
            String networkReservationTaxiDriverLicenseNumber = driverBaseInfo.getNetworkReservationTaxiDriverLicenseNumber();
            String address = driverBaseInfo.getAddress();
            String driverLicenceNum = driverBaseInfo.getDrivingLicenceNumber();
            try {
                if (null != strPhoneNum && !"".equals(strPhoneNum)) {
                    strPhoneNum = EncriptUtil.toHexString(EncriptUtil.encrypt(strPhoneNum)).toUpperCase();
                }
                if (null != networkReservationTaxiDriverLicenseNumber && !"".equals(networkReservationTaxiDriverLicenseNumber)) {
                    networkReservationTaxiDriverLicenseNumber = EncriptUtil.toHexString(EncriptUtil.encrypt(networkReservationTaxiDriverLicenseNumber)).toUpperCase();
                }
                if (null != address && !"".equals(address)) {
                    address = EncriptUtil.toHexString(EncriptUtil.encrypt(address)).toUpperCase();
                }
                if (null != driverLicenceNum && !"".equals(driverLicenceNum)) {
                    driverLicenceNum = EncriptUtil.toHexString(EncriptUtil.encrypt(driverLicenceNum)).toUpperCase();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            driverInfo.setPhoneNumber(strPhoneNum);
            driverInfo.setCreateTime(new Date());
            int driverInfoInsert = driverInfoDao.insertSelective(driverInfo);
            if (1 != driverInfoInsert) {
                return ResponseResult.fail(1, "添加driverInfo失败！");
            }
            int driverId = driverInfo.getId();
            driverBaseInfoView.getDriverBaseInfo().setId(driverInfo.getId());
            driverLicenceInfo.setDriverId(driverId);
            driverBaseInfo.setId(driverId);
            driverLicenceInfo.setPhoneNumber(driverLicenceNum);
            driverLicenceInfo.setCreateTime(new Date());
            int driverLicenceInfoInsert = driverLicenceInfoDao.insertSelective(driverLicenceInfo);
            if (1 != driverLicenceInfoInsert) {
                return ResponseResult.fail(1, "添加driverLicenceInfoInsert失败！");
            }
            driverBaseInfo.setNetworkReservationTaxiDriverLicenseNumber(networkReservationTaxiDriverLicenseNumber);
            driverBaseInfo.setAddress(address);
            driverBaseInfo.setDrivingLicenceNumber(driverLicenceNum);
            driverBaseInfo.setCreateTime(new Date());
            int driverBaseInfoInsert = driverBaseInfoDao.insertSelective(driverBaseInfo);
            if (1 != driverBaseInfoInsert) {
                return ResponseResult.fail(1, "添加driverBaseInfoInsert失败！");
            }
            driverBaseInfoView.setDriverBaseInfo(driverBaseInfo);
            driverBaseInfoView.setDriverLicenceInfo(driverLicenceInfo);
            driverBaseInfoView.setDriverInfo(driverInfo);
        } else if (update == type) {
            driverInfo.setUpdateTime(new Date());
            String strPhoneNum = driverInfo.getPhoneNumber();
            String networkReservationTaxiDriverLicenseNumber = driverBaseInfo.getNetworkReservationTaxiDriverLicenseNumber();
            String address = driverBaseInfo.getAddress();
            String driverLicenceNum = driverBaseInfo.getDrivingLicenceNumber();
            if (null != driverInfo.getPhoneNumber() && !"".equals(driverInfo.getPhoneNumber())) {
                strPhoneNum = EncriptUtil.toHexString(EncriptUtil.encrypt(strPhoneNum)).toUpperCase();
            }
            if (null != networkReservationTaxiDriverLicenseNumber && !"".equals(networkReservationTaxiDriverLicenseNumber)) {
                networkReservationTaxiDriverLicenseNumber = EncriptUtil.toHexString(EncriptUtil.encrypt(networkReservationTaxiDriverLicenseNumber)).toUpperCase();
            }
            if (null != address && !"".equals(address)) {
                address = EncriptUtil.toHexString(EncriptUtil.encrypt(address)).toUpperCase();
            }
            if (null != driverLicenceNum && !"".equals(driverLicenceNum)) {
                driverLicenceNum = EncriptUtil.toHexString(EncriptUtil.encrypt(driverLicenceNum)).toUpperCase();
            }
            if (null != driverBaseInfoView.getDriverInfo()) {
                driverInfo.setPhoneNumber(strPhoneNum);
                driverInfoDao.updateByPrimaryKeySelective(driverInfo);
            }
            if (null != driverBaseInfoView.getDriverBaseInfo()) {
                driverBaseInfo.setNetworkReservationTaxiDriverLicenseNumber(networkReservationTaxiDriverLicenseNumber);
                driverBaseInfo.setAddress(address);
                driverBaseInfo.setDrivingLicenceNumber(driverLicenceNum);
                driverBaseInfoDao.updateByPrimaryKey(driverBaseInfo);
            }
            if (null != driverBaseInfoView.getDriverLicenceInfo()) {
                driverLicenceInfoDao.updateByPrimaryKeySelective(driverBaseInfoView.getDriverLicenceInfo());
            }
        }
        return ResponseResult.success(driverBaseInfoView);
    }

    public HashMap<String, Object> getDriverBaseInfoView(int driverId) {
        HashMap<String, Object> view = new HashMap<>();
        DriverInfo driverInfo = driverInfoDao.selectByPrimaryKey(driverId);
        DriverLicenceInfo driverLicenceInfo = driverLicenceInfoDao.selectByDriverId(driverId);
        DriverBaseInfo baseInfo = driverBaseInfoDao.selectByPrimaryKey(driverId);
        if (null != driverInfo) {
            String strPhoneNum = EncriptUtil.decrypt(driverInfo.getPhoneNumber());
            driverInfo.setPhoneNumber(strPhoneNum);
            view.put("driverInfo", driverInfo);
        }
        if (null != driverLicenceInfo) {
            view.put("driverLicenceInfo", driverLicenceInfo);
        }
        if (null != baseInfo) {
            String networkReservationTaxiDriverLicenseNumber = EncriptUtil.decrypt(baseInfo.getNetworkReservationTaxiDriverLicenseNumber());
            if ("".equals(networkReservationTaxiDriverLicenseNumber)) {
                baseInfo.setNetworkReservationTaxiDriverLicenseNumber(baseInfo.getNetworkReservationTaxiDriverLicenseNumber());
            } else {
                baseInfo.setNetworkReservationTaxiDriverLicenseNumber(networkReservationTaxiDriverLicenseNumber);
            }
            String address = EncriptUtil.decrypt(baseInfo.getAddress());
            if ("".equals(address)) {
                baseInfo.setAddress(baseInfo.getAddress());
            } else {
                baseInfo.setAddress(address);
            }
            String driverLicenceNum = EncriptUtil.decrypt(baseInfo.getDrivingLicenceNumber());
            if ("".equals(driverLicenceNum)) {
                baseInfo.setDrivingLicenceNumber(baseInfo.getDrivingLicenceNumber());
            } else {
                baseInfo.setDrivingLicenceNumber(driverLicenceNum);
            }

            view.put("baseInfo", baseInfo);
        }
        return view;
    }
    public  ResponseResult UpdateDriverAddressRequest(UpdateDriverAddressRequest request){
        DriverInfo driverInfo = new DriverInfo();
        driverInfo.setId(request.getId());
        String strPhone = EncriptUtil.encryptionPhoneNumber(request.getPhoneNumber());
        if(!"".equals(strPhone)){
            driverInfo.setPhoneNumber(strPhone);
        }else{
            driverInfo.setPhoneNumber(request.getPhoneNumber());
        }
        driverInfoDao.updateByPrimaryKeySelective(driverInfo);
        DriverBaseInfo driverBaseInfo = new DriverBaseInfo();
        driverBaseInfo.setId(request.getId());
        driverBaseInfo.setAddress(request.getAddress());
        driverBaseInfo.setAddressLatitude(request.getAddressLatitude());
        driverBaseInfo.setAddressLongitude(request.getAddressLongitude());
        driverBaseInfoDao.updateByPrimaryKeySelective(driverBaseInfo);
        return ResponseResult.success("");
    }
}
