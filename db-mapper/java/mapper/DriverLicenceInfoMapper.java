package com.lkmotion.yesincar.mapper;

import com.lkmotion.yesincar.entity.DriverLicenceInfo;

public interface DriverLicenceInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_driver_licence_info
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_driver_licence_info
     *
     * @mbggenerated
     */
    int insert(DriverLicenceInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_driver_licence_info
     *
     * @mbggenerated
     */
    int insertSelective(DriverLicenceInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_driver_licence_info
     *
     * @mbggenerated
     */
    DriverLicenceInfo selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_driver_licence_info
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(DriverLicenceInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_driver_licence_info
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(DriverLicenceInfo record);
}