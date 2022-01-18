package com.yidian.shop.service;

import com.yidian.shop.param.DistanceCalculateParam;

/**
 * @function:
 * @description: GaoDeDeliveryService.java
 * @date: 2021/09/23
 * @author: sunfayun
 * @version: 1.0
 */
public interface GaoDeDeliveryService {

    Integer calculateDistance(DistanceCalculateParam param);

}
