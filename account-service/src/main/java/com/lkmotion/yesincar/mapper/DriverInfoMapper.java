package com.lkmotion.yesincar.mapper;

import com.lkmotion.yesincar.entity.DriverInfo;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author lizhaoteng
 */
@Service
public interface DriverInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DriverInfo record);

    int insertSelective(DriverInfo record);

    DriverInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DriverInfo record);

    int updateByPrimaryKey(DriverInfo record);

    DriverInfo queryDriverInfoByPhoneNum(String phoneNum);

    int queryDriverInfoByCarId(Integer carId);

    int updateDriverInfoByPhoneNum(DriverInfo driverInfo);

    public int updateCsWorkStatusByDriverId(Map<String, Object> param);

}
