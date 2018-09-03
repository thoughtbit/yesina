package com.lkmotion.yesincar.service.impl;

import com.lkmotion.yesincar.constant.*;
import com.lkmotion.yesincar.constatnt.BusinessInterfaceStatus;
import com.lkmotion.yesincar.dao.PassengerWalletDao;
import com.lkmotion.yesincar.dao.PassengerWalletFreezeRecordDao;
import com.lkmotion.yesincar.dto.RemainPrice;
import com.lkmotion.yesincar.dto.ResponseResult;
import com.lkmotion.yesincar.entity.PassengerWallet;
import com.lkmotion.yesincar.entity.PassengerWalletFreezeRecord;
import com.lkmotion.yesincar.service.FreezeService;
import com.lkmotion.yesincar.service.PassengerWalletService;
import com.lkmotion.yesincar.util.BigDecimalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author chaopengfei
 * @date 2018/8/22
 */
@Service
public class FreezeServiceImpl implements FreezeService {

    @Autowired
    private PassengerWalletFreezeRecordDao passengerWalletFreezeRecordDao;

    @Autowired
    private PassengerWalletDao passengerWalletDao;

    @Autowired
    private PassengerWalletService passengerWalletService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult freeze(Integer yid, Integer orderId, Double price) {
        Date nowTime = new Date();
        PassengerWalletFreezeRecord passengerWalletFreezeRecord = passengerWalletFreezeRecordDao.selectByOrderIdAndYid(orderId,yid);
        if (null == passengerWalletFreezeRecord){
            Double capital = 0d;
            Double giveFee = 0d;
            PassengerWallet passengerWallet = passengerWalletDao.selectByPassengerInfoId(yid);

            if (null == passengerWallet){
                passengerWallet = passengerWalletService.initPassengerWallet(yid,0d,0d);
            } else{
                capital = passengerWallet.getCapital();
                giveFee = passengerWallet.getGiveFee();
            }
            //剩余可冻结金额
            Double remainPrice = BigDecimalUtil.add(capital.toString(),giveFee.toString());
            if(price.compareTo(remainPrice) <= 0){
                Double freezeCapital = 0d;
                Double freezeGiveFee = 0d;
                if (price.compareTo(capital) <= 0){
                    freezeCapital = price;
                }else {
                    freezeCapital = capital;
                    freezeGiveFee = BigDecimalUtil.sub(price.toString(),capital.toString());
                }
                capital = BigDecimalUtil.sub(capital.toString(),freezeCapital.toString());
                giveFee = BigDecimalUtil.sub(giveFee.toString(),freezeGiveFee.toString());
                passengerWallet.setGiveFee(giveFee);
                passengerWallet.setCapital(capital);

                passengerWalletFreezeRecord = new PassengerWalletFreezeRecord();
                passengerWalletFreezeRecord.setCreateTime(nowTime);
                passengerWalletFreezeRecord.setFreezeCapital(freezeCapital);
                passengerWalletFreezeRecord.setFreezeGiveFee(freezeGiveFee);
                passengerWalletFreezeRecord.setOrderId(orderId);
                passengerWalletFreezeRecord.setPassengerInfoId(yid);
                passengerWalletFreezeRecord.setFreezeStatus(FreezeStatusEnum.FREEZE.getCode());

                passengerWalletFreezeRecordDao.insertSelective(passengerWalletFreezeRecord);

                freezeGiveFee = BigDecimalUtil.add(freezeGiveFee.toString(),passengerWallet.getFreezeGiveFee().toString());
                freezeCapital = BigDecimalUtil.add(freezeCapital.toString(),passengerWallet.getFreezeCapital().toString());

                passengerWallet.setFreezeGiveFee(freezeGiveFee);
                passengerWallet.setFreezeCapital(freezeCapital);
                passengerWalletDao.updateByPrimaryKeySelective(passengerWallet);
                return ResponseResult.success(passengerWalletFreezeRecord.getId());
            }else{
                //不够冻结
                return ResponseResult.fail(ResponseStatusEnum.FREEZE_NOT_ENOUGH.getCode(),ResponseStatusEnum.FREEZE_NOT_ENOUGH.getValue(),
                        "");
            }

        }else {
            //已经冻结
            return ResponseResult.fail(ResponseStatusEnum.NOT_ALLOW_RE_FREEZE.getCode(),ResponseStatusEnum.NOT_ALLOW_RE_FREEZE.getValue(),
                    "");
        }
    }

    @Override
    public ResponseResult unFreeze(Integer yid, Integer orderId) {
        //查询冻结记录
        PassengerWalletFreezeRecord passengerWalletFreezeRecord = passengerWalletFreezeRecordDao.selectByOrderIdAndYid(orderId,yid);
        if(null == passengerWalletFreezeRecord){
            return ResponseResult.fail(ResponseStatusEnum.FREEZE_RECORD_EMPTY.getCode(),ResponseStatusEnum.FREEZE_RECORD_EMPTY.getValue(),
                    "");
        }else {
            Double freezeCapital = passengerWalletFreezeRecord.getFreezeCapital();
            Double freezeGiveFee = passengerWalletFreezeRecord.getFreezeGiveFee();

            passengerWalletFreezeRecord.setFreezeStatus(FreezeStatusEnum.UNFREEZE.getCode());
            passengerWalletFreezeRecordDao.updateByPrimaryKeySelective(passengerWalletFreezeRecord);

            PassengerWallet passengerWallet = passengerWalletDao.selectByPassengerInfoId(yid);

            if (null == passengerWallet){
                return ResponseResult.fail(ResponseStatusEnum.PASSENGER_WALLET_EMPTY.getCode(),
                        ResponseStatusEnum.PASSENGER_WALLET_EMPTY.getValue(),
                        "");
            } else{
                Double walletCapital = passengerWallet.getCapital();
                Double walletGiveFee = passengerWallet.getGiveFee();

                walletCapital = BigDecimalUtil.add(walletCapital.toString(),freezeCapital.toString());
                walletGiveFee = BigDecimalUtil.add(walletGiveFee.toString(),freezeGiveFee.toString());
                passengerWallet.setCapital(walletCapital);
                passengerWallet.setGiveFee(walletGiveFee);

                Double walletFreezeCapital = passengerWallet.getFreezeCapital();
                Double walletFreezeGiveFee = passengerWallet.getFreezeGiveFee();
                walletFreezeCapital = BigDecimalUtil.sub(walletFreezeCapital.toString(),freezeCapital.toString());
                walletFreezeGiveFee = BigDecimalUtil.sub(walletFreezeGiveFee.toString(),freezeGiveFee.toString());
                passengerWallet.setFreezeCapital(walletFreezeCapital);
                passengerWallet.setFreezeGiveFee(walletFreezeGiveFee);

                passengerWalletDao.updateByPrimaryKeySelective(passengerWallet);
                return ResponseResult.success("");

            }

        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult pay(Integer yid, Integer orderId, Double price) {
        //解冻
        ResponseResult responseResult = unFreeze(yid,orderId);
        if(responseResult.getCode() != BusinessInterfaceStatus.SUCCESS.getCode()){
            return responseResult;
        }
        //扣款
        PassengerWallet passengerWallet = passengerWalletDao.selectByPassengerInfoId(yid);
        Double remainPrice = 0d;

        if (null == passengerWallet){
            return ResponseResult.fail(ResponseStatusEnum.PASSENGER_WALLET_EMPTY.getCode(),
                    ResponseStatusEnum.PASSENGER_WALLET_EMPTY.getValue(),
                    "");
        } else {
            Double walletCapital = passengerWallet.getCapital();
            Double walletGiveFee = passengerWallet.getGiveFee();
            Double sum = BigDecimalUtil.add(walletCapital.toString(),walletGiveFee.toString());

            Double walletCapitalNew = walletCapital;
            Double walletGiveFeeNew = walletGiveFee;
            if(price.compareTo(walletCapital) <= 0){
                walletCapitalNew = BigDecimalUtil.sub(walletCapitalNew.toString(),price.toString());

            }else if(price.compareTo(sum) <= 0){
                walletCapitalNew = 0d;
                Double temp = BigDecimalUtil.sub(price.toString(),walletCapital.toString());
                walletGiveFeeNew = BigDecimalUtil.sub(walletGiveFee.toString(),temp.toString());
            }else {
                walletCapitalNew = 0d;
                walletGiveFeeNew = 0d;

                remainPrice = BigDecimalUtil.sub(price.toString(),sum.toString());
            }

            Double subCapital = BigDecimalUtil.sub(walletCapital.toString(),walletCapitalNew.toString());
            Double subGiveFee = BigDecimalUtil.sub(walletGiveFee.toString(),walletGiveFeeNew.toString());
            passengerWalletService.alterPassengerWalletPrice(yid,subCapital,subGiveFee,ChangeStatusEnum.SUB.getCode());

            //生成扣款记录
            passengerWalletService.createWalletRecord(yid,subCapital,subGiveFee,PayTypeEnum.BALANCE.getCode(),TradeTypeEnum.CONSUME.getCode(),
                    "消费",orderId,PayEnum.PAID.getCode());

            RemainPrice remainPriceBean = new RemainPrice();
            remainPriceBean.setRemainPrice(remainPrice);
            return ResponseResult.success(remainPriceBean);
        }

    }
}
