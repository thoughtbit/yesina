package com.lkmotion.yesincar.mapper;

import com.lkmotion.yesincar.entity.BaseInfoCompany;

public interface BaseInfoCompanyMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_base_info_company
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_base_info_company
     *
     * @mbggenerated
     */
    int insert(BaseInfoCompany record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_base_info_company
     *
     * @mbggenerated
     */
    int insertSelective(BaseInfoCompany record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_base_info_company
     *
     * @mbggenerated
     */
    BaseInfoCompany selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_base_info_company
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(BaseInfoCompany record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_base_info_company
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(BaseInfoCompany record);
}