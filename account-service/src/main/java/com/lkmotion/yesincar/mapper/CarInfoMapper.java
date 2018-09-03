package com.lkmotion.yesincar.mapper;

import com.lkmotion.yesincar.entity.CarInfo;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author lizhaoteng
 */
@Service
public interface CarInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CarInfo record);

    int insertSelective(CarInfo record);

    CarInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CarInfo record);

    int updateByPrimaryKey(CarInfo record);

    int countCarInfo(Map<String, Object> param);
}
