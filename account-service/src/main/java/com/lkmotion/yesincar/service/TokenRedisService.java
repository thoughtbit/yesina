package com.lkmotion.yesincar.service;

/**
 * @author LiZhaoTeng
 **/
public interface TokenRedisService {
    public void put(String phoneNum, String token, Integer expHours);
    public String get(String phoneNum);
    public Boolean expire(String phoneNum, Integer expHours);
    public void delete(String phoneNum);
}
