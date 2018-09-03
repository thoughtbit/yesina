package com.lkmotion.yesincar.mapper;

import com.lkmotion.yesincar.entity.CarDispatchSpecialPeriodSet;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CarDispatchSpecialPeriodSetMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CarDispatchSpecialPeriodSet record);

    int insertSelective(CarDispatchSpecialPeriodSet record);

    CarDispatchSpecialPeriodSet selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CarDispatchSpecialPeriodSet record);

    int updateByPrimaryKey(CarDispatchSpecialPeriodSet record);

    CarDispatchSpecialPeriodSet getByCityCode(@Param("cityCode") String cityCode, @Param("serviceTypeId") int serviceTypeId);
}
