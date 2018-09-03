package com.lkmotion.yesincar.mapper;

import com.lkmotion.yesincar.entity.CarDispatchDirectRouteOrderRadiusSet;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CarDispatchDirectRouteOrderRadiusSetMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CarDispatchDirectRouteOrderRadiusSet record);

    int insertSelective(CarDispatchDirectRouteOrderRadiusSet record);

    CarDispatchDirectRouteOrderRadiusSet selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CarDispatchDirectRouteOrderRadiusSet record);

    int updateByPrimaryKey(CarDispatchDirectRouteOrderRadiusSet record);

    CarDispatchDirectRouteOrderRadiusSet getCarDispatchDirectRouteOrderRadiusSet(@Param("cityCode") String cityCode, @Param("serviceTypeId") int serviceTypeId, @Param("type") int type);
}
