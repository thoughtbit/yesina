package com.lkmotion.yesincar.dto.map;

import lombok.Data;

/**
 * 距离测量结果
 *
 * @author chaopengfei
 */
@Data
public class Route {

    /**
     * 行驶距离（米）
     */
    private Double distance;

    /**
     * 行驶时间（秒）
     */
    private Double duration;
}
