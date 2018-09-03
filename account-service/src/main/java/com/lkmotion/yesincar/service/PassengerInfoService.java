package com.lkmotion.yesincar.service;

import com.lkmotion.yesincar.entity.PassengerInfo;

/**
 * @author LiZhaoTeng
 **/
public interface PassengerInfoService {

    public PassengerInfo queryPassengerInfoByPhoneNum(String phoneNum);

    public void insertPassengerInfo(PassengerInfo passengerInfo) ;


    public void updatePassengerInfoLoginTime(Integer passengerId);




}
