package com.yidian.shop.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 取消订单请求参数
 *
 * @function:
 * @description: CancelOrderParam.java
 * @date: 2021/08/31
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class CancelOrderParam {

    /**
     * 订单编号
     */
    @NotBlank(message = "订单编号不能为空")
    private String orderNo;

}
