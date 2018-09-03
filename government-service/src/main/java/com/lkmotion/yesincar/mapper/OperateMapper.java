package com.lkmotion.yesincar.mapper;

import com.lkmotion.yesincar.dto.operate.OperateDto;
import com.lkmotion.yesincar.dto.operate.OperatePayDto;

/**
 * 经营Mapper
 *
 * @author ZhuBin
 * @date 2018/8/30
 */
public interface OperateMapper {

    /**
     * 经营上线
     *
     * @param id
     * @return
     */
    OperateDto selectOnline(Integer id);

    /**
     * 经营下线
     *
     * @param id
     * @return
     */
    OperateDto selectOffline(Integer id);

    /**
     * 经营出发
     *
     * @param id 订单主键
     * @return 经营DTO
     */
    OperateDto selectDeparture(Integer id);

    /**
     * 经营到达
     *
     * @param id 订单主键
     * @return 经营DTO
     */
    OperateDto selectArrival(Integer id);

    OperatePayDto selectOperatorPay(Integer id);

}
