package com.lkmotion.yesincar.dao;

import com.lkmotion.yesincar.entity.ChargeRule;
import com.lkmotion.yesincar.entity.ChargeRuleDetail;
import com.lkmotion.yesincar.mapper.ChargeRuleDetailMapper;
import com.lkmotion.yesincar.mapper.ChargeRuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 规则操作
 * @author LiHeng
 **/
@Repository
@Service
public class ChargeRuleDao {

    @Autowired
    private ChargeRuleMapper chargeRuleMapper;
    @Autowired
    private ChargeRuleDetailMapper chargeRuleDetailMapper;

    public List<ChargeRule> selectByPrimaryKey(Map<String, Object> map){
        return chargeRuleMapper.selectByPrimaryKey(map);
    }

    public List<ChargeRuleDetail> chargeRuleDetailList (Integer ruleId){
        return chargeRuleDetailMapper.selectByPrimaryKey(ruleId);
    }
}
