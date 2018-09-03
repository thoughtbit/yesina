package com.lkmotion.yesincar.dto.map.request;

import lombok.Data;

/**
 * 
 * @author chaopengfei
 */
@Data
public class DistanceRequest {
	
	private String originLongitude;
	
	private String originLatitude;
	
	private String destinationLongitude;
	
	private String destinationLatitude;
	
}
