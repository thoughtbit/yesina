package com.lkmotion.yesincar.mapper;

import com.lkmotion.yesincar.entity.OrderRuleMirror;
import org.springframework.stereotype.Service;

/**
 * @author Li----Heng
 */
@Service
public interface OrderRuleMirrorMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(OrderRuleMirror record);

    int insertSelective(OrderRuleMirror record);

    OrderRuleMirror selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderRuleMirror record);

    int updateByPrimaryKeyWithBLOBs(OrderRuleMirror record);

    int updateByPrimaryKey(OrderRuleMirror record);

}
