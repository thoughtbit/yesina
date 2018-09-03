package com.lkmotion.yesincar.dto.map;
import java.util.List;
import lombok.Data;
/**
 * 
 * @author chaopengfei
 */
@Data
public class Dispatch{
	
	private Integer count;
	
	private String orderId;
	
	private List<Vehicle> vehicles;
}