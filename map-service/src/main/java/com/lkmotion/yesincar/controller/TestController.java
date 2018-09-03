package com.lkmotion.yesincar.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * 
 * @author chaopengfei
 */
@RestController
public class TestController {

	@RequestMapping("/test")
	public String test() {
		return "map success";
	}
}
