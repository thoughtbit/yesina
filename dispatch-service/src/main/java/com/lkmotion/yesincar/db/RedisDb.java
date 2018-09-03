package com.lkmotion.yesincar.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

/**
 * @author dulin
 */
@Repository
public class RedisDb {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void set(String key, String value, long expireTime) {
        redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
        // redisTemplate.opsForValue().set
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public boolean setnx(String key, String value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }
}
