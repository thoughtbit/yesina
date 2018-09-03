package com.lkmotion.yesincar.file.entity;

import java.util.Date;

public class CheckRecord {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_check_record.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_check_record.driver_id
     *
     * @mbggenerated
     */
    private Integer driverId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_check_record.phone_number
     *
     * @mbggenerated
     */
    private String phoneNumber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_check_record.main_driving_license
     *
     * @mbggenerated
     */
    private String mainDrivingLicense;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_check_record.vice_driving_license
     *
     * @mbggenerated
     */
    private String viceDrivingLicense;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_check_record.all_driving_license
     *
     * @mbggenerated
     */
    private String allDrivingLicense;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_check_record.group_driving_license
     *
     * @mbggenerated
     */
    private String groupDrivingLicense;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_check_record.driver_name
     *
     * @mbggenerated
     */
    private String driverName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_check_record.identity_card_id
     *
     * @mbggenerated
     */
    private String identityCardId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_check_record.check_status
     *
     * @mbggenerated
     */
    private Integer checkStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_check_record.check_text
     *
     * @mbggenerated
     */
    private String checkText;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_check_record.submit_time
     *
     * @mbggenerated
     */
    private Date submitTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_check_record.check_time
     *
     * @mbggenerated
     */
    private Date checkTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_check_record.main_idcard_license
     *
     * @mbggenerated
     */
    private String mainIdcardLicense;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_check_record.vice_idcard_license
     *
     * @mbggenerated
     */
    private String viceIdcardLicense;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_check_record.group_idcard_license
     *
     * @mbggenerated
     */
    private String groupIdcardLicense;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_check_record.main_car_license
     *
     * @mbggenerated
     */
    private String mainCarLicense;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_check_record.vice_car_license
     *
     * @mbggenerated
     */
    private String viceCarLicense;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_check_record.all_car_license
     *
     * @mbggenerated
     */
    private String allCarLicense;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_check_record.group_car_license
     *
     * @mbggenerated
     */
    private String groupCarLicense;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_check_record.plate_number
     *
     * @mbggenerated
     */
    private String plateNumber;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_check_record.id
     *
     * @return the value of tbl_check_record.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_check_record.id
     *
     * @param id the value for tbl_check_record.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_check_record.driver_id
     *
     * @return the value of tbl_check_record.driver_id
     *
     * @mbggenerated
     */
    public Integer getDriverId() {
        return driverId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_check_record.driver_id
     *
     * @param driverId the value for tbl_check_record.driver_id
     *
     * @mbggenerated
     */
    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_check_record.phone_number
     *
     * @return the value of tbl_check_record.phone_number
     *
     * @mbggenerated
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_check_record.phone_number
     *
     * @param phoneNumber the value for tbl_check_record.phone_number
     *
     * @mbggenerated
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_check_record.main_driving_license
     *
     * @return the value of tbl_check_record.main_driving_license
     *
     * @mbggenerated
     */
    public String getMainDrivingLicense() {
        return mainDrivingLicense;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_check_record.main_driving_license
     *
     * @param mainDrivingLicense the value for tbl_check_record.main_driving_license
     *
     * @mbggenerated
     */
    public void setMainDrivingLicense(String mainDrivingLicense) {
        this.mainDrivingLicense = mainDrivingLicense == null ? null : mainDrivingLicense.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_check_record.vice_driving_license
     *
     * @return the value of tbl_check_record.vice_driving_license
     *
     * @mbggenerated
     */
    public String getViceDrivingLicense() {
        return viceDrivingLicense;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_check_record.vice_driving_license
     *
     * @param viceDrivingLicense the value for tbl_check_record.vice_driving_license
     *
     * @mbggenerated
     */
    public void setViceDrivingLicense(String viceDrivingLicense) {
        this.viceDrivingLicense = viceDrivingLicense == null ? null : viceDrivingLicense.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_check_record.all_driving_license
     *
     * @return the value of tbl_check_record.all_driving_license
     *
     * @mbggenerated
     */
    public String getAllDrivingLicense() {
        return allDrivingLicense;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_check_record.all_driving_license
     *
     * @param allDrivingLicense the value for tbl_check_record.all_driving_license
     *
     * @mbggenerated
     */
    public void setAllDrivingLicense(String allDrivingLicense) {
        this.allDrivingLicense = allDrivingLicense == null ? null : allDrivingLicense.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_check_record.group_driving_license
     *
     * @return the value of tbl_check_record.group_driving_license
     *
     * @mbggenerated
     */
    public String getGroupDrivingLicense() {
        return groupDrivingLicense;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_check_record.group_driving_license
     *
     * @param groupDrivingLicense the value for tbl_check_record.group_driving_license
     *
     * @mbggenerated
     */
    public void setGroupDrivingLicense(String groupDrivingLicense) {
        this.groupDrivingLicense = groupDrivingLicense == null ? null : groupDrivingLicense.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_check_record.driver_name
     *
     * @return the value of tbl_check_record.driver_name
     *
     * @mbggenerated
     */
    public String getDriverName() {
        return driverName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_check_record.driver_name
     *
     * @param driverName the value for tbl_check_record.driver_name
     *
     * @mbggenerated
     */
    public void setDriverName(String driverName) {
        this.driverName = driverName == null ? null : driverName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_check_record.identity_card_id
     *
     * @return the value of tbl_check_record.identity_card_id
     *
     * @mbggenerated
     */
    public String getIdentityCardId() {
        return identityCardId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_check_record.identity_card_id
     *
     * @param identityCardId the value for tbl_check_record.identity_card_id
     *
     * @mbggenerated
     */
    public void setIdentityCardId(String identityCardId) {
        this.identityCardId = identityCardId == null ? null : identityCardId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_check_record.check_status
     *
     * @return the value of tbl_check_record.check_status
     *
     * @mbggenerated
     */
    public Integer getCheckStatus() {
        return checkStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_check_record.check_status
     *
     * @param checkStatus the value for tbl_check_record.check_status
     *
     * @mbggenerated
     */
    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_check_record.check_text
     *
     * @return the value of tbl_check_record.check_text
     *
     * @mbggenerated
     */
    public String getCheckText() {
        return checkText;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_check_record.check_text
     *
     * @param checkText the value for tbl_check_record.check_text
     *
     * @mbggenerated
     */
    public void setCheckText(String checkText) {
        this.checkText = checkText == null ? null : checkText.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_check_record.submit_time
     *
     * @return the value of tbl_check_record.submit_time
     *
     * @mbggenerated
     */
    public Date getSubmitTime() {
        return submitTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_check_record.submit_time
     *
     * @param submitTime the value for tbl_check_record.submit_time
     *
     * @mbggenerated
     */
    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_check_record.check_time
     *
     * @return the value of tbl_check_record.check_time
     *
     * @mbggenerated
     */
    public Date getCheckTime() {
        return checkTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_check_record.check_time
     *
     * @param checkTime the value for tbl_check_record.check_time
     *
     * @mbggenerated
     */
    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_check_record.main_idcard_license
     *
     * @return the value of tbl_check_record.main_idcard_license
     *
     * @mbggenerated
     */
    public String getMainIdcardLicense() {
        return mainIdcardLicense;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_check_record.main_idcard_license
     *
     * @param mainIdcardLicense the value for tbl_check_record.main_idcard_license
     *
     * @mbggenerated
     */
    public void setMainIdcardLicense(String mainIdcardLicense) {
        this.mainIdcardLicense = mainIdcardLicense == null ? null : mainIdcardLicense.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_check_record.vice_idcard_license
     *
     * @return the value of tbl_check_record.vice_idcard_license
     *
     * @mbggenerated
     */
    public String getViceIdcardLicense() {
        return viceIdcardLicense;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_check_record.vice_idcard_license
     *
     * @param viceIdcardLicense the value for tbl_check_record.vice_idcard_license
     *
     * @mbggenerated
     */
    public void setViceIdcardLicense(String viceIdcardLicense) {
        this.viceIdcardLicense = viceIdcardLicense == null ? null : viceIdcardLicense.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_check_record.group_idcard_license
     *
     * @return the value of tbl_check_record.group_idcard_license
     *
     * @mbggenerated
     */
    public String getGroupIdcardLicense() {
        return groupIdcardLicense;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_check_record.group_idcard_license
     *
     * @param groupIdcardLicense the value for tbl_check_record.group_idcard_license
     *
     * @mbggenerated
     */
    public void setGroupIdcardLicense(String groupIdcardLicense) {
        this.groupIdcardLicense = groupIdcardLicense == null ? null : groupIdcardLicense.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_check_record.main_car_license
     *
     * @return the value of tbl_check_record.main_car_license
     *
     * @mbggenerated
     */
    public String getMainCarLicense() {
        return mainCarLicense;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_check_record.main_car_license
     *
     * @param mainCarLicense the value for tbl_check_record.main_car_license
     *
     * @mbggenerated
     */
    public void setMainCarLicense(String mainCarLicense) {
        this.mainCarLicense = mainCarLicense == null ? null : mainCarLicense.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_check_record.vice_car_license
     *
     * @return the value of tbl_check_record.vice_car_license
     *
     * @mbggenerated
     */
    public String getViceCarLicense() {
        return viceCarLicense;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_check_record.vice_car_license
     *
     * @param viceCarLicense the value for tbl_check_record.vice_car_license
     *
     * @mbggenerated
     */
    public void setViceCarLicense(String viceCarLicense) {
        this.viceCarLicense = viceCarLicense == null ? null : viceCarLicense.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_check_record.all_car_license
     *
     * @return the value of tbl_check_record.all_car_license
     *
     * @mbggenerated
     */
    public String getAllCarLicense() {
        return allCarLicense;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_check_record.all_car_license
     *
     * @param allCarLicense the value for tbl_check_record.all_car_license
     *
     * @mbggenerated
     */
    public void setAllCarLicense(String allCarLicense) {
        this.allCarLicense = allCarLicense == null ? null : allCarLicense.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_check_record.group_car_license
     *
     * @return the value of tbl_check_record.group_car_license
     *
     * @mbggenerated
     */
    public String getGroupCarLicense() {
        return groupCarLicense;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_check_record.group_car_license
     *
     * @param groupCarLicense the value for tbl_check_record.group_car_license
     *
     * @mbggenerated
     */
    public void setGroupCarLicense(String groupCarLicense) {
        this.groupCarLicense = groupCarLicense == null ? null : groupCarLicense.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_check_record.plate_number
     *
     * @return the value of tbl_check_record.plate_number
     *
     * @mbggenerated
     */
    public String getPlateNumber() {
        return plateNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_check_record.plate_number
     *
     * @param plateNumber the value for tbl_check_record.plate_number
     *
     * @mbggenerated
     */
    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber == null ? null : plateNumber.trim();
    }
}