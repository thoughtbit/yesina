package com.lkmotion.yesincar.task.rated;

import com.lkmotion.yesincar.dto.rated.RatedDriverDto;
import com.lkmotion.yesincar.mapper.RatedMapper;
import com.lkmotion.yesincar.task.AbstractSupervisionTask;
import com.yipin.data.upload.proto.OTIpcDef;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 驾驶员信誉信息控制器
 *
 * @author ZhuBin
 * @date 2018/9/1
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RatedDriverTask extends AbstractSupervisionTask {

    @NonNull
    private RatedMapper ratedMapper;

    /**
     * 监听到插入操作
     *
     * @param id 主键
     * @return 是否为合法上报数据
     */
    @Override
    public boolean insert(Integer id) {
        return generate(id);
    }

    /**
     * 监听到更新操作
     *
     * @param id 主键
     * @return 是否为合法上报数据
     */
    @Override
    public boolean update(Integer id) {
        return generate(id);
    }

    /**
     * 监听到删除操作
     *
     * @param id 主键
     * @return 是否为合法上报数据
     */
    @Override
    public boolean delete(Integer id) {
        return generate(id);
    }

    /**
     * 检索数据
     *
     * @param id 主键
     * @return 是否为合法上报数据
     */
    private boolean generate(Integer id) {
        if (id == null) {
            log.error("{}：id为空", getClass().getName());
            return false;
        }

        RatedDriverDto dto = ratedMapper.selectRatedDriver(id);

        if (dto == null) {
            log.error("{}：id={}为空", RatedDriverDto.class.getName(), id);
            return false;
        }

        ipcType = OTIpcDef.IpcType.ratedDriver;
        messageMap.put("LicenseId", dto.getDrivingLicenceNumber());
        messageMap.put("Level", dto.getGrade());
        messageMap.put("TestDate", formatDateTime(dto.getTestDate(), DateTimePatternEnum.Date));
        messageMap.put("TestDepartment", dto.getTestDepartment());

        return true;
    }
}
