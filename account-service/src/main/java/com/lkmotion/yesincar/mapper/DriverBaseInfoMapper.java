package com.lkmotion.yesincar.mapper;

import com.lkmotion.yesincar.entity.DriverBaseInfo;
import org.springframework.stereotype.Service;

/**
 * @author lizhaoteng
 */
@Service
public interface DriverBaseInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DriverBaseInfo record);

    int insertSelective(DriverBaseInfo record);

    DriverBaseInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DriverBaseInfo record);

    int updateByPrimaryKey(DriverBaseInfo record);
}
