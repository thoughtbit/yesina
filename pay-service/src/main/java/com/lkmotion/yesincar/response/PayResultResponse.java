package com.lkmotion.yesincar.response;

import lombok.Data;

/**
 * @author chaopengfei
 * @date 2018/8/30
 */
@Data
public class PayResultResponse {
    private Integer status;

    public PayResultResponse(Integer status) {
        this.status = status;
    }
}
