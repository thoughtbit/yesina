package com.lkmotion.yesincar.mapper;

import com.lkmotion.yesincar.dto.rated.RatedDriverDto;
import com.lkmotion.yesincar.dto.rated.RatedPassengerComplaintDto;
import com.lkmotion.yesincar.dto.rated.RatedPassengerDto;

import java.util.List;

/**
 * 评价信息Mapper
 *
 * @author ZhuBin
 * @date 2018/9/1
 */
public interface RatedMapper {

    /**
     * 获取乘客评价信息
     *
     * @param id 主键
     * @return 乘客评价信息DTO
     */
    RatedPassengerDto selectRatedPassenger(Integer id);

    /**
     * 获取乘客投诉信息
     *
     * @param id 主键
     * @return 乘客投诉信息DTO
     */
    RatedPassengerComplaintDto selectRatedPassengerComplaint(Integer id);

    /**
     * 获取驾驶员信誉信息
     *
     * @return 驾驶员信誉信息DTO
     */
    List<RatedDriverDto> selectRatedDrivers();

    /**
     * 获取驾驶员信誉信息
     *
     * @return 驾驶员信誉信息DTO
     */
    RatedDriverDto selectRatedDriver(Integer id);
}
