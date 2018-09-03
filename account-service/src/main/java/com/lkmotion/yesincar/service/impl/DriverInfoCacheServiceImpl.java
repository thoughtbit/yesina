package com.lkmotion.yesincar.service.impl;

import com.lkmotion.yesincar.service.DriverInfoCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 司机信息
 *
 * @author LiZhaoTeng
 **/
@Service
@Slf4j
public class DriverInfoCacheServiceImpl implements DriverInfoCacheService {
    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> vOps;
    private final String DRIVER_KEY = "driver_info_";

    @Override
    public String get(String phoneNum) {
        String key = DRIVER_KEY + phoneNum;
        return vOps.get(key);
    }

    @Override
    public void put(String phoneNum, String value) {
        String key = DRIVER_KEY + phoneNum;

        vOps.set(key, value);

        log.info("hashKey:" + key + " 缓存在Redis中的hashValue为:" + value);
    }

}
