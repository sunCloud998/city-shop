package com.yidian.shop.merchant.param;

import com.yidian.shop.param.BasePageParam;
import lombok.Data;

/**
 * 供应商列表查询请求参数
 *
 * @function:
 * @description: MerchantListParam.java
 * @date: 2021/08/10
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class MerchantListParam extends BasePageParam {

    /**
     * 手机号
     */
    private String phone;

    /**
     * 供应商名称
     */
    private String merchantName;

    /**
     * 状态 0待审核，1启用，2禁用
     */
    private Integer status;

}
