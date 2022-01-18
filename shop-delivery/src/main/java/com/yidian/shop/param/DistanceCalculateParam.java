package com.yidian.shop.param;

import lombok.Data;

/**
 * @function:
 * @description: DistanceCalculateParam.java
 * @date: 2021/09/23
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class DistanceCalculateParam {

    /**
     * 起点经度
     */
    private String startLon;

    /**
     * 起点纬度
     */
    private String startLat;

    /**
     * 终点经度
     */
    private String endLon;

    /**
     * 终点纬度
     */
    private String endLat;

    /**
     * 路径计算方式，0：直线距离，1：驾车导航距离
     */
    private Integer type;

}
