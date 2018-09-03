package com.lkmotion.yesincar.request;

import com.lkmotion.yesincar.dto.phone.PhoneInfoView;
import lombok.Data;

import java.util.List;

/**
 * @author lizhaoteng
 **/
@Data
public class PhoneRequest {
    private Integer idType;
    private List<PhoneInfoView> InfoList;

}
