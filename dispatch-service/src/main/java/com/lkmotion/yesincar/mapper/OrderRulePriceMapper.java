package com.lkmotion.yesincar.mapper;

import com.lkmotion.yesincar.entity.OrderRulePrice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderRulePriceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderRulePrice record);

    int insertSelective(OrderRulePrice record);

    OrderRulePrice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderRulePrice record);

    int updateByPrimaryKey(OrderRulePrice record);

    OrderRulePrice selectByOrderId(@Param("orderId") int orderId);
}
