package com.yidian.shop.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @function:
 * @description: PayInterceptorConfig.java
 * @date: 2021/09/16
 * @author: sunfayun
 * @version: 1.0
 */
@Configuration
public class PayInterceptorConfig implements WebMvcConfigurer {

    @Resource
    private AliPayInterceptor aliPayInterceptor;
    @Resource
    private WxPayInterceptor wxPayInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(aliPayInterceptor).addPathPatterns("/api/aliPay/**");
        registry.addInterceptor(wxPayInterceptor).addPathPatterns("/api/wxPay/**");
    }
}
