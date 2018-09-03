package com.lkmotion.yesincar.controller;

import com.lkmotion.yesincar.constant.AccountStatusCode;
import com.lkmotion.yesincar.dto.ResponseResult;
import com.lkmotion.yesincar.dto.driver.DriverBaseInfoView;
import com.lkmotion.yesincar.entity.DriverInfo;
import com.lkmotion.yesincar.request.DriverChangeRequest;
import com.lkmotion.yesincar.request.DriverWorkStatusRequest;
import com.lkmotion.yesincar.request.GetTokenRequest;
import com.lkmotion.yesincar.request.UpdateDriverAddressRequest;
import com.lkmotion.yesincar.service.impl.DriverInfoServiceImpl;
import com.lkmotion.yesincar.service.impl.DriverRegistHandleServiceImpl;
import com.lkmotion.yesincar.service.impl.DriverWorkStautsHandleService;
import com.lkmotion.yesincar.util.DriverInfoValidator;
import com.lkmotion.yesincar.util.EncriptUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 司机控制层
 *
 * @author LiZhaoTeng
 * @date 2018/08/08
 **/
@RestController
@RequestMapping("/driver")
@Slf4j
public class DriverController {
    @Autowired
    private DriverInfoServiceImpl driverInfoService;
    @Autowired
    private DriverRegistHandleServiceImpl driverRegistHandleService;
    @Autowired
    private DriverWorkStautsHandleService driverWorkStautsHandleService;

    /**
     * 司机登陆
     */
    @RequestMapping(value = {"/regist"}, produces = "application/json; charset=utf-8", method = RequestMethod.POST)
    public ResponseResult getVerificationDriverCode(@RequestBody GetTokenRequest request) {

        try {
            // 参数校验：手机号码
            String phoneNum = request.getPhoneNum();
            if (StringUtils.isBlank(phoneNum)) {
                return ResponseResult.fail(AccountStatusCode.PHONE_NUM_EMPTY.getCode(), AccountStatusCode.PHONE_NUM_EMPTY.getValue(), phoneNum);
            }
            int num = 11;
            if (phoneNum.length() != num) {
                return ResponseResult.fail(AccountStatusCode.PHONE_NUM_DIDIT.getCode(), AccountStatusCode.PHONE_NUM_DIDIT.getValue(), phoneNum);
            }
            String regex = "^((13[0-9])|(92[0-9])|(98[0-9])|(14[5,6,7,8,9])|(15([0-3]|[5-9]))|((16)([124567]))|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[0-9]))\\d{8}$";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phoneNum);
            boolean isMatch = m.matches();
            if (!isMatch) {
                return ResponseResult.fail(AccountStatusCode.PHONE_NUM_ERROR.getCode(), AccountStatusCode.PHONE_NUM_ERROR.getValue(), phoneNum);
            }
            // 检查司机状态
            String strPhoneNum = EncriptUtil.toHexString(EncriptUtil.encrypt(phoneNum)).toUpperCase();
            DriverInfo driverInfo = driverInfoService.queryDriverInfoByPhoneNum(strPhoneNum);
            ResponseResult errResponse = DriverInfoValidator.hasError(phoneNum, driverInfo);
            if (null != errResponse) {
                return errResponse;
            }
            return driverRegistHandleService.checkIn(request);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.fail(1, "服务器异常", request.getPhoneNum());
        }
    }

    /**
     * 修改司机工作状态
     */
    @RequestMapping(value = {"/changeWorkStatus"}, produces = "application/json; charset=utf-8", method = RequestMethod.POST)
    public ResponseResult changeWorkStatus(@RequestBody DriverWorkStatusRequest driverWorkStatusRequest) {
        Integer id = driverWorkStatusRequest.getId();
        if (null == id || 0 == id) {
            return ResponseResult.fail(-1, "longitude:主键id为空");
        }
        driverWorkStatusRequest.setId(id);
        Double lng = driverWorkStatusRequest.getLongitude();
        if (null == lng) {
            return ResponseResult.fail(AccountStatusCode.LONGITUDE_EMPTY.getCode(),AccountStatusCode.LONGITUDE_EMPTY.getValue());
        }
        Double lat = driverWorkStatusRequest.getLatitude();
        if (null == lat) {
            return ResponseResult.fail(AccountStatusCode.LATITUDE_EMPTY.getCode(),AccountStatusCode.LATITUDE_EMPTY.getValue());
        }
        String city = driverWorkStatusRequest.getCity();
        if (StringUtils.isBlank(city)) {
            return ResponseResult.fail(AccountStatusCode.CITY_CODE_EMPTY.getCode(), AccountStatusCode.CAR_TYPE_EMPTY.getValue());
        }
        Double speed = driverWorkStatusRequest.getSpeed();
        if (null == speed) {
            return ResponseResult.fail(AccountStatusCode.SPEED_EMPTY.getCode(), AccountStatusCode.SPEED_EMPTY.getValue());
        }
        ResponseResult responseResult = null;
        try {
            log.info("请求参数："+
                    "isFollowing:"+driverWorkStatusRequest.getIsFollowing()+
                    "workStatus:" +driverWorkStatusRequest.getWorkStatus()+
                    "csWorkStatus:" +driverWorkStatusRequest.getCsWorkStatus()+
                    "latitude:"+ driverWorkStatusRequest.getLatitude()+
                    "longitude:"+ driverWorkStatusRequest.getLongitude()+
                    "city:"+ driverWorkStatusRequest.getCity()+
                    "id:"+ driverWorkStatusRequest.getId() +
                    "speed:" + driverWorkStatusRequest.getSpeed()
            );
            responseResult = driverWorkStautsHandleService.changeWorkStatus(driverWorkStatusRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseResult;
    }

    /**
     * 添加司机
     */
    @RequestMapping(value = {"/driver"}, produces = "application/json; charset=utf-8", method = RequestMethod.POST)
    public ResponseResult driverAdd(@RequestBody DriverChangeRequest request) {
        DriverBaseInfoView driverBaseInfoView = request.getData();
        if (null == driverBaseInfoView.getDriverInfo().getCityCode()|| "".equals(driverBaseInfoView.getDriverInfo().getCityCode())) {
            log.error("服务城市为空");
            return ResponseResult.fail(AccountStatusCode.CITY_CODE_EMPTY.getCode(),AccountStatusCode.CITY_CODE_EMPTY.getValue());
        }
        if (null == driverBaseInfoView.getDriverInfo().getDriverLeader()) {
            log.error("司机主管为空");
            return ResponseResult.fail(AccountStatusCode.DRIVER_LEADER_EMPTY.getCode(),AccountStatusCode.DRIVER_LEADER_EMPTY.getValue());
        }
        if (null == driverBaseInfoView.getDriverInfo().getGender()) {
            log.error("司机性别为空");
            return ResponseResult.fail(AccountStatusCode.GENDER_EMPTY.getCode(),AccountStatusCode.GENDER_EMPTY.getValue());
        }
        if (null == driverBaseInfoView.getDriverInfo().getDriverName() || "".equals(driverBaseInfoView.getDriverInfo().getDriverName())) {
            log.info("司机姓名为空！");
            return ResponseResult.fail(AccountStatusCode.DRIVER_NAME_EMPTY.getCode(), AccountStatusCode.DRIVER_NAME_EMPTY.getValue());
        }
        if (null == driverBaseInfoView.getDriverLicenceInfo().getIdentityCardId() || "".equals(driverBaseInfoView.getDriverLicenceInfo().getIdentityCardId())) {
            log.info("司机身份证为空！");
            return ResponseResult.fail(AccountStatusCode.DRIVER_CARD_ID_EMPTY.getCode(),AccountStatusCode.DRIVER_CARD_ID_EMPTY.getValue());
        }
        if (null == driverBaseInfoView.getDriverInfo().getPhoneNumber() || "".equals(driverBaseInfoView.getDriverInfo().getPhoneNumber())) {
            log.info("司机手机号为空！");
            return ResponseResult.fail(AccountStatusCode.DRIVER_PHONE_NUM_EMPTY.getCode(),AccountStatusCode.DRIVER_PHONE_NUM_EMPTY.getValue());
        }
        if (null == driverBaseInfoView.getDriverBaseInfo().getContractEndDate()) {
            log.info("合同协议有效期止为空！");
            return ResponseResult.fail(AccountStatusCode.CONTRACT_END_DATE_EMPTY.getCode(),AccountStatusCode.CONTRACT_END_DATE_EMPTY.getValue());
        }
        if (null == driverBaseInfoView.getDriverBaseInfo().getContractStartDate()) {
            log.info("合同协议有效期始为空！");
            return ResponseResult.fail(AccountStatusCode.CONTRACT_START_DATE_EMPTY.getCode(),AccountStatusCode.CONTRACT_START_DATE_EMPTY.getValue());
        }
        if (null == driverBaseInfoView.getDriverInfo().getUseStatus()) {
            log.info("启用状态为空！");
            return ResponseResult.fail(AccountStatusCode.DRIVER_USE_STATUS.getCode(),AccountStatusCode.DRIVER_USE_STATUS.getValue());
        }
        return driverInfoService.changeDriverBaseInfo(driverBaseInfoView, 1);
    }

    /**
     * 修改司机
     */
    @RequestMapping(value = {"/changeDriver"}, produces = "application/json; charset=utf-8", method = RequestMethod.POST)
    public ResponseResult driverChange(@RequestBody DriverChangeRequest request) {
        DriverBaseInfoView driverBaseInfoView = request.getData();
        if (null == driverBaseInfoView.getDriverInfo().getCityCode()|| "".equals(driverBaseInfoView.getDriverInfo().getCityCode())) {
            log.error("服务城市为空");
            return ResponseResult.fail(AccountStatusCode.CITY_CODE_EMPTY.getCode(),AccountStatusCode.CITY_CODE_EMPTY.getValue());
        }
        if (null == driverBaseInfoView.getDriverInfo().getDriverLeader()) {
            log.error("司机主管为空");
            return ResponseResult.fail(AccountStatusCode.DRIVER_LEADER_EMPTY.getCode(),AccountStatusCode.DRIVER_LEADER_EMPTY.getValue());
        }
        if (null == driverBaseInfoView.getDriverInfo().getGender()) {
            log.error("司机性别为空");
            return ResponseResult.fail(AccountStatusCode.GENDER_EMPTY.getCode(),AccountStatusCode.GENDER_EMPTY.getValue());
        }
        if (null == driverBaseInfoView.getDriverInfo().getDriverName() || "".equals(driverBaseInfoView.getDriverInfo().getDriverName())) {
            log.info("司机姓名为空！");
            return ResponseResult.fail(AccountStatusCode.DRIVER_NAME_EMPTY.getCode(), AccountStatusCode.DRIVER_NAME_EMPTY.getValue());
        }
        if (null == driverBaseInfoView.getDriverLicenceInfo().getIdentityCardId() || "".equals(driverBaseInfoView.getDriverLicenceInfo().getIdentityCardId())) {
            log.info("司机身份证为空！");
            return ResponseResult.fail(AccountStatusCode.DRIVER_CARD_ID_EMPTY.getCode(),AccountStatusCode.DRIVER_CARD_ID_EMPTY.getValue());
        }
        if (null == driverBaseInfoView.getDriverInfo().getPhoneNumber() || "".equals(driverBaseInfoView.getDriverInfo().getPhoneNumber())) {
            log.info("司机手机号为空！");
            return ResponseResult.fail(AccountStatusCode.DRIVER_PHONE_NUM_EMPTY.getCode(),AccountStatusCode.DRIVER_PHONE_NUM_EMPTY.getValue());
        }
        if (null == driverBaseInfoView.getDriverBaseInfo().getContractEndDate()) {
            log.info("合同协议有效期止为空！");
            return ResponseResult.fail(AccountStatusCode.CONTRACT_END_DATE_EMPTY.getCode(),AccountStatusCode.CONTRACT_END_DATE_EMPTY.getValue());
        }
        if (null == driverBaseInfoView.getDriverBaseInfo().getContractStartDate()) {
            log.info("合同协议有效期始为空！");
            return ResponseResult.fail(AccountStatusCode.CONTRACT_START_DATE_EMPTY.getCode(),AccountStatusCode.CONTRACT_START_DATE_EMPTY.getValue());
        }
        if (null == driverBaseInfoView.getDriverInfo().getUseStatus()) {
            log.info("启用状态为空！");
            return ResponseResult.fail(AccountStatusCode.DRIVER_USE_STATUS.getCode(),AccountStatusCode.DRIVER_USE_STATUS.getValue());
        }
        return driverInfoService.changeDriverBaseInfo(driverBaseInfoView, 2);
    }

    /**
     * 查询司机详情
     */
    @RequestMapping(value = {"/driverInfo"}, produces = "application/json; charset=utf-8", method = RequestMethod.POST)
    public ResponseResult driverInfo(@RequestBody DriverChangeRequest request) {
        return ResponseResult.success(driverInfoService.getDriverBaseInfoView(request.getId()));
    }
    /**
     * 修改司机地址信息
     */
    @RequestMapping(value = {"/updateDriverAddress"}, produces = "application/json; charset=utf-8", method = RequestMethod.POST)
    public ResponseResult updateDriverAddress(@RequestBody UpdateDriverAddressRequest request) {
        if(null == request.getId() ){
            return ResponseResult.fail(AccountStatusCode.ID_EMPTY.getCode(),AccountStatusCode.ID_EMPTY.getValue());
        }
        if(null == request.getAddress() || "".equals(request.getAddress())){
            return ResponseResult.fail(AccountStatusCode.ADDRESS_EMPTY.getCode(),AccountStatusCode.ADDRESS_EMPTY.getValue());
        }
        if(null == request.getAddressLongitude() || "".equals(request.getAddressLongitude())){
            return ResponseResult.fail(AccountStatusCode.LONGITUDE_EMPTY.getCode(),AccountStatusCode.LONGITUDE_EMPTY.getValue());
        }
        if(null == request.getAddressLatitude() || "".equals(request.getAddressLatitude())){
            return ResponseResult.fail(AccountStatusCode.LATITUDE_EMPTY.getCode(),AccountStatusCode.LATITUDE_EMPTY.getValue());
        }
        if(null == request.getPhoneNumber() || "".equals(request.getPhoneNumber())){
            return  ResponseResult.fail(AccountStatusCode.PHONE_NUM_EMPTY.getCode(),AccountStatusCode.PHONE_NUM_EMPTY.getValue());
        }
        return driverInfoService.UpdateDriverAddressRequest(request);
    }

}
