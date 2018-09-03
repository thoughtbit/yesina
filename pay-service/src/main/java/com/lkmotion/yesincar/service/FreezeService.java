package com.lkmotion.yesincar.service;

import com.lkmotion.yesincar.dto.ResponseResult;

/**
 * @author chaopengfei
 * @date 2018/8/21
 */
public interface FreezeService {

    ResponseResult freeze(Integer yid, Integer orderId, Double price);

    ResponseResult unFreeze(Integer yid, Integer orderId);

    ResponseResult pay(Integer yid, Integer orderId, Double price);
}
