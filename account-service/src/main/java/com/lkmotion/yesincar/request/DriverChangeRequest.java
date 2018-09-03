package com.lkmotion.yesincar.request;

import com.lkmotion.yesincar.dto.driver.DriverBaseInfoView;
import lombok.Data;

import java.util.Date;

/**
 * @author LiZhaoTeng
 **/
@Data
public class DriverChangeRequest  {
    private DriverBaseInfoView data;
    private int id;
    private int driverStatus;
    private int carId;
    private Date searchCreateStartTime;
    private Date searchCreateEndTime;

}
