package com.yidian.shop.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 退货商品寄回请求参数
 *
 * @function:
 * @description: RefundBackParam.java
 * @date: 2021/09/08
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class RefundBackParam {

    /**
     * 退款单号
     */
    @NotNull(message = "退款单号不能为空")
    private Long refundId;

    /**
     * 退款物流公司
     */
    @NotBlank(message = "退货物流公司不能为空")
    private String refundDeliveryCompany;

    /**
     * 退款物流单号
     */
    @NotBlank(message = "退货物流单号不能为空")
    private String refundDeliveryNo;

}
