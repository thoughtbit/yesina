package com.lkmotion.yesincar.request;

import com.lkmotion.yesincar.dto.car.CarBaseInfoView;
import lombok.Data;

/**
 * 车辆信息
 *
 * @author LiZhaoTeng
 * @date 2018/08/15
 **/
@Data
public class CarInfoRequest {
    private CarBaseInfoView data;
}
