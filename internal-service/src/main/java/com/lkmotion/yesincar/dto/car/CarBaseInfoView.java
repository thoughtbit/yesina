package com.lkmotion.yesincar.dto.car;

import com.lkmotion.yesincar.entity.CarBaseInfo;
import com.lkmotion.yesincar.entity.CarInfo;
import lombok.Data;

/**
 * @author LiZhaoTeng
 * @date 2018-08-09 18:00
 **/
@Data
public class CarBaseInfoView {
    private CarBaseInfo carBaseInfo;
    private  CarInfo carInfo;

}
