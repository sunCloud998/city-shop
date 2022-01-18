package com.yidian.shop.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 退款申请请求参数
 *
 * @function:
 * @description: RefundApplyParam.java
 * @date: 2021/09/07
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class RefundApplyParam {

    /**
     * 商家id
     */
    @NotNull(message = "商家ID不能为空")
    private Long merchantId;

    /**
     * 商品id
     */
    @NotNull(message = "商品ID不能为空")
    private Long productId;

    /**
     * 订单编号
     */
    @NotBlank(message = "订单号不能为空")
    private String orderNo;

    /**
     * 退款金额
     */
    private BigDecimal refundAmount;

    /**
     * 退款原因
     */
    @NotBlank(message = "退款原因不能为空")
    private String refundReason;

    /**
     * 描述描述
     */
    private String refundDescription;

    /**
     * 备注
     */
    private String remark;

}
