package com.lkmotion.yesincar.util;
/**
 * 
 * @author chaopengfei
 */
public class AmapLocationUtils {
	
	public static String getLongitude(String location) {
		String[] locationArray = location.split(",");
		return locationArray[0];
	}
	
	public static String getLatitude(String location) {
		String[] locationArray = location.split(",");
		return locationArray[1];
	}
}
