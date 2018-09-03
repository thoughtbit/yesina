package com.lkmotion.yesincar.service;

import com.lkmotion.yesincar.dto.ResponseResult;
import com.lkmotion.yesincar.dto.map.Dispatch;
import com.lkmotion.yesincar.dto.map.request.DispatchRequest;

/**
 * 
 * @author chaopengfei
 */
public interface DispatchService {
	
	/**
	 * 调度车辆
	 * @param dispatchRequest
	 * @return
	 */
	ResponseResult<Dispatch> dispatch(DispatchRequest dispatchRequest);
}
