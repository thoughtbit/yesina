package com.lkmotion.yesincar.service;

/**
 * @author LiZhaoTeng
 **/

public interface DriverInfoCacheService {
    /**
     * 查询司机信息
     * @param phoneNum
     * @return
     */
    public String get(String phoneNum);

    /**
     * 保存司机信息
     * @param phoneNum
     * @param driverInfo
     * @return
     */
    public void put(String phoneNum, String driverInfo);

}
