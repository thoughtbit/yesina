package com.lkmotion.yesincar.controller;

import com.lkmotion.yesincar.dto.ResponseResult;
import com.lkmotion.yesincar.dto.map.request.VehicleRequest;
import com.lkmotion.yesincar.service.VehicleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author chaopengfei
 */
@RestController
public class VehicleController {

	@Autowired
	private VehicleService vehicleService;
	
    @RequestMapping(value = "/vehicle",method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult vehicle(@RequestBody  VehicleRequest vehicleRequest){
    	
    	vehicleService.uploadCar(vehicleRequest);
    	return ResponseResult.success(null);
    }
}
