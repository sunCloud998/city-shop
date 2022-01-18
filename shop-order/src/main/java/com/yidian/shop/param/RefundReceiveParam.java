package com.yidian.shop.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 退回商品收货请求参数
 *
 * @function:
 * @description: RefundReceiveParam.java
 * @date: 2021/09/09
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class RefundReceiveParam {

    /**
     * 退款订单ID
     */
    @NotNull(message = "退款订单号不能为空")
    private Long refundId;

    /**
     * 操作类型，1：同意退款(默认)，2：拒绝退款
     */
    private Integer optType;

    /**
     * 备注
     */
    private String remark;

}
