package com.lkmotion.yesincar.mapper;

import com.lkmotion.yesincar.entity.CarDispatchTimeThresholdSet;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CarDispatchTimeThresholdSetMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CarDispatchTimeThresholdSet record);

    int insertSelective(CarDispatchTimeThresholdSet record);

    CarDispatchTimeThresholdSet selectByPrimaryKey(Integer id);

    CarDispatchTimeThresholdSet selectByCityAndServiceType(@Param("cityCode") String cityCode, @Param("serviceTypeId") int serviceTypeId);

    int updateByPrimaryKeySelective(CarDispatchTimeThresholdSet record);

    int updateByPrimaryKey(CarDispatchTimeThresholdSet record);
}
