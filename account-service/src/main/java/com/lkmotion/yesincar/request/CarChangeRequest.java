package com.lkmotion.yesincar.request;

import com.lkmotion.yesincar.dto.car.CarBaseInfoView;

/**
 * @author LiZhaoTeng
 **/
public class CarChangeRequest   {
    private CarBaseInfoView data;

    public CarBaseInfoView getData() {
        return data;
    }

    public CarChangeRequest setData(CarBaseInfoView data) {
        this.data = data;
        return this;
    }
}
