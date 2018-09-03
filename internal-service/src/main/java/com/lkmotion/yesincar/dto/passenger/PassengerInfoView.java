package com.lkmotion.yesincar.dto.passenger;

import com.lkmotion.yesincar.entity.PassengerAddress;
import com.lkmotion.yesincar.entity.PassengerInfo;
import lombok.Data;

/**
 *  乘客详情信息
 * @author LiZhaoTeng
 **/
@Data
public class PassengerInfoView {
    private PassengerInfo passengerInfo;
    private PassengerAddress passengerAdress;


}
