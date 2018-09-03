package com.lkmotion.yesincar.mapper;

import com.lkmotion.yesincar.entity.CarBaseInfo;
import org.springframework.stereotype.Service;

/**
 * @author lizhaoteng
 */
@Service
public interface CarBaseInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CarBaseInfo record);

    int insertSelective(CarBaseInfo record);

    CarBaseInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CarBaseInfo record);

    int updateByPrimaryKey(CarBaseInfo record);
}
