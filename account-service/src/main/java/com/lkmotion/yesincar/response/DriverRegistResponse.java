package com.lkmotion.yesincar.response;

import lombok.Data;

/**
 * @author LiZhaoTeng
 **/
@Data
public class DriverRegistResponse  {
    private String accessToken ;

    private String phoneNumber;

    private Integer gerder;

    private String driverName;

    private Integer checkStatus;

    private String headImg;

    private Integer driverId;

    private String jpushId;

    private String workStatus;

}
