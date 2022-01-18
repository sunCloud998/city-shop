package com.yidian.shop.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 商品状态变更请求参数
 *
 * @function:
 * @description: ProductChangeStatusParam.java
 * @date: 2021/08/22
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class ProductChangeStatusParam {

    /**
     * 商品id
     */
    @NotNull(message = "商品ID不能为空")
    private Long id;

    /**
     * 商品状态，1-待上架，2-上架，3-下架
     */
    @NotNull(message = "商品状态不能为空")
    private Integer status;

}
