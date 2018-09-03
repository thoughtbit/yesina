package com.lkmotion.yesincar.service.impl;

import com.lkmotion.yesincar.constant.AmapEnum;
import com.lkmotion.yesincar.constant.AmapResultConfig;
import com.lkmotion.yesincar.constant.AmapUrlConfig;
import com.lkmotion.yesincar.constatnt.BusinessInterfaceStatus;
import com.lkmotion.yesincar.dto.ResponseResult;
import com.lkmotion.yesincar.dto.map.Dispatch;
import com.lkmotion.yesincar.dto.map.Vehicle;
import com.lkmotion.yesincar.dto.map.request.DispatchRequest;
import com.lkmotion.yesincar.entity.AmapResult;
import com.lkmotion.yesincar.service.DispatchService;
import com.lkmotion.yesincar.util.AmapLocationUtils;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author chaopengfei
 */
@Service
@Slf4j
public class DispatchServiceImpl implements DispatchService {

	@Value("${amap.key}")
	private String amapKey;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public ResponseResult<Dispatch> dispatch(DispatchRequest dispatchRequest) {

		StringBuilder urlBuild = new StringBuilder();
		urlBuild.append(AmapUrlConfig.DISPATCH_URL);
		urlBuild.append("?key=" + amapKey);
		urlBuild.append("&orderID=" + dispatchRequest.getOrderId());
		urlBuild.append("&amapOID=");
		urlBuild.append("&customerDeviceID=" + dispatchRequest.getCustomerDeviceId());
		urlBuild.append("&orderType=1");
		// + dispatchRequest.getVehicleType()
		urlBuild.append("&vehicleType=2");
		urlBuild.append("&orderCity=" + dispatchRequest.getOrderCity());
		urlBuild.append("&orderTime=" + dispatchRequest.getOrderTime()/1000);
		urlBuild.append("&startTime=" + dispatchRequest.getStartTime()/1000);
		String startName = dispatchRequest.getStartName().replaceAll(" ", "");
		urlBuild.append("&startName=" + startName);
		urlBuild.append("&start=" + dispatchRequest.getStartLongitude() + ","
				+ dispatchRequest.getStartLatitude());
		String endName = dispatchRequest.getEndName().replaceAll(" ", "");
		urlBuild.append("&endName=" + endName);
		urlBuild.append("&end=" + dispatchRequest.getEndLongitude() + "," + dispatchRequest.getEndLatitude());
		urlBuild.append("&radius=" + dispatchRequest.getRadius());
		urlBuild.append("&maxCount=" + dispatchRequest.getMaxCount());

		String url = urlBuild.toString();

		log.info("高德地图：调度车辆请求信息：" + url);
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
		log.info("高德地图：调度车辆返回信息：" + responseEntity.getBody());

		AmapResult<Dispatch> amapResult = parseResponseEntity(responseEntity);
		if(amapResult.getStatus() == AmapEnum.OK.getCode()) {

			return ResponseResult.success(amapResult.getData());
		}else {
			return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(),
					BusinessInterfaceStatus.FAIL.getValue());
		}
	}

	private AmapResult<Dispatch> parseResponseEntity(ResponseEntity<String> responseEntity){
		AmapResult<Dispatch> response = new AmapResult<>();
		try {
			String body = responseEntity.getBody();
			JSONObject result = JSONObject.fromObject(body);

			if (result.has(AmapResultConfig.AMAP_DATA)) {
				response.setStatus(AmapEnum.OK.getCode());

				Dispatch dispatch = new Dispatch();
				JSONObject data = result.getJSONObject(AmapResultConfig.AMAP_DATA);
				Integer count = 0;
				String orderId = "";
				if (data.has(AmapResultConfig.AMAP_COUNT)) {
					count = data.getInt(AmapResultConfig.AMAP_COUNT);
					dispatch.setCount(count);
				}
				if (data.has(AmapResultConfig.AMAP_ORDER_ID)) {
					orderId = data.getString(AmapResultConfig.AMAP_ORDER_ID);
					dispatch.setOrderId(orderId);
				}
				if (data.has(AmapResultConfig.AMAP_VEHICLES)) {
					List<Vehicle> vehiclesObj = new ArrayList<Vehicle>();
					JSONArray vehicles = data.getJSONArray(AmapResultConfig.AMAP_VEHICLES);
					for (int i = 0; i < vehicles.size(); i++) {
						Vehicle vehicle = new Vehicle();
						JSONObject vehicleJSON = vehicles.getJSONObject(i);
						if (vehicleJSON.has(AmapResultConfig.AMAP_LOCATION)) {
							String locationStr = vehicleJSON.getString(AmapResultConfig.AMAP_LOCATION);
							vehicle.setLongitude(AmapLocationUtils.getLongitude(locationStr));
							vehicle.setLatitude(AmapLocationUtils.getLatitude(locationStr));
						}
						if(vehicleJSON.has(AmapResultConfig.AMAP_DISTANCE)) {
							vehicle.setDistance(vehicleJSON.getString(AmapResultConfig.AMAP_DISTANCE));
						}
						if (vehicleJSON.has(AmapResultConfig.AMAP_VEHICLE_ID)) {
							String vehicleId = vehicleJSON.getString(AmapResultConfig.AMAP_VEHICLE_ID);
							vehicle.setVehicleId(vehicleId);
						}
						vehiclesObj.add(vehicle);
					}
					dispatch.setVehicles(vehiclesObj);
				}
				response.setData(dispatch);
			}
		}catch (Exception e) {
			response.setStatus(AmapEnum.EXCEPTION.getCode());
		}
		return response;
	}

}
