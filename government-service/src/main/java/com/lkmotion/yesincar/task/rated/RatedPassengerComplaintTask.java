package com.lkmotion.yesincar.task.rated;

import com.lkmotion.yesincar.dto.rated.RatedPassengerComplaintDto;
import com.lkmotion.yesincar.mapper.RatedMapper;
import com.lkmotion.yesincar.task.AbstractSupervisionTask;
import com.yipin.data.upload.proto.OTIpcDef;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 乘客投诉信息控制器
 *
 * @author ZhuBin
 * @date 2018/9/1
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RatedPassengerComplaintTask extends AbstractSupervisionTask {

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

        RatedPassengerComplaintDto dto = ratedMapper.selectRatedPassengerComplaint(id);

        if (dto == null) {
            log.error("{}：id={}为空", RatedPassengerComplaintDto.class.getName(), id);
            return false;
        }

        ipcType = OTIpcDef.IpcType.ratedPassengerComplaint;
        messageMap.put("OrderId", dto.getOrderNumber());
        messageMap.put("ComplaintTime", formatDateTime(dto.getUpdateTime(), DateTimePatternEnum.DateTime));
        messageMap.put("Detail", dto.getContent());

        return true;
    }
}
