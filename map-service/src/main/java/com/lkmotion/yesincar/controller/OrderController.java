package com.lkmotion.yesincar.controller;

import com.lkmotion.yesincar.dto.ResponseResult;
import com.lkmotion.yesincar.dto.map.request.OrderRequest;
import com.lkmotion.yesincar.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 
 * @author chaopengfei
 */
@RestController
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@PostMapping("/order")
	public ResponseResult order(@RequestBody OrderRequest orderRequest){
		String result = orderService.order(orderRequest);
		return ResponseResult.success(result);
	}
}
