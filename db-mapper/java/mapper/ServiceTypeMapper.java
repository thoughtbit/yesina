package com.lkmotion.yesincar.mapper;

import com.lkmotion.yesincar.entity.ServiceType;

public interface ServiceTypeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_service_type
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_service_type
     *
     * @mbggenerated
     */
    int insert(ServiceType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_service_type
     *
     * @mbggenerated
     */
    int insertSelective(ServiceType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_service_type
     *
     * @mbggenerated
     */
    ServiceType selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_service_type
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(ServiceType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_service_type
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(ServiceType record);
}