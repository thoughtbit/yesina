package com.lkmotion.yesincar.service;

import com.lkmotion.yesincar.dto.ResponseResult;
import com.lkmotion.yesincar.dto.car.CarBaseInfoView;

/**
 * @author LiZhaoTeng
 **/
public interface CarBaseService {
    public void updateCarBaseInfoView(CarBaseInfoView view);
    public ResponseResult addCarBaseInfo(CarBaseInfoView view) ;
}
