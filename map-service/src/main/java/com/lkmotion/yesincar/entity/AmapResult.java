package com.lkmotion.yesincar.entity;

import lombok.Data;

/**
 * 
 * @author chaopengfei
 * @param <T>
 */
@Data
public class AmapResult<T> {

	private int status;
	
	private T data;
	
	
}
