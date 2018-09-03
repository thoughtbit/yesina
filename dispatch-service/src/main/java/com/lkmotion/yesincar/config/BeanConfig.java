package com.lkmotion.yesincar.config;

import com.lkmotion.yesincar.lock.RedisLock;
import com.lkmotion.yesincar.service.DispatchService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dulin
 */

@Configuration
public class BeanConfig {
    @Bean
    public DispatchService dispatchService() {
        return DispatchService.ins();
    }

    @Bean
    public RedisLock redisLock() {
        return RedisLock.ins();
    }
}
