package com.lkmotion.yesincar.dao;

import com.lkmotion.yesincar.entity.DriverLicenceInfo;
import com.lkmotion.yesincar.mapper.DriverLicenceInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *	审核资料
 * @author LiZhaoTeng
 **/
@Repository
public class DriverLicenceInfoDao {
    @Autowired
    private DriverLicenceInfoMapper driverLicenceInfoMapper;

    public int deleteByPrimaryKey(Integer id) {
        return driverLicenceInfoMapper.deleteByPrimaryKey(id);
    }


    public int insert(DriverLicenceInfo record) {
        return driverLicenceInfoMapper.insert(record);
    }


    public int insertSelective(DriverLicenceInfo record) {
        return driverLicenceInfoMapper.insertSelective(record);
    }


    public DriverLicenceInfo selectByPrimaryKey(Integer id) {
        return driverLicenceInfoMapper.selectByPrimaryKey(id);
    }
    public DriverLicenceInfo selectByDriverId(Integer driverId) {
        return driverLicenceInfoMapper.selectByDriverId(driverId);
    }


    public int updateByPrimaryKeySelective(DriverLicenceInfo record) {
        return driverLicenceInfoMapper.updateByPrimaryKeySelective(record);
    }


    public int updateByPrimaryKey(DriverLicenceInfo record) {
        return driverLicenceInfoMapper.updateByPrimaryKey(record);
    }
}
