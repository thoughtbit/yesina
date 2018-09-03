package com.lkmotion.yesincar.service;

import com.lkmotion.yesincar.dto.ResponseResult;
import com.lkmotion.yesincar.request.OrderRequest;

/**
 * 功能描述
 *
 * @author liheng
 * @date 2018/8/25
 */

public interface OrderGrabService {

    /**
     * 司机抢单
     * @param orderRequest
     * @return
     */
    ResponseResult grab(OrderRequest orderRequest) throws Exception;
}
