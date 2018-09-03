package com.lkmotion.yesincar.service;

import com.lkmotion.yesincar.dto.map.request.OrderRequest;

/**
 * 
 * @author chaopengfei
 */
public interface OrderService {

	/**
	 * 同步订单
	 * @param orderRequest
	 * @return
	 */
	public String order(OrderRequest orderRequest) ;
}
