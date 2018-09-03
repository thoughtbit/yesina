package com.lkmotion.yesincar.dto.phone;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author lizhaoteng
 **/
@Data
@Accessors(chain = true)
public class PhoneInfoView {
    private int id;

    private String phone;

    private String encrypt;

}
