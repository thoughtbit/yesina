package com.lkmotion.yesincar.dto;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

/**
 * @author chaopengfei
 */
@Data
public class SmsSendRequest{

	private String[] receivers;
	private List<SmsTemplateDto> data;

	@Override
	public String toString() {
		return "SmsSendRequest [reveivers=" + Arrays.toString(receivers) + ", data=" + data + "]";
	}

}