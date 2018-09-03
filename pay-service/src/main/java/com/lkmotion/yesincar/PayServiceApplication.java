package com.lkmotion.yesincar;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author chaopengfei
 */
@SpringBootApplication
@MapperScan("com.lkmotion.yesincar.mapper")
public class PayServiceApplication {

	public static void main(String[] args) {
		System.setProperty("spring.devtools.restart.enabled","false");
		SpringApplication.run(PayServiceApplication.class, args);
	}
}
