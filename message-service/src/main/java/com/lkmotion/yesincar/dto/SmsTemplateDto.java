package com.lkmotion.yesincar.dto;

import lombok.Data;

import java.util.Map;

/**
 * @author chaopengfei
 */
@Data
public class SmsTemplateDto {

	private String id;

	private Map<String, Object> templateMap;

	@Override
	public String toString() {
		return "SmsTemplateDto [id=" + id + ", templateMap=" + templateMap + "]";
	}

}