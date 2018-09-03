package com.lkmotion.yesincar.request;

        import lombok.Data;

/**
 * @author lizhaoteng
 **/
@Data
public class UpdateDriverAddressRequest {

    private Integer id;
    private String phoneNumber;
    private String addressLongitude;
    private String addressLatitude;
    private String address;
}
