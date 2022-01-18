package com.yidian.shop.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 添加购物车请求参数
 *
 * @function:
 * @description: AddCartInfoParam.java
 * @date: 2021/08/27
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class AddCartInfoParam {

    /**
     * 商品ID
     */
    @NotNull(message = "商品ID不能为空")
    private Long productId;

    /**
     * 商品sku id
     */
    @NotNull(message = "商品sku id不能为空")
    private Long productSkuId;

    /**
     * 购买数量
     */
    @NotNull(message = "购买数量不能为空")
    private Integer number;

}
