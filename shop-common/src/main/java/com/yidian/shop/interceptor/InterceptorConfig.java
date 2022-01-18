package com.yidian.shop.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @function:
 * @description: InterceptorConfig.java
 * @date: 2021/04/25
 * @author: sunfayun
 * @version: 1.0
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Resource
    private AuthenticationAdminInterceptor authenticationAdminInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(authenticationAdminInterceptor)
                .addPathPatterns("/api/**");

    }
}
