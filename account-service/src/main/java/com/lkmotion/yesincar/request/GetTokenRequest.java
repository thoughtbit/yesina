package com.lkmotion.yesincar.request;

import lombok.Data;

/**
 * 获取请求token
 * @author LiZhaoTeng
 **/
@Data
public class GetTokenRequest  {

    private String phoneNum;
    private String verifyCode;
    private Integer id;
    /**
     * 住址类型
     */
    private Integer type;
    public Integer identityStatus;
    /**
     * 1:乘客，2：司机，3：车机，4：大屏
     */
    public String equipType;
    private Double longitude;

    private Double latitude;

    private Double speed;

    private Double accuracy;

    private Double direction;

    private Double height;

    private String city = "330100";

    private String token;
}
