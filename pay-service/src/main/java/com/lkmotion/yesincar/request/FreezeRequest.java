package com.lkmotion.yesincar.request;

import lombok.Data;

/**
 * @author chaopengfei
 * @date 2018/8/21
 */
@Data
public class FreezeRequest {

    private Double price;

    private Integer orderId;

    private Integer yid;
}
