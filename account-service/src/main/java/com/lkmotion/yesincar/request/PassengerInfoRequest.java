package com.lkmotion.yesincar.request;

import com.lkmotion.yesincar.dto.passenger.PassengerInfoView;
import lombok.Data;

/**
 * 乘客请求信息
 *
 * @author LiZhaoTeng
 * @date 2018/08/15
 **/
@Data
public class PassengerInfoRequest {

    private Integer id;
    private PassengerInfoView data;
}
