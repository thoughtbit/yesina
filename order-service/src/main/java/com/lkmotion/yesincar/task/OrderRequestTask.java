package com.lkmotion.yesincar.task;

import com.lkmotion.yesincar.constatnt.BusinessInterfaceStatus;
import com.lkmotion.yesincar.constatnt.EnableDisableEnum;
import com.lkmotion.yesincar.dto.BaseInfoDto;
import com.lkmotion.yesincar.dto.ResponseResult;
import com.lkmotion.yesincar.entity.CarLevel;
import com.lkmotion.yesincar.entity.Channel;
import com.lkmotion.yesincar.entity.City;
import com.lkmotion.yesincar.entity.ServiceType;
import com.lkmotion.yesincar.mapper.CarLevelMapper;
import com.lkmotion.yesincar.mapper.ChannelMapper;
import com.lkmotion.yesincar.mapper.CityMapper;
import com.lkmotion.yesincar.mapper.ServiceTypeMapper;
import com.lkmotion.yesincar.request.OrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 订单任务请求任务
 * @author liheng
 * @date 2018/9/1
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class OrderRequestTask {

    @Autowired
    private CityMapper cityMapper;
    @Autowired
    private ServiceTypeMapper serviceTypeMapper;
    @Autowired
    private ChannelMapper channelMapper;
    @Autowired
    private CarLevelMapper carLevelMapper;


    /**
     * 校验基础信息
     * @param orderRequest
     * @return
     */
    public ResponseResult checkBaseInfo(OrderRequest orderRequest){
        City city = cityMapper.selectByPrimaryKey(orderRequest.getCityCode());
        if(null == city){
            return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), "该城市不存在");
        }else{
            if(StringUtils.isNotBlank(city.getCityStatus().toString()) && city.getCityStatus().equals(EnableDisableEnum.ENABLE.getCode())){
                return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), "该城市未开通");
            }
        }
        ServiceType serviceType = serviceTypeMapper.selectByPrimaryKey(orderRequest.getServiceTypeId());
        if(null == serviceType){
            return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), "该服务类型为空");
        }else{
            if(StringUtils.isNotBlank(serviceType.getServiceTypeStatus().toString()) && serviceType.getServiceTypeStatus().equals(EnableDisableEnum.ENABLE.getCode())){
                return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), "该服务类型未启用");
            }
        }
        Channel channel = channelMapper.selectByPrimaryKey(orderRequest.getChannelId());
        if(null == channel){
            return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), "无渠道");
        }else{
            if(StringUtils.isNotBlank(channel.getChannelStatus().toString()) && channel.getChannelStatus().equals(EnableDisableEnum.ENABLE.getCode())){
                return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), "该渠道未启用");
            }
        }
        CarLevel carLevel = carLevelMapper.selectByPrimaryKey(orderRequest.getCarLevelId());
        if(null == carLevel){
            return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), "车辆级别无");
        }else{
            if(StringUtils.isNotBlank(carLevel.getEnable().toString()) && carLevel.getEnable().equals(EnableDisableEnum.ENABLE.getCode())){
                return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), "该车辆未启用");
            }
        }
        BaseInfoDto baseInfoDto = new BaseInfoDto();
        baseInfoDto.setCityName(city.getCityName());
        baseInfoDto.setServiceTypeName(serviceType.getServiceTypeName());
        baseInfoDto.setChannelName(channel.getChannelName());
        baseInfoDto.setCarLevelName(carLevel.getLabel());
        return ResponseResult.success(baseInfoDto);
    }


}
