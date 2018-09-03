package com.lkmotion.yesincar.dto.rated;

import lombok.Data;

import java.util.Date;

/**
 * 乘客评价信息DTO
 *
 * @author ZhuBin
 * @date 2018/9/1
 */
@Data
public class RatedPassengerDto {

    /**
     * 订单编号
     */
    private String orderNumber;

    /**
     * 评价时间
     */
    private Date updateTime;

    /**
     * 服务满意度
     */
    private Integer grade;
}
