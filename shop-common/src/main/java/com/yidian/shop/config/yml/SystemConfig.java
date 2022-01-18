package com.yidian.shop.config.yml;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @function:
 * @description: SystemConfig.java
 * @date: 2021/05/19
 * @author: sunfayun
 * @version: 1.0
 */
@Data
@Component
@Configuration
public class SystemConfig {

    /**
     * 配置文件
     */
    @Value("${spring.profiles.active}")
    private String active;

}
