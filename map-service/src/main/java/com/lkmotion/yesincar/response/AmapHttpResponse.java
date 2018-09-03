package com.lkmotion.yesincar.response;

import lombok.Data;
import net.sf.json.JSONObject;
/**
 * 
 * @author chaopengfei
 */
@Data
public class AmapHttpResponse {
	
	private int status;
	
	private JSONObject result;
}
