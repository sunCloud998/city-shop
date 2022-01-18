package com.yidian.shop.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 支付请求参数
 *
 * @function:
 * @description: PayParam.java
 * @date: 2021/09/18
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class PayParam {

    /**
     * 订单号
     */
    @NotBlank(message = "订单号不能为空")
    private String orderNo;

    /**
     * 支付方式：0->未支付；1->微信；2->支付宝;3->paypal
     */
    @NotNull(message = "支付方式不能为空")
    private Integer payType;

}
