package com.lkmotion.yesincar.service;

import com.lkmotion.yesincar.dto.ResponseResult;
import com.lkmotion.yesincar.dto.map.Route;
import com.lkmotion.yesincar.dto.map.request.DistanceRequest;

/**
 * 距离测量
 * @author chaopengfei
 */
public interface DistanceService {
	/**
	 * 测量距离
	 * @param distanceRequest
	 * @return
	 */
	ResponseResult<Route> distance(DistanceRequest distanceRequest);
	
}
