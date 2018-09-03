package com.lkmotion.yesincar.dto.map;


import java.util.List;

import lombok.Data;
/**
 * 
 * @author chaopengfei
 */
@Data
public class Points {
	
	private Location startPoint;
	
	private Location endPoint;
	
	private List<Location> points;
	
	private Integer pointCount ;
	
	
}