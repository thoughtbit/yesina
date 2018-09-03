package com.lkmotion.yesincar.controller;

import com.lkmotion.yesincar.constant.AccountStatusCode;
import com.lkmotion.yesincar.dto.ResponseResult;
import com.lkmotion.yesincar.dto.car.CarBaseInfoView;
import com.lkmotion.yesincar.request.CarChangeRequest;
import com.lkmotion.yesincar.request.CarInfoRequest;
import com.lkmotion.yesincar.service.impl.CarBaseServiceImpl;
import com.lkmotion.yesincar.service.impl.CarInfoServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * boss车辆
 *
 * @author LiZhaoTeng
 * @date 2018/08/09
 **/
@RestController
@RequestMapping("/car")
@Slf4j
public class CarInfoController {
    @Autowired
    private CarBaseServiceImpl carService;
    @Autowired
    private CarInfoServiceImpl carInfoService;

    /**
     * 修改车辆
     */
    @RequestMapping(value = {"/updateCar"}, produces = "application/json; charset=utf-8", method = RequestMethod.POST)
    public ResponseResult carUpdate(@RequestBody CarChangeRequest request) {
        CarBaseInfoView carBaseInfoView = request.getData();
        if (null == carBaseInfoView.getCarInfo().getCityCode() || "".equals(carBaseInfoView.getCarInfo().getCityCode())) {
            log.error("城市代码为空！");
            return ResponseResult.fail(AccountStatusCode.CITY_CODE_EMPTY.getCode(),AccountStatusCode.CITY_CODE_EMPTY.getValue());
        }
        if (null == carBaseInfoView.getCarInfo().getCarLevelId() || 0 == carBaseInfoView.getCarInfo().getCarLevelId()) {
            log.error("车辆级别id为空！");
            return ResponseResult.fail(AccountStatusCode.CAR_LEVEL_EMPTY.getCode(),AccountStatusCode.CAR_LEVEL_EMPTY.getValue());
        }
        if (null == carBaseInfoView.getCarInfo().getCarTypeId() || 0 == carBaseInfoView.getCarInfo().getCarTypeId()) {
            log.error("车辆类型id为空！");
            return ResponseResult.fail(AccountStatusCode.CAR_TYPE_EMPTY.getCode(),AccountStatusCode.CAR_TYPE_EMPTY.getValue());
        }
        if (null == carBaseInfoView.getCarInfo().getPlateNumber() || "".equals(carBaseInfoView.getCarInfo().getPlateNumber())) {
            log.error("车牌号为空！");
            return ResponseResult.fail(AccountStatusCode.PLATE_NUMBER_EMPTY.getCode(),AccountStatusCode.PLATE_NUMBER_EMPTY.getValue());
        }
        if (null == carBaseInfoView.getCarBaseInfo().getVinNumber() || "".equals(carBaseInfoView.getCarBaseInfo().getVinNumber())) {
            log.error("vin为空！");
            return ResponseResult.fail(AccountStatusCode.VIM_NUMBER_EMPTY.getCode(),AccountStatusCode.VIM_NUMBER_EMPTY.getValue());
        }
        if (null == carBaseInfoView.getCarInfo().getLargeScreenDeviceBrand() || "".equals(carBaseInfoView.getCarInfo().getLargeScreenDeviceBrand())) {
            log.error("大屏品牌为空！");
            return ResponseResult.fail(AccountStatusCode.LARGE_SCREEN_DEVICE_BRAND.getCode(),AccountStatusCode.LARGE_SCREEN_DEVICE_BRAND.getValue());
        }
        if (null == carBaseInfoView.getCarInfo().getLargeScreenDeviceCode() || "".equals(carBaseInfoView.getCarInfo().getLargeScreenDeviceCode())) {
            log.error("大屏编号为空！");
            return ResponseResult.fail(AccountStatusCode.LARGE_SCREEN_DEVICE_CODE.getCode(),AccountStatusCode.LARGE_SCREEN_DEVICE_CODE.getValue());
        }
        if (null == carBaseInfoView.getCarInfo().getUseStatus()) {
            log.error("车辆状态为空！");
            return ResponseResult.fail(AccountStatusCode.USE_STATUS.getCode(),AccountStatusCode.USE_STATUS.getValue());
        }
        int carBaseInfoCode = carService.updateCarBaseInfoView(request.getData().getCarBaseInfo());
        int carInfoCode = carInfoService.updateByPrimaryKeySelective(request.getData().getCarInfo());
        if (1 != carBaseInfoCode) {
            return ResponseResult.fail("修改CarBaseInfo失败");
        } else if (1 != carInfoCode) {
            return ResponseResult.fail("修改CarInfo失败");
        } else {
            return ResponseResult.success("");
        }
    }

    /**
     * 车辆录入
     */
    @RequestMapping(value = {"/car"}, produces = "application/json; charset=utf-8", method = RequestMethod.POST)
    public ResponseResult carAdd(@RequestBody CarInfoRequest request) {
        CarBaseInfoView carBaseInfoView = request.getData();
        if (null == carBaseInfoView.getCarInfo().getCityCode() || "".equals(carBaseInfoView.getCarInfo().getCityCode())) {
            log.error("城市代码为空！");
            return ResponseResult.fail(AccountStatusCode.CITY_CODE_EMPTY.getCode(),AccountStatusCode.CITY_CODE_EMPTY.getValue());
        }
        if (null == carBaseInfoView.getCarInfo().getCarLevelId() || 0 == carBaseInfoView.getCarInfo().getCarLevelId()) {
            log.error("车辆级别id为空！");
            return ResponseResult.fail(AccountStatusCode.CAR_LEVEL_EMPTY.getCode(),AccountStatusCode.CAR_LEVEL_EMPTY.getValue());
        }
        if (null == carBaseInfoView.getCarInfo().getCarTypeId() || 0 == carBaseInfoView.getCarInfo().getCarTypeId()) {
            log.error("车辆类型id为空！");
            return ResponseResult.fail(AccountStatusCode.CAR_TYPE_EMPTY.getCode(),AccountStatusCode.CAR_TYPE_EMPTY.getValue());
        }
        if (null == carBaseInfoView.getCarInfo().getPlateNumber() || "".equals(carBaseInfoView.getCarInfo().getPlateNumber())) {
            log.error("车牌号为空！");
            return ResponseResult.fail(AccountStatusCode.PLATE_NUMBER_EMPTY.getCode(),AccountStatusCode.PLATE_NUMBER_EMPTY.getValue());
        }
        if (null == carBaseInfoView.getCarBaseInfo().getVinNumber() || "".equals(carBaseInfoView.getCarBaseInfo().getVinNumber())) {
            log.error("vin为空！");
            return ResponseResult.fail(AccountStatusCode.VIM_NUMBER_EMPTY.getCode(),AccountStatusCode.VIM_NUMBER_EMPTY.getValue());
        }
        if (null == carBaseInfoView.getCarInfo().getLargeScreenDeviceBrand() || "".equals(carBaseInfoView.getCarInfo().getLargeScreenDeviceBrand())) {
            log.error("大屏品牌为空！");
            return ResponseResult.fail(AccountStatusCode.LARGE_SCREEN_DEVICE_BRAND.getCode(),AccountStatusCode.LARGE_SCREEN_DEVICE_BRAND.getValue());
        }
        if (null == carBaseInfoView.getCarInfo().getLargeScreenDeviceCode() || "".equals(carBaseInfoView.getCarInfo().getLargeScreenDeviceCode())) {
            log.error("大屏编号为空！");
            return ResponseResult.fail(AccountStatusCode.LARGE_SCREEN_DEVICE_CODE.getCode(),AccountStatusCode.LARGE_SCREEN_DEVICE_CODE.getValue());
        }
        if (null == carBaseInfoView.getCarInfo().getUseStatus()) {
            log.error("车辆状态为空！");
            return ResponseResult.fail(AccountStatusCode.USE_STATUS.getCode(),AccountStatusCode.USE_STATUS.getValue());
        }

        return carService.addCarBaseInfo(request.getData());
    }
}
