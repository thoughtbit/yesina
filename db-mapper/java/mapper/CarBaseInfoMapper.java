package com.lkmotion.yesincar.mapper;

import com.lkmotion.yesincar.entity.CarBaseInfo;

public interface CarBaseInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_car_base_info
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_car_base_info
     *
     * @mbggenerated
     */
    int insert(CarBaseInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_car_base_info
     *
     * @mbggenerated
     */
    int insertSelective(CarBaseInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_car_base_info
     *
     * @mbggenerated
     */
    CarBaseInfo selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_car_base_info
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(CarBaseInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_car_base_info
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(CarBaseInfo record);
}