package com.yidian.shop.vo;

import lombok.Data;

import java.util.List;

/**
 * 首页返回结果
 *
 * @function:
 * @description: ProductIndexVo.java
 * @date: 2021/08/23
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class ProductIndexVo {

    /**
     * 人气推荐商品列表
     */
    private List<ProductInfoVo> hotProductList;

    /**
     * 新品商品列表
     */
    private List<ProductInfoVo> newProductList;

    /**
     * 默认商品列表
     */
    private List<ProductInfoVo> defaultProductList;

}
