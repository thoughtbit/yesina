package com.lkmotion.yesincar.task.operate;

import com.lkmotion.yesincar.dto.operate.OperateDto;
import com.lkmotion.yesincar.mapper.OperateMapper;
import com.lkmotion.yesincar.task.AbstractSupervisionTask;
import com.yipin.data.upload.proto.OTIpcDef;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 * 经营出发
 *
 * @author ZhuBin
 * @date 2018/8/30
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class OperateDepartTask extends AbstractSupervisionTask {

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
        OperateDto dto = operateMapper.selectDeparture(id);

        if (ObjectUtils.nullSafeEquals(dto.getStatus(), 5)) {
            ipcType = OTIpcDef.IpcType.operateDepart;
            messageMap.put("OrderId", dto.getOrderNumber());
            messageMap.put("LicenseId", dto.getDrivingLicenceNumber());
            messageMap.put("FareType", dto.getRuleId());
            messageMap.put("VehicleNo", dto.getPlateNumber());
            messageMap.put("Encrypt", 1);
            messageMap.put("DepLongitude", toCoordinates(dto.getReceivePassengerLongitude()));
            messageMap.put("DepLatitude", toCoordinates(dto.getReceivePassengerLatitude()));
            messageMap.put("DepTime", formatDateTime(dto.getReceivePassengerTime(), DateTimePatternEnum.DateTime));
            return true;
        }
        return false;
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
