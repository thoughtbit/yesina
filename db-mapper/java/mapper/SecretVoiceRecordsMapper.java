package com.lkmotion.yesincar.mapper;

import com.lkmotion.yesincar.entity.secretVoiceRecords;

public interface secretVoiceRecordsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_secret_voice_records
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_secret_voice_records
     *
     * @mbggenerated
     */
    int insert(secretVoiceRecords record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_secret_voice_records
     *
     * @mbggenerated
     */
    int insertSelective(secretVoiceRecords record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_secret_voice_records
     *
     * @mbggenerated
     */
    secretVoiceRecords selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_secret_voice_records
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(secretVoiceRecords record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_secret_voice_records
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(secretVoiceRecords record);
}