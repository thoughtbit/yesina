package com.lkmotion.yesincar.request;

import lombok.Data;

/**
 * @author chaopengfei
 * @date 2018/8/16
 */
@Data
public class PayRequest {

    private Integer yid ;
    private Double capital;
    private Double giveFee;
    private String source;
    private Integer rechargeType;
    private Integer orderId;

}
