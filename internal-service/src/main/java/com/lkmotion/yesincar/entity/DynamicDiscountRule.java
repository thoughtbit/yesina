package com.lkmotion.yesincar.entity;

import java.util.Date;

public class DynamicDiscountRule {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_dynamic_discount_rule.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_dynamic_discount_rule.service_type
     *
     * @mbggenerated
     */
    private String serviceType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_dynamic_discount_rule.car_level
     *
     * @mbggenerated
     */
    private String carLevel;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_dynamic_discount_rule.time_set
     *
     * @mbggenerated
     */
    private String timeSet;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_dynamic_discount_rule.date_type
     *
     * @mbggenerated
     */
    private Integer dateType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_dynamic_discount_rule.week_set
     *
     * @mbggenerated
     */
    private String weekSet;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_dynamic_discount_rule.special_date_set
     *
     * @mbggenerated
     */
    private String specialDateSet;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_dynamic_discount_rule.discount_max_price
     *
     * @mbggenerated
     */
    private Double discountMaxPrice;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_dynamic_discount_rule.valid_start_time
     *
     * @mbggenerated
     */
    private Date validStartTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_dynamic_discount_rule.valid_end_time
     *
     * @mbggenerated
     */
    private Date validEndTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_dynamic_discount_rule.is_unuse
     *
     * @mbggenerated
     */
    private Integer isUnuse;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_dynamic_discount_rule.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_dynamic_discount_rule.update_time
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_dynamic_discount_rule.id
     *
     * @return the value of tbl_dynamic_discount_rule.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_dynamic_discount_rule.id
     *
     * @param id the value for tbl_dynamic_discount_rule.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_dynamic_discount_rule.service_type
     *
     * @return the value of tbl_dynamic_discount_rule.service_type
     *
     * @mbggenerated
     */
    public String getServiceType() {
        return serviceType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_dynamic_discount_rule.service_type
     *
     * @param serviceType the value for tbl_dynamic_discount_rule.service_type
     *
     * @mbggenerated
     */
    public void setServiceType(String serviceType) {
        this.serviceType = serviceType == null ? null : serviceType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_dynamic_discount_rule.car_level
     *
     * @return the value of tbl_dynamic_discount_rule.car_level
     *
     * @mbggenerated
     */
    public String getCarLevel() {
        return carLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_dynamic_discount_rule.car_level
     *
     * @param carLevel the value for tbl_dynamic_discount_rule.car_level
     *
     * @mbggenerated
     */
    public void setCarLevel(String carLevel) {
        this.carLevel = carLevel == null ? null : carLevel.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_dynamic_discount_rule.time_set
     *
     * @return the value of tbl_dynamic_discount_rule.time_set
     *
     * @mbggenerated
     */
    public String getTimeSet() {
        return timeSet;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_dynamic_discount_rule.time_set
     *
     * @param timeSet the value for tbl_dynamic_discount_rule.time_set
     *
     * @mbggenerated
     */
    public void setTimeSet(String timeSet) {
        this.timeSet = timeSet == null ? null : timeSet.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_dynamic_discount_rule.date_type
     *
     * @return the value of tbl_dynamic_discount_rule.date_type
     *
     * @mbggenerated
     */
    public Integer getDateType() {
        return dateType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_dynamic_discount_rule.date_type
     *
     * @param dateType the value for tbl_dynamic_discount_rule.date_type
     *
     * @mbggenerated
     */
    public void setDateType(Integer dateType) {
        this.dateType = dateType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_dynamic_discount_rule.week_set
     *
     * @return the value of tbl_dynamic_discount_rule.week_set
     *
     * @mbggenerated
     */
    public String getWeekSet() {
        return weekSet;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_dynamic_discount_rule.week_set
     *
     * @param weekSet the value for tbl_dynamic_discount_rule.week_set
     *
     * @mbggenerated
     */
    public void setWeekSet(String weekSet) {
        this.weekSet = weekSet == null ? null : weekSet.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_dynamic_discount_rule.special_date_set
     *
     * @return the value of tbl_dynamic_discount_rule.special_date_set
     *
     * @mbggenerated
     */
    public String getSpecialDateSet() {
        return specialDateSet;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_dynamic_discount_rule.special_date_set
     *
     * @param specialDateSet the value for tbl_dynamic_discount_rule.special_date_set
     *
     * @mbggenerated
     */
    public void setSpecialDateSet(String specialDateSet) {
        this.specialDateSet = specialDateSet == null ? null : specialDateSet.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_dynamic_discount_rule.discount_max_price
     *
     * @return the value of tbl_dynamic_discount_rule.discount_max_price
     *
     * @mbggenerated
     */
    public Double getDiscountMaxPrice() {
        return discountMaxPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_dynamic_discount_rule.discount_max_price
     *
     * @param discountMaxPrice the value for tbl_dynamic_discount_rule.discount_max_price
     *
     * @mbggenerated
     */
    public void setDiscountMaxPrice(Double discountMaxPrice) {
        this.discountMaxPrice = discountMaxPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_dynamic_discount_rule.valid_start_time
     *
     * @return the value of tbl_dynamic_discount_rule.valid_start_time
     *
     * @mbggenerated
     */
    public Date getValidStartTime() {
        return validStartTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_dynamic_discount_rule.valid_start_time
     *
     * @param validStartTime the value for tbl_dynamic_discount_rule.valid_start_time
     *
     * @mbggenerated
     */
    public void setValidStartTime(Date validStartTime) {
        this.validStartTime = validStartTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_dynamic_discount_rule.valid_end_time
     *
     * @return the value of tbl_dynamic_discount_rule.valid_end_time
     *
     * @mbggenerated
     */
    public Date getValidEndTime() {
        return validEndTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_dynamic_discount_rule.valid_end_time
     *
     * @param validEndTime the value for tbl_dynamic_discount_rule.valid_end_time
     *
     * @mbggenerated
     */
    public void setValidEndTime(Date validEndTime) {
        this.validEndTime = validEndTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_dynamic_discount_rule.is_unuse
     *
     * @return the value of tbl_dynamic_discount_rule.is_unuse
     *
     * @mbggenerated
     */
    public Integer getIsUnuse() {
        return isUnuse;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_dynamic_discount_rule.is_unuse
     *
     * @param isUnuse the value for tbl_dynamic_discount_rule.is_unuse
     *
     * @mbggenerated
     */
    public void setIsUnuse(Integer isUnuse) {
        this.isUnuse = isUnuse;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_dynamic_discount_rule.create_time
     *
     * @return the value of tbl_dynamic_discount_rule.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_dynamic_discount_rule.create_time
     *
     * @param createTime the value for tbl_dynamic_discount_rule.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_dynamic_discount_rule.update_time
     *
     * @return the value of tbl_dynamic_discount_rule.update_time
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_dynamic_discount_rule.update_time
     *
     * @param updateTime the value for tbl_dynamic_discount_rule.update_time
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}