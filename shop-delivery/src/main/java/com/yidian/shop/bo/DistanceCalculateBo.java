package com.yidian.shop.bo;

import lombok.Data;

import java.util.List;

/**
 * @function:
 * @description: DistanceCalculateBo.java
 * @date: 2021/09/23
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class DistanceCalculateBo {

    private String status;
    private String info;
    private String infocode;
    private String count;
    private List<DistanceInfo> results;

    @Data
    public static class DistanceInfo {
        private String origin_id;
        private String dest_id;
        /**
         * 路径距离，单位：米
         */
        private String distance;
        private String duration;
    }

}
