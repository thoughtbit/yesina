package com.lkmotion.yesincar.service;

import com.lkmotion.yesincar.dto.ResponseResult;
import com.lkmotion.yesincar.entity.FenceCreateEntity;
import com.lkmotion.yesincar.response.AmapFenceInResponse;
import com.lkmotion.yesincar.response.AmapFenceSearchResponse;

import java.util.List;

/**
 * 
 * @author chaopengfei
 */
public interface FenceService {

	AmapFenceInResponse isInFence(String longitude,String latitude,String diu);

	ResponseResult createFence(String gid, String name, String points , String description, String validTime, String enable);

	FenceCreateEntity changeStatus(String gid ,String enable);

	AmapFenceSearchResponse searchFence(String id, String gid, String name, String pageNo, String pageSize, String enable,
										String startTime, String endTime);

	ResponseResult searchFence(List<String> gids);

	Boolean delFence(String gid);
}
