package com.lkmotion.yesincar.mapper;

import com.lkmotion.yesincar.entity.CarInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CarInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CarInfo record);

    int insertSelective(CarInfo record);

    CarInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CarInfo record);

    int updateByPrimaryKey(CarInfo record);
}
