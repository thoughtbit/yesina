package com.lkmotion.yesincar.dto.baseinfo;

import lombok.Data;

import java.util.Date;

/**
 * @author lizhaoteng
 **/
@Data
public class BaseInfoPassengerDto {
    private String companyId;
    private Date registerDate;
    private String passengerPhone;
}
