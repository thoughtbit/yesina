package com.lkmotion.yesincar.mapper;

import com.lkmotion.yesincar.entity.CarType;

public interface CarTypeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_car_type
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_car_type
     *
     * @mbggenerated
     */
    int insert(CarType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_car_type
     *
     * @mbggenerated
     */
    int insertSelective(CarType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_car_type
     *
     * @mbggenerated
     */
    CarType selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_car_type
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(CarType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_car_type
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(CarType record);
}