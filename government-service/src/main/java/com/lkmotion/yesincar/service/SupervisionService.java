package com.lkmotion.yesincar.service;

import com.lkmotion.yesincar.dto.government.SupervisionData;

/**
 * 监管上报服务
 *
 * @author ZhuBin
 * @date 2018/8/23
 */
public interface SupervisionService {

    /**
     * 分发上报任务
     *
     * @param data 上报对象DTO
     * @throws Exception 异常信息
     */
    void dispatch(SupervisionData data) throws Exception;

}