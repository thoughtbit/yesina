package com.lkmotion.yesincar.dto.driver;

import com.lkmotion.yesincar.entity.DriverBaseInfo;
import com.lkmotion.yesincar.entity.DriverInfo;
import com.lkmotion.yesincar.entity.DriverLicenceInfo;
import lombok.Data;

/**

 * @authorLiZhaoTeng
 * @date 2018-08-09 10:36
 **/
@Data
public class DriverBaseInfoView {

    private DriverBaseInfo driverBaseInfo;
    private DriverInfo driverInfo;
    private DriverLicenceInfo driverLicenceInfo;


}
