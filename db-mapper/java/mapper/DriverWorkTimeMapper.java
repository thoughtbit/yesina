package com.lkmotion.yesincar.mapper;

import com.lkmotion.yesincar.entity.DriverWorkTime;

public interface DriverWorkTimeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_driver_work_time
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_driver_work_time
     *
     * @mbggenerated
     */
    int insert(DriverWorkTime record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_driver_work_time
     *
     * @mbggenerated
     */
    int insertSelective(DriverWorkTime record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_driver_work_time
     *
     * @mbggenerated
     */
    DriverWorkTime selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_driver_work_time
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(DriverWorkTime record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_driver_work_time
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(DriverWorkTime record);
}