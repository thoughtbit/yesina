package com.lkmotion.yesincar.mapper;

import com.lkmotion.yesincar.entity.City;

public interface CityMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_city
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_city
     *
     * @mbggenerated
     */
    int insert(City record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_city
     *
     * @mbggenerated
     */
    int insertSelective(City record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_city
     *
     * @mbggenerated
     */
    City selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_city
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(City record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_city
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(City record);
}