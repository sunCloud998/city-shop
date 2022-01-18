package com.yidian.shop.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 订单发货请求参数
 *
 * @function:
 * @description: OrderDeliveryParam.java
 * @date: 2021/09/09
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class OrderDeliveryParam {

    /**
     * 订单号
     */
    @NotBlank(message = "订单号不能为空")
    private String orderNo;

    /**
     * 物流公司
     */
    @NotBlank(message = "物流公司不能为空")
    private String deliveryCompany;

    /**
     * 物流单号
     */
    @NotBlank(message = "物流单号不能为空")
    private String deliveryNo;

}
