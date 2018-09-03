package com.lkmotion.yesincar.dao;

import com.lkmotion.yesincar.entity.OrderRuleMirror;
import com.lkmotion.yesincar.mapper.OrderRuleMirrorMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 订单计费规则镜像操作DAO
 *
 * @author ZhuBin
 * @date 2018/8/14
 */
@Repository
@RequiredArgsConstructor
public class OrderRuleMirrorDao {

    @NonNull
    private OrderRuleMirrorMapper orderRuleMirrorMapper;

    /**
     * 根据订单ID查询计费规则镜像
     *
     * @param orderId 订单ID
     * @return 计费规则镜像
     */
    public OrderRuleMirror selectByOrderId(Integer orderId) {
        return orderRuleMirrorMapper.selectByOrderId(orderId);
    }

}
