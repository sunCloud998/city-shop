package com.yidian.shop.pay;

import com.alipay.api.AlipayApiException;
import com.ijpay.alipay.AliPayApiConfig;
import com.ijpay.alipay.AliPayApiConfigKit;
import com.yidian.shop.config.yml.AliPayConfig;
import com.yidian.shop.controller.AbstractAliPayApiController;
import com.yidian.shop.param.PayParam;
import com.yidian.shop.service.PayInfoService;
import com.yidian.shop.vo.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 支付宝支付接口列表
 *
 * @function:
 * @description: AliPayController.java
 * @date: 2021/09/16
 * @author: sunfayun
 * @version: 1.0
 */
@RestController
@RequestMapping("/api/aliPay")
public class AliPayController extends AbstractAliPayApiController {

    @Resource
    private AliPayConfig aliPayConfig;
    @Resource
    private PayInfoService payInfoService;

    @Override
    public AliPayApiConfig getApiConfig() throws AlipayApiException {
        AliPayApiConfig aliPayApiConfig;
        try {
            aliPayApiConfig = AliPayApiConfigKit.getApiConfig(aliPayConfig.getAppId());
        } catch (Exception e) {
            aliPayApiConfig = AliPayApiConfig.builder()
                    .setAppId(aliPayConfig.getAppId())
                    .setPrivateKey(aliPayConfig.getPrivateKey())
                    .setAppCertPath(aliPayConfig.getAppCertPath())
                    .setAliPayCertPath(aliPayConfig.getAliPayCertPath())
                    .setAliPayRootCertPath(aliPayConfig.getAliPayRootCertPath())
                    .setCharset("UTF-8")
                    .setServiceUrl(aliPayConfig.getServerUrl())
                    .setSignType("RSA2")
                    // 证书模式
                    .buildByCert();

        }
        return aliPayApiConfig;
    }

    /**
     * 支付宝支付
     *
     * @param param 支付请求参数
     * @return
     */
    @PostMapping("/pay")
    public Result<String> aliPay(@RequestBody @Valid PayParam param) {
        return payInfoService.aliPay(param);
    }
}
