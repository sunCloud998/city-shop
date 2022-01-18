package com.yidian.shop.service.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Maps;
import com.yidian.shop.bo.DistanceCalculateBo;
import com.yidian.shop.config.yml.DeliveryConfig;
import com.yidian.shop.param.DistanceCalculateParam;
import com.yidian.shop.service.GaoDeDeliveryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @function:
 * @description: GaoDeDeliveryServiceImpl.java
 * @date: 2021/09/23
 * @author: sunfayun
 * @version: 1.0
 */
@Service
@Slf4j
public class GaoDeDeliveryServiceImpl implements GaoDeDeliveryService {

    @Resource
    private DeliveryConfig deliveryConfig;

    @Override
    public Integer calculateDistance(DistanceCalculateParam param) {
        if (StringUtils.isBlank(param.getStartLon()) || StringUtils.isBlank(param.getStartLat()) || StringUtils.isBlank(param.getEndLon()) || StringUtils.isBlank(param.getEndLat())) {
            return Integer.MAX_VALUE;
        }
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("origins", param.getStartLon() + "," + param.getStartLat());
        paramMap.put("destination", param.getEndLon() + "," + param.getEndLat());
        paramMap.put("key", deliveryConfig.getGaoDeKey());
        String result = HttpUtil.get(deliveryConfig.getDistanceUrl(), paramMap);
        if (StringUtils.isBlank(result)) {
            return Integer.MAX_VALUE;
        }
        DistanceCalculateBo distanceCalculateBo = JSONUtil.toBean(result, DistanceCalculateBo.class);
        if (distanceCalculateBo == null) {
            return Integer.MAX_VALUE;
        }
        if (CollectionUtils.isEmpty(distanceCalculateBo.getResults()) || distanceCalculateBo.getResults().get(0) == null) {
            return Integer.MAX_VALUE;
        }
        return Integer.valueOf(distanceCalculateBo.getResults().get(0).getDistance());
    }

}
