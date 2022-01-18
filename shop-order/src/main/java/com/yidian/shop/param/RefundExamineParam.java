package com.yidian.shop.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @function: 退款审核请求参数
 * @description: RefundExamineParam.java
 * @date: 2021/09/08
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class RefundExamineParam {

    /**
     * 退款记录ID
     */
    @NotNull(message = "退款记录ID不能为空")
    private Long refundId;

    /**
     * 审核结果，1：通过，2：拒绝
     */
    @NotNull(message = "审核结果")
    private Integer examineType;

    /**
     * 备注
     */
    private String remark;

}
