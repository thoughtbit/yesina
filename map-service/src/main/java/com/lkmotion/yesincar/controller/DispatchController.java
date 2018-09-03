package com.lkmotion.yesincar.controller;

import com.lkmotion.yesincar.dto.ResponseResult;
import com.lkmotion.yesincar.dto.map.request.DispatchRequest;
import com.lkmotion.yesincar.service.DispatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author chaopengfei
 * @date 2018/8/20
 */
@RestController
public class DispatchController {
	
	@Autowired
	private DispatchService dispatchService;
	
	@PostMapping("/vehicleDispatch")
	public ResponseResult dispatch(@RequestBody DispatchRequest dispatchRequest) {
		return dispatchService.dispatch(dispatchRequest);
	}
}
