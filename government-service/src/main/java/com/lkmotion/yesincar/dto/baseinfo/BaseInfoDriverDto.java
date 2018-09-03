package com.lkmotion.yesincar.dto.baseinfo;

import lombok.Data;

/**
 * @author lizhaoteng
 **/
@Data
public class BaseInfoDriverDto {
    private String driverPhone;
    private String driverGender;
    private String driverBirthday;
    private String driverNation;
    private String driverMaritalStatus;
    private String drverLanguageLevel;
    private String driverEducation;
    private String driverCensus;
    private String driverAddress;
    private String driverContactAddress;
    private String licenseld;
    private Integer getDriverLicenseDate;
    private Integer driverLicenseOn;
    private Integer driverLicenseOff;
    private Integer taxiDriver;
    private String certificateNo;
    private String networkCarIssueOrGanization;
    private Integer networkCarlssueDate;
    private Integer getNetworkCarProoDate;
    private Integer networkCarProofOn;
    private Integer networkCarProofOff;
    private Integer registerDate;
    private Integer commercialType;
    private String contractCompany;
    private Integer contractOn;
    private Integer contractOff;
    private Integer state;
}
