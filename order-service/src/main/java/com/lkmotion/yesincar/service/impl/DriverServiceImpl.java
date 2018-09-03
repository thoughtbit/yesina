package com.lkmotion.yesincar.service.impl;

import com.lkmotion.yesincar.entity.DriverInfo;
import com.lkmotion.yesincar.mapper.DriverInfoMapper;
import com.lkmotion.yesincar.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 功能描述
 *
 * @author liheng
 * @date 2018/8/28
 */
@Service
public class DriverServiceImpl implements DriverService {

    @Autowired
    private DriverInfoMapper driverInfoMapper;

    @Override
    public List<DriverInfo> selectDriverList() {
        return driverInfoMapper.selectDriverInfoList();
    }
}
