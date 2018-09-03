package com.lkmotion.yesincar.dao;

import com.lkmotion.yesincar.entity.PushAccount;
import com.lkmotion.yesincar.mapper.PushAccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author chaopengfei
 */
@Repository
public class PushAccountDao {

    @Autowired
    private PushAccountMapper pushAccountMapper;

    public List<PushAccount> selectByIdentityAndYid(Integer identityStatus , String yid){
        PushAccount pushAccount = new PushAccount();
        pushAccount.setIdentityStatus(identityStatus);
        pushAccount.setYid(yid);
        return pushAccountMapper.selectByIdentityAndYid(pushAccount);
    }
}
