package com.yidian.shop.controller;

import com.alipay.api.AlipayApiException;
import com.ijpay.alipay.AliPayApiConfig;

/**
 * @function:
 * @description: AbstractAliPayApiController.java
 * @date: 2021/09/16
 * @author: sunfayun
 * @version: 1.0
 */
public abstract class AbstractAliPayApiController {

    /**
     * 获取支付宝配置
     *
     * @return {@link AliPayApiConfig} 支付宝配置
     * @throws AlipayApiException 支付宝 Api 异常
     */
    public abstract AliPayApiConfig getApiConfig() throws AlipayApiException;

}
