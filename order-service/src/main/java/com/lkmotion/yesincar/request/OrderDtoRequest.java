package com.lkmotion.yesincar.request;

import com.lkmotion.yesincar.entity.Order;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 功能描述
 *
 * @author liheng
 * @date 2018/8/27
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class OrderDtoRequest extends Order {
    private Integer orderId;

}
