package com.lkmotion.yesincar.mapper;

import com.lkmotion.yesincar.dto.baseinfo.BaseInfoDriverDto;
import com.lkmotion.yesincar.dto.baseinfo.BaseInfoPassengerDto;
import com.lkmotion.yesincar.dto.baseinfo.BaseInfoVehicleDto;

/**
 * 基础信息Mapper
 *
 * @author ZhuBin
 * @date 2018/8/24
 */
public interface BaseInfoMapper {

    BaseInfoDriverDto getBaseInfoDriver(Integer id);

    BaseInfoPassengerDto getBaseInfoPassenger(Integer id);

    /**
     * 获取车辆基本信息
     *
     * @param id 主键
     * @return 车辆
     */
    BaseInfoVehicleDto getBaseInfoVehicle(Integer id);
}
