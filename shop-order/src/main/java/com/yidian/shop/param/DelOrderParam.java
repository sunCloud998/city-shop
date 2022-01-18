package com.yidian.shop.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 删除订单请求参数
 *
 * @function:
 * @description: DelOrderParam.java
 * @date: 2021/09/05
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class DelOrderParam {

    /**
     * 订单编号
     */
    @NotBlank(message = "订单编号不能为空")
    private String orderNo;

}
