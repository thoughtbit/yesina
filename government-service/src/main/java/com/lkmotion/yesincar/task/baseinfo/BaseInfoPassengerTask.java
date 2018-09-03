package com.lkmotion.yesincar.task.baseinfo;

import com.lkmotion.yesincar.dto.baseinfo.BaseInfoPassengerDto;
import com.lkmotion.yesincar.mapper.BaseInfoMapper;
import com.lkmotion.yesincar.task.AbstractSupervisionTask;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author lizhaoteng
 **/
@Component
@RequiredArgsConstructor
@Slf4j
public class BaseInfoPassengerTask extends AbstractSupervisionTask {
    @NonNull
    private BaseInfoMapper passengerInfoMapper;

    /**
     * 监听到插入操作
     *
     * @param id 主键
     * @return 是否为合法上报数据
     */
    @Override
    public boolean insert(Integer id) {
        BaseInfoPassengerDto passengerInfo = passengerInfoMapper.getBaseInfoPassenger(id);

        messageMap.put("CompanyId", "");
        messageMap.put("RegisterDate", trimDate(passengerInfo.getRegisterDate()));
        messageMap.put("PassengerPhone", passengerInfo.getPassengerPhone());
        messageMap.put("Status", 0);
        messageMap.put("Flag", 1);
        messageMap.put("UpdateTime", now());

        return true;
    }

    /**
     * 监听到更新操作
     *
     * @param id 主键
     * @return 是否为合法上报数据
     */
    @Override
    public boolean update(Integer id) {
        BaseInfoPassengerDto passengerInfo = passengerInfoMapper.getBaseInfoPassenger(id);

        messageMap.put("CompanyId", "");
        messageMap.put("RegisterDate", trimDate(passengerInfo.getRegisterDate()));
        messageMap.put("PassengerPhone", passengerInfo.getPassengerPhone());
        messageMap.put("Status", 0);
        messageMap.put("Flag", 2);
        messageMap.put("UpdateTime", now());

        return true;
    }

    /**
     * 监听到删除操作
     *
     * @param id 主键
     * @return 是否为合法上报数据
     */
    @Override
    public boolean delete(Integer id) {
        BaseInfoPassengerDto passengerInfo = passengerInfoMapper.getBaseInfoPassenger(id);

        messageMap.put("CompanyId", "");
        messageMap.put("RegisterDate", trimDate(passengerInfo.getRegisterDate()));
        messageMap.put("PassengerPhone", passengerInfo.getPassengerPhone());
        messageMap.put("Status", 0);
        messageMap.put("Flag", 3);
        messageMap.put("UpdateTime", now());

        return true;
    }

}
