package com.yidian.shop.controller;

import com.ijpay.wxpay.WxPayApiConfig;

/**
 * @function:
 * @description: AbstractWxPayApiController.java
 * @date: 2021/09/16
 * @author: sunfayun
 * @version: 1.0
 */
public abstract class AbstractWxPayApiController {

    /**
     * 获取微信支付配置
     *
     * @return {@link WxPayApiConfig} 微信支付配置
     */
    public abstract WxPayApiConfig getApiConfig();

}
