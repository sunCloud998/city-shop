package com.yidian.shop.config.yml;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @function:
 * @description: WxPayConfig.java
 * @date: 2021/09/29
 * @author: sunfayun
 * @version: 1.0
 */
@Data
@Component
@Configuration
public class WxPayConfig {

    @Value("${pay.wx.appId}")
    private String appId;
    @Value("${pay.wx.mchId}")
    private String mchId;
    @Value("${pay.wx.partnerKey}")
    private String partnerKey;
    @Value("${pay.wx.certPath}")
    private String certPath;
    @Value("${pay.wx.domain}")
    private String domain;

}
