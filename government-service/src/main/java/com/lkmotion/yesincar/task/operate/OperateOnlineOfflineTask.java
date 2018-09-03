package com.lkmotion.yesincar.task.operate;

import com.lkmotion.yesincar.dto.operate.OperateDto;
import com.lkmotion.yesincar.mapper.OperateMapper;
import com.lkmotion.yesincar.task.AbstractSupervisionTask;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 * 功能描述
 *
 * @author liheng
 * @date 2018/8/31
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class OperateOnlineOfflineTask extends AbstractSupervisionTask {

    @NonNull
    private OperateMapper operateMapper;

    @Override
    public boolean insert(Integer id) {
        OperateDto dto = operateMapper.selectOnline(id);
        //司机出车
        if (ObjectUtils.nullSafeEquals(dto.getWorkStatus(), 1)) {
            messageMap.put("LicenseId", dto.getDrivingLicenceNumber());
            messageMap.put("VehicleNo", dto.getPlateNumber());
            messageMap.put("LoginTime", formatDateTime(dto.getWorkStart(), DateTimePatternEnum.DateTime));
            messageMap.put("Encrypt", 1);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Integer id) {
        //司机收车
        OperateDto dto = operateMapper.selectOffline(id);
        if (ObjectUtils.nullSafeEquals(dto.getWorkStatus(), 3)) {
            messageMap.put("LicenseId", dto.getDrivingLicenceNumber());
            messageMap.put("VehicleNo", dto.getPlateNumber());
            messageMap.put("LogoutTime", formatDateTime(dto.getWorkEnd(), DateTimePatternEnum.DateTime));
            messageMap.put("Encrypt", 1);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }
}
