package com.lkmotion.yesincar.mapper;

import com.lkmotion.yesincar.entity.DriverBaseInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DriverBaseInfoMapper {

    DriverBaseInfo selectByPrimaryKey(Integer id);

}
