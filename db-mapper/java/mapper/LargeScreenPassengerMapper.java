package com.lkmotion.yesincar.mapper;

import com.lkmotion.yesincar.entity.LargeScreenPassenger;

public interface LargeScreenPassengerMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_large_screen_passenger
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_large_screen_passenger
     *
     * @mbggenerated
     */
    int insert(LargeScreenPassenger record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_large_screen_passenger
     *
     * @mbggenerated
     */
    int insertSelective(LargeScreenPassenger record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_large_screen_passenger
     *
     * @mbggenerated
     */
    LargeScreenPassenger selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_large_screen_passenger
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(LargeScreenPassenger record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_large_screen_passenger
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(LargeScreenPassenger record);
}