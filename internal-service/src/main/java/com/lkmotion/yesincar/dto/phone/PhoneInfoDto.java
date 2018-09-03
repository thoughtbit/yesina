package com.lkmotion.yesincar.dto.phone;

import lombok.Data;

import java.util.List;

/**
 * 电话号码加密解密DTO
 *
 * @author ZhuBin
 * @date 2018/8/29
 */
@Data
public class PhoneInfoDto {
    private Integer idType;
    private List<PhoneInfoView> infoList;
}
