package com.yidian.shop.vo;

import lombok.Data;

/**
 * 商品规格返回结果
 *
 * @function:
 * @description: ProductSpecVo.java
 * @date: 2021/08/22
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class ProductSpecVo {

    /**
     * 商品规格ID
     */
    private Long id;

    /**
     * 商品表的商品ID
     */
    private Long productId;

    /**
     * 商品规格名称
     */
    private String specName;

    /**
     * 商品规格值
     */
    private String specValue;

    /**
     * 商品规格图片
     */
    private String imageUrl;

}
