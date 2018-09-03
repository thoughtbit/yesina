package com.lkmotion.yesincar.service.impl;

import com.lkmotion.yesincar.constant.ChangeStatusEnum;
import com.lkmotion.yesincar.constant.PayEnum;
import com.lkmotion.yesincar.constant.PayTypeEnum;
import com.lkmotion.yesincar.constant.TradeTypeEnum;
import com.lkmotion.yesincar.dao.PassengerWalletDao;
import com.lkmotion.yesincar.dao.PassengerWalletRecordDao;
import com.lkmotion.yesincar.dto.RefundPrice;
import com.lkmotion.yesincar.entity.PassengerWalletRecord;
import com.lkmotion.yesincar.service.PassengerWalletService;
import com.lkmotion.yesincar.service.RefundService;
import com.lkmotion.yesincar.util.BigDecimalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author chaopengfei
 * @date 2018/8/21
 */
@Repository
public class RefundServiceImpl implements RefundService {

    @Autowired
    private PassengerWalletRecordDao walletRecordDao;

    @Autowired
    private PassengerWalletDao passengerWalletDao;

    @Autowired
    private PassengerWalletService passengerWalletService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void refund(Integer yid,Integer orderId, Double refundPrice) {

        if(refundPrice == null || refundPrice.compareTo(0d) <= 0){
            //没必要退款
            return;
        }

        //算出退款的本金，和赠费
        RefundPrice returnPrice = getRefundPrice(orderId,refundPrice);

        Double refundCapital = returnPrice.getRefundCapital();
        Double refundGiveFee = returnPrice.getRefundGiveFee();

        if(refundCapital.compareTo(0d)==0 && refundGiveFee.compareTo(0d)==0){
            return;
        }
        //生成退款记录
        PassengerWalletRecord passengerWalletRecord = passengerWalletService.createWalletRecord(yid,refundCapital,refundGiveFee,
                PayTypeEnum.SYSTEM.getCode(),TradeTypeEnum.REFUND.getCode(),
                "退款",orderId,PayEnum.PAID.getCode());

        //回冲
        passengerWalletService.alterPassengerWalletPrice(yid,refundCapital,refundGiveFee,ChangeStatusEnum.ADD.getCode());


    }

    private RefundPrice getRefundPrice(Integer orderId,Double refundPrice){
        Double refundCapital = 0d;
        Double refundGiveFee = 0d;
        RefundPrice refundPriceBean = new RefundPrice(0d,0d);
        List<PassengerWalletRecord> walletRecords = walletRecordDao.selectPaidRecordByOrderId(orderId);
        if (walletRecords.isEmpty()){
            return null;
        } else {
            //计算所有剩余可退的钱
            Double remainGiveFeeAll = 0d;
            Double remainCapitalAll = 0d;
            for (PassengerWalletRecord passengerWalletRecord:walletRecords){
                Double tempCapital = BigDecimalUtil.sub(passengerWalletRecord.getPayCapital().toString(),passengerWalletRecord.getRefundCapital().toString());
                remainCapitalAll = BigDecimalUtil.add(remainCapitalAll.toString(),tempCapital.toString());

                Double tempGiveFee = BigDecimalUtil.sub(passengerWalletRecord.getPayGiveFee().toString(),passengerWalletRecord.getRefundGiveFee().toString());
                remainGiveFeeAll = BigDecimalUtil.add(remainGiveFeeAll.toString(),tempGiveFee.toString());
            }
            Double remainAll = BigDecimalUtil.add(remainCapitalAll.toString(),remainGiveFeeAll.toString());
            if(refundPrice.compareTo(remainAll) > 0){
                return null;
            }
            //此订单有支付记录
            for (PassengerWalletRecord passengerWalletRecord:walletRecords
                    ) {
                //记录付过的钱
                Double payCapital = passengerWalletRecord.getPayCapital();
                Double payGiveFee = passengerWalletRecord.getPayGiveFee();
                Double sum = BigDecimalUtil.add(payCapital.toString(),payGiveFee.toString());
                //记录退过的钱
                Double recordRefundCapital = passengerWalletRecord.getRefundCapital();
                Double recordRefundGiveFee = passengerWalletRecord.getRefundGiveFee();

                //剩余可退
                Double remainCapital = BigDecimalUtil.sub(payCapital.toString(),recordRefundCapital.toString());
                Double remainGiveFee = BigDecimalUtil.sub(payGiveFee.toString(),recordRefundGiveFee.toString());
                Double sumRemain = BigDecimalUtil.add(remainCapital.toString(),remainGiveFee.toString());
                if (sumRemain.compareTo(0d)<=0){
                    continue;
                }
                if (refundPrice.compareTo(remainGiveFee) <= 0){

                    refundGiveFee = BigDecimalUtil.add(refundGiveFee.toString(),refundPrice.toString());
                    //更新已退的赠费
                    Double alreadyRefundGiveFee = BigDecimalUtil.add(recordRefundGiveFee.toString(),refundPrice.toString());
                    passengerWalletRecord.setRefundGiveFee(alreadyRefundGiveFee);
                    walletRecordDao.updateByPrimaryKeySelective(passengerWalletRecord);
                    break;
                }else if (refundPrice.compareTo(sumRemain) <= 0){
                    //可退的赠费 总
                    refundGiveFee = BigDecimalUtil.add(refundGiveFee.toString(),remainGiveFee.toString());
                    //剩余需要退还的本金
                    Double remainTemp = BigDecimalUtil.sub(refundPrice.toString(),remainGiveFee.toString());
                    refundCapital = BigDecimalUtil.add(refundCapital.toString(),remainTemp.toString());
                    //更新已退的赠费
                    Double alreadyRefundGiveFee = BigDecimalUtil.add(recordRefundGiveFee.toString(),remainGiveFee.toString());
                    passengerWalletRecord.setRefundGiveFee(alreadyRefundGiveFee);
                    Double alreadyRefundCapital = BigDecimalUtil.add(recordRefundCapital.toString(),remainTemp.toString());
                    passengerWalletRecord.setRefundCapital(alreadyRefundCapital);
                    walletRecordDao.updateByPrimaryKeySelective(passengerWalletRecord);
                    break;
                }else{
                    //可退的钱
                    refundGiveFee = BigDecimalUtil.add(refundGiveFee.toString(),remainGiveFee.toString());
                    refundCapital = BigDecimalUtil.add(refundCapital.toString(),remainCapital.toString());
                    //更新已退的钱
                    Double alreadyRefundGiveFee = BigDecimalUtil.add(recordRefundGiveFee.toString(),remainGiveFee.toString());
                    passengerWalletRecord.setRefundGiveFee(alreadyRefundGiveFee);
                    Double alreadyRefundCapital = BigDecimalUtil.add(recordRefundCapital.toString(),remainCapital.toString());
                    passengerWalletRecord.setRefundCapital(alreadyRefundCapital);
                    walletRecordDao.updateByPrimaryKeySelective(passengerWalletRecord);

                    Double sumTemp = BigDecimalUtil.add(refundGiveFee.toString(),refundCapital.toString());
                    refundPrice = BigDecimalUtil.sub(refundPrice.toString(),sumTemp.toString());
                    continue;
                }

            }
        }

        refundPriceBean.setRefundCapital(refundCapital);
        refundPriceBean.setRefundGiveFee(refundGiveFee);
        return refundPriceBean;
    }
}
