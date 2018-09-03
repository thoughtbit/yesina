package com.lkmotion.yesincar.service;

import com.lkmotion.yesincar.entity.DriverInfo;

import java.util.List;

/**
 * 功能描述
 *
 * @author liheng
 * @date 2018/8/28
 */

public interface DriverService {

    /**
     *  查询司机
     */
    List<DriverInfo> selectDriverList();
}
