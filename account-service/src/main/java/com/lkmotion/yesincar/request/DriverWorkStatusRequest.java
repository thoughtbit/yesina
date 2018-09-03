package com.lkmotion.yesincar.request;

import lombok.Data;

/**
 * @author lizhaoteng
 **/
@Data
public class DriverWorkStatusRequest {
    private Integer id;

    private Integer workStatus;

    private Integer csWorkStatus;

    private Integer isFollowing;

    private Integer status;

    private Double longitude;

    private Double latitude;

    private String city = "330100";

    private Double speed;

    private Double accuracy;

    private Double direction;

    private Double height;

    private Integer locationType;

    private String phoneNum;

}
