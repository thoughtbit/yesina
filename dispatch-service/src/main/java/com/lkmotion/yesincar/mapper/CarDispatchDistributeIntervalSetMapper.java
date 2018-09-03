package com.lkmotion.yesincar.mapper;

import com.lkmotion.yesincar.entity.CarDispatchDistributeIntervalSet;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CarDispatchDistributeIntervalSetMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CarDispatchDistributeIntervalSet record);

    int insertSelective(CarDispatchDistributeIntervalSet record);

    CarDispatchDistributeIntervalSet selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CarDispatchDistributeIntervalSet record);

    int updateByPrimaryKey(CarDispatchDistributeIntervalSet record);

    CarDispatchDistributeIntervalSet selectByCityCodeAndServiceType(@Param("cityCode") String cityCode, @Param("serviceTypeId") Integer serviceTypeId);
}
