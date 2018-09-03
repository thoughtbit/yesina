package com.lkmotion.yesincar.service;

import com.lkmotion.yesincar.entity.PassengerWallet;
import com.lkmotion.yesincar.entity.PassengerWalletRecord;

/**
 * @author chaopengfei
 * @date 2018/8/21
 */
public interface PassengerWalletService {

    /**
     * 用户的余额变更
     * @param yid
     * @param capital
     * @param giveFee
     * @param changeStatus -1：减，1：加
     * @return
     */
    int alterPassengerWalletPrice(Integer yid,Double capital,Double giveFee,int changeStatus);

    /**
     * 生成钱包记录
     * @param yid
     * @param capital
     * @param giveFee
     * @param payType
     * @param tradeType
     * @param description
     * @param orderId
     * @return
     */
    PassengerWalletRecord createWalletRecord(Integer yid , Double capital, Double giveFee,
                                             Integer payType, Integer tradeType , String description ,
                                             Integer orderId , Integer payStatus);

    /**
     * 支付完成处理逻辑
     * @param rechargeType
     * @param rechargeId
     * @return
     */
    int handleCallBack(int rechargeType,Integer rechargeId);

    /**
     * 初始化乘客钱包
     * @param passengerInfoId
     * @param capital
     * @param giveFee
     * @return
     */
    PassengerWallet initPassengerWallet(Integer passengerInfoId, Double capital, Double giveFee);
}
