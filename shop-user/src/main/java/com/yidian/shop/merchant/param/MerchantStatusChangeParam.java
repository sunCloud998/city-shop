package com.yidian.shop.merchant.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 供应商状态变更请求参数
 *
 * @function:
 * @description: MerchantStatusChangeParam.java
 * @date: 2021/08/11
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class MerchantStatusChangeParam {

    /**
     * 供应商ID
     */
    @NotNull(message = "供应商ID不能为空")
    private Long merchantId;
    /**
     * 状态，0-待审核；1-审核通过；2-审核拒绝；3-禁用
     */
    @NotNull(message = "操作状态不能为空")
    private Integer status;

    /**
     * 操作备注
     */
    private String remark;

}
