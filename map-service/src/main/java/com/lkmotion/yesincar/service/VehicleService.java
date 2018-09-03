package com.lkmotion.yesincar.service;

import com.lkmotion.yesincar.dto.map.request.VehicleRequest;

/**
 * 
 * @author chaopengfei
 */
public interface VehicleService {

	/**
	 * 同步车辆
	 * @param vehicleRequest
	 * @return
	 */
	public String uploadCar(VehicleRequest vehicleRequest);
}
