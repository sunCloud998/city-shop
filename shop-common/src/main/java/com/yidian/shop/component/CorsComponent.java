package com.yidian.shop.component;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @function:
 * @description: CorsComponent.java
 * @date: 2021/10/12
 * @author: sunfayun
 * @version: 1.0
 */
@Configuration
public class CorsComponent implements WebMvcConfigurer {

    /**
     * 跨域支持
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .allowedHeaders("*")
                .maxAge(3600 * 24);
    }

}
