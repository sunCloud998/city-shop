package com.yidian.shop.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 更新购物车请求参数
 *
 * @function:
 * @description: UpdateCartInfoParam.java
 * @date: 2021/08/27
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class UpdateCartInfoParam {

    /**
     * 购物车ID
     */
    @NotNull(message = "购物车ID不能为空")
    private Long cartId;

    /**
     * 购买数量
     */
    @NotNull(message = "购买数量不能为空")
    private Integer number;

    /**
     * 商品是否选中状态，1：选中
     */
    private Integer checked;

}
