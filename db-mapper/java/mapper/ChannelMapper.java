package com.lkmotion.yesincar.mapper;

import com.lkmotion.yesincar.entity.Channel;

public interface ChannelMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_channel
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_channel
     *
     * @mbggenerated
     */
    int insert(Channel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_channel
     *
     * @mbggenerated
     */
    int insertSelective(Channel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_channel
     *
     * @mbggenerated
     */
    Channel selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_channel
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Channel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_channel
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Channel record);
}