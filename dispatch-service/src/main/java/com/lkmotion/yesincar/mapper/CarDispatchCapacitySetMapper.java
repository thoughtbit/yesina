package com.lkmotion.yesincar.mapper;

import com.lkmotion.yesincar.entity.CarDispatchCapacitySet;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CarDispatchCapacitySetMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CarDispatchCapacitySet record);

    int insertSelective(CarDispatchCapacitySet record);

    CarDispatchCapacitySet selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CarDispatchCapacitySet record);

    int updateByPrimaryKey(CarDispatchCapacitySet record);

    CarDispatchCapacitySet getCarDispatchCapacitySet(@Param("cityCode") String cityCode, @Param("timeType") int timeType);

}
