package com.lkmotion.yesincar.mapper;

import com.lkmotion.yesincar.entity.DriverInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DriverInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DriverInfo record);

    int insertSelective(DriverInfo record);

    DriverInfo selectByPrimaryKey(Integer id);

    DriverInfo selectByCarId(@Param("carId") Integer carId);

    int updateByPrimaryKeySelective(DriverInfo record);

    int updateByPrimaryKey(DriverInfo record);

    int countWorkDriver(@Param("cityCode") String cityCode, @Param("carType") int carType);
}
