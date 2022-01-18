package com.yidian.shop.config.yml;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @function:
 * @description: DeliveryConfig.java
 * @date: 2021/09/23
 * @author: sunfayun
 * @version: 1.0
 */
@Data
@Component
@Configuration
public class DeliveryConfig {

    @Value("${delivery.gaoDe.key}")
    private String gaoDeKey;

    @Value("${delivery.gaoDe.distanceUrl}")
    private String distanceUrl;

}
