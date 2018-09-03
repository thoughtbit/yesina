package com.lkmotion.yesincar.task.operate;

import com.lkmotion.yesincar.dto.operate.OperateDto;
import com.lkmotion.yesincar.mapper.OperateMapper;
import com.lkmotion.yesincar.task.AbstractSupervisionTask;
import com.yipin.data.upload.proto.OTIpcDef;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 经营到达
 *
 * @author ZhuBin
 * @date 2018/8/30
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class OperateArriveTask extends AbstractSupervisionTask {

    @NonNull
    private OperateMapper operateMapper;

    /**
     * 监听到插入操作
     *
     * @param id 主键
     * @return 是否为合法上报数据
     */
    @Override
    public boolean insert(Integer id) {
        return false;
    }

    /**
     * 监听到更新操作
     *
     * @param id 主键
     * @return 是否为合法上报数据
     */
    @Override
    public boolean update(Integer id) {
        OperateDto dto = operateMapper.selectArrival(id);

        //经营到达
        ipcType = OTIpcDef.IpcType.operateArrive;
        messageMap.put("OrderId", dto.getOrderNumber());
        messageMap.put("DestLongitude", toCoordinates(dto.getPassengerGetoffLongitude()));
        messageMap.put("DestLatitude", toCoordinates(dto.getPassengerGetoffLatitude()));
        messageMap.put("Encrypt", 1);
        messageMap.put("DestTime", formatDateTime(dto.getPassengerGetoffTime(), DateTimePatternEnum.DateTime));
        messageMap.put("DriveMile", dto.getTotalDistance());
        messageMap.put("DriveTime", dto.getTotalTime() * 60);
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
        return false;
    }
}
