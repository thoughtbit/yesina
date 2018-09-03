package com.lkmotion.yesincar.service.impl;

import com.lkmotion.yesincar.constant.*;
import com.lkmotion.yesincar.dao.PassengerWalletDao;
import com.lkmotion.yesincar.dao.PassengerWalletRecordDao;
import com.lkmotion.yesincar.entity.PassengerWallet;
import com.lkmotion.yesincar.entity.PassengerWalletRecord;
import com.lkmotion.yesincar.service.PassengerWalletService;
import com.lkmotion.yesincar.util.BigDecimalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @author chaopengfei
 * @date 2018/8/21
 */
@Repository
public class PassengerWalletServiceImpl implements PassengerWalletService {

    @Autowired
    private PassengerWalletDao passengerWalletDao;

    @Autowired
    private PassengerWalletRecordDao walletRecordDao;

    @Override
    public PassengerWalletRecord createWalletRecord(Integer yid , Double capital, Double giveFee,
                                                    Integer payType, Integer tradeType , String description ,
                                                    Integer orderId,Integer payStatus){
        Date nowDate = new Date();
        PassengerWalletRecord passengerWalletRecord = new PassengerWalletRecord();
        passengerWalletRecord.setPassengerInfoId(yid);
        passengerWalletRecord.setPayTime(nowDate);
        passengerWalletRecord.setPayCapital(capital);
        passengerWalletRecord.setPayGiveFee(giveFee);

        Double sumMoney = BigDecimalUtil.add(capital.toString(),giveFee.toString());
        Double discount = BigDecimalUtil.div(giveFee.toString(),sumMoney.toString(),2);
        passengerWalletRecord.setRechargeDiscount(discount);
        passengerWalletRecord.setPayType(payType);
        passengerWalletRecord.setCreateTime(nowDate);
        passengerWalletRecord.setPayStatus(payStatus);
        passengerWalletRecord.setTradeType(tradeType);
        passengerWalletRecord.setDescription(description);
        passengerWalletRecord.setOrderId(orderId);

        walletRecordDao.insertSelective(passengerWalletRecord);
        return passengerWalletRecord;

    }

    @Override
    public int handleCallBack(int rechargeType, Integer rechargeId) {

        // 查询充值记录
        PassengerWalletRecord passengerWalletRecord = walletRecordDao.selectByPrimaryKey(rechargeId);
        Double capital = passengerWalletRecord.getPayCapital();
        Double giveFee = passengerWalletRecord.getPayGiveFee();

        Integer passengerInfoId = passengerWalletRecord.getPassengerInfoId();
        PassengerWallet passengerWallet = passengerWalletDao.selectByPassengerInfoId(passengerInfoId);
        //更新乘客钱包
        if (null == passengerWallet){
            passengerWallet = initPassengerWallet(passengerInfoId,capital,giveFee);
        } else {
            Double capitalDB = passengerWallet.getCapital();
            Double giveFeeDB = passengerWallet.getGiveFee();

            Double capitalNew = BigDecimalUtil.add(capital.toString(),capitalDB.toString());
            Double giveFeeNew = BigDecimalUtil.add(giveFee.toString(),giveFeeDB.toString());
            passengerWallet.setCapital(capitalNew);
            passengerWallet.setGiveFee(giveFeeNew);

        }
        //更改流水记录
        passengerWalletRecord.setPayStatus(PayEnum.PAID.getCode());
        walletRecordDao.updateByPrimaryKeySelective(passengerWalletRecord);
        //如果是充值，并消费，产生消费记录
        if(rechargeType == RechargeTypeEnum.CONSUME.getCode()){

            PassengerWalletRecord consume = createWalletRecord(passengerInfoId , capital, giveFee,
                    PayTypeEnum.BALANCE.getCode(), TradeTypeEnum.CONSUME.getCode() ,"订单消费" ,
                    passengerWalletRecord.getOrderId(),PayEnum.PAID.getCode());
        }else {
            passengerWalletDao.updateByPrimaryKeySelective(passengerWallet);
        }

        return 0;
    }

    @Override
    public PassengerWallet initPassengerWallet(Integer passengerInfoId,Double capital,Double giveFee){
        Date nowTime = new Date();
        PassengerWallet passengerWallet = new PassengerWallet();
        passengerWallet.setPassengerInfoId(passengerInfoId);
        passengerWallet.setCapital(capital);
        passengerWallet.setGiveFee(giveFee);
        passengerWallet.setFreezeCapital(0d);
        passengerWallet.setFreezeGiveFee(0d);
        passengerWallet.setCreateTime(nowTime);
        passengerWallet.setUpdateTime(nowTime);
        passengerWalletDao.insertSelective(passengerWallet);
        return passengerWallet;
    }

    @Override
    public int alterPassengerWalletPrice(Integer yid, Double capital, Double giveFee , int changeStatus) {

        PassengerWallet passengerWallet = passengerWalletDao.selectByPassengerInfoId(yid);
        Double capitalOld = passengerWallet.getCapital();
        Double giveFeeOld = passengerWallet.getGiveFee();

        Double capitalNew  ;
        Double giveFeeNew ;
        if (changeStatus == ChangeStatusEnum.ADD.getCode()){
            capitalNew = BigDecimalUtil.add(capitalOld.toString(),capital.toString());
            giveFeeNew = BigDecimalUtil.add(giveFeeOld.toString(),giveFee.toString());
        }else if(changeStatus == ChangeStatusEnum.SUB.getCode()){
            capitalNew = BigDecimalUtil.sub(capitalOld.toString(),capital.toString());
            giveFeeNew = BigDecimalUtil.sub(giveFeeOld.toString(),giveFee.toString());
        }else {
            return 0;
        }
        return passengerWalletDao.updateBalanceBypassengerInfoId(yid,capitalNew,giveFeeNew,capitalOld,giveFeeOld);

    }
}
