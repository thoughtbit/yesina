package com.lkmotion.yesincar.service;

import com.lkmotion.yesincar.dto.ResponseResult;
import com.lkmotion.yesincar.dto.map.Dispatch;
import com.lkmotion.yesincar.dto.map.Distance;
import com.lkmotion.yesincar.dto.map.request.DistanceRequest;
import com.lkmotion.yesincar.dto.map.request.OrderRequest;
import com.lkmotion.yesincar.dto.push.PushLoopBatchRequest;
import com.lkmotion.yesincar.dto.push.PushRequest;
import com.lkmotion.yesincar.entity.Order;
import com.lkmotion.yesincar.request.DispatchRequest;
import com.lkmotion.yesincar.request.SmsSendRequest;
import com.lkmotion.yesincar.request.SmsTemplateDto;
import com.lkmotion.yesincar.util.RestTemplateHepler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author d
 * @date 2018/8/14
 */
@Service
@Slf4j
public class HttpService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ConfigService configService;

    public ResponseResult sendSms(String phone, String smsCode, Map<String, Object> templateMap) {
        String url = configService.messageServiceUrl() + "/sms/send";
        SmsSendRequest request = new SmsSendRequest();
        String[] receivers = new String[1];
        receivers[0] = phone;
        request.setReceivers(receivers);
        List<SmsTemplateDto> list = new ArrayList<>();
        SmsTemplateDto dto = new SmsTemplateDto();
        dto.setId(smsCode);
        dto.setTemplateMap(templateMap);
        list.add(dto);
        request.setData(list);

        ResponseResult response = restTemplate.postForObject(url, request, ResponseResult.class);
        return response;
    }

    public ResponseResult updateAmapOrder(OrderRequest orderRequest) {
        String url = configService.mapServiceUrl() + "/order";
        ResponseResult response = restTemplate.postForObject(url, orderRequest, ResponseResult.class);
        return response;
    }

    public ResponseResult<Dispatch> dispatch(DispatchRequest dispatchRequest) {
        String url = configService.mapServiceUrl() + "/vehicleDispatch";
        ResponseResult response = restTemplate.postForObject(url, dispatchRequest, ResponseResult.class);
        try {
            log.info(response.toString());
            Dispatch o = RestTemplateHepler.parse(response, Dispatch.class);
            return ResponseResult.success(o);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public double calDistance(DistanceRequest distanceRequest) {
        String url = configService.mapServiceUrl() + "/distance";
        double distance = 0;
        ResponseResult response = restTemplate.getForObject(url, ResponseResult.class, distanceRequest);
        if (response.getData() != null) {
            try {
                Distance o = RestTemplateHepler.parse(response, Distance.class);
                distance = o.getDistance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return distance;
    }

    public boolean updateOrder(Order order) {
        String url = configService.orderServiceUrl() + "/order/updateOrder";
        ResponseResult response = restTemplate.postForObject(url, order, ResponseResult.class);
        if (response.getCode() == 0) {
            return true;
        }
        return false;
    }

    public ResponseResult pushMsg(PushRequest pushRequest) {
        String url = configService.messageServiceUrl() + "/push/message";
        ResponseResult response = restTemplate.postForObject(url, pushRequest, ResponseResult.class);
        return response;
    }

    public ResponseResult loopMessage(PushLoopBatchRequest request) {
        String url = configService.messageServiceUrl() + "/loop/message";
        ResponseResult response = restTemplate.postForObject(url, request, ResponseResult.class);
        return response;
    }

    public ResponseResult loopMessageBatch(PushLoopBatchRequest request) {
        String url = configService.messageServiceUrl() + "/loop/batch/message";
        ResponseResult response = restTemplate.postForObject(url, request, ResponseResult.class);
        return response;
    }
}

