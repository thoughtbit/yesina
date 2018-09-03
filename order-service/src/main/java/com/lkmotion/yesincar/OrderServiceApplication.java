package com.lkmotion.yesincar;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 功能描述
 * @author liheng
 * @date 2018/8/25
 */
@SpringBootApplication
@MapperScan("com.lkmotion.yesincar.mapper")
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}
}
