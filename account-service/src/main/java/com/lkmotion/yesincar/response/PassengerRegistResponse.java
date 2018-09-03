package com.lkmotion.yesincar.response;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 响应
 * @author LiZhaoTeng
 **/
@Data
public class PassengerRegistResponse  {

    private String status;
    private String accessToken ;

    private String phoneNum;

    private Byte gender;
    private Integer id;
    private String passengerName;
    /**
     * 余额
     */
    private BigDecimal balance;

    private String headImg;

    private String jpushId;


}
