package com.lkmotion.yesincar.request;

import lombok.Data;

/**
 * @author lizhaoteng
 **/
@Data
public class CreateTokenRequest {
    private int type;
    private String phoneNum;
    private int id;
}
