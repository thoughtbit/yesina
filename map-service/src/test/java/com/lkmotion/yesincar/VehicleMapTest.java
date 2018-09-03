package com.lkmotion.yesincar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.lkmotion.yesincar.service.VehicleService;
import com.lkmotion.yesincar.service.impl.VehicleServiceImpl;
/**
 * 
 * @author chaopengfei
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VehicleMapTest {
	
	@Autowired
	private VehicleServiceImpl service;
	
	@Test
	public void uploadCar() {
		service.uploadCar(null);
	}
}
