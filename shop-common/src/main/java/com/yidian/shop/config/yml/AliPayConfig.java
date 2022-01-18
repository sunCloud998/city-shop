package com.yidian.shop.config.yml;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @function:
 * @description: AliPayConfig.java
 * @date: 2021/09/16
 * @author: sunfayun
 * @version: 1.0
 */
@Data
@Component
@Configuration
public class AliPayConfig {

    @Value("${pay.ali.appId}")
    private String appId;
    @Value("${pay.ali.privateKey}")
    private String privateKey;
    @Value("${pay.ali.appCertPath}")
    private String appCertPath;
    @Value("${pay.ali.aliPayCertPath}")
    private String aliPayCertPath;
    @Value("${pay.ali.aliPayRootCertPath}")
    private String aliPayRootCertPath;
    @Value("${pay.ali.serverUrl}")
    private String serverUrl;
    @Value("${pay.ali.domain}")
    private String domain;

}
