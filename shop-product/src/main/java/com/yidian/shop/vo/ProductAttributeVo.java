package com.yidian.shop.vo;

import lombok.Data;

/**
 * 商品参数返回结果
 */
@Data
public class ProductAttributeVo {

    /**
     * 商品参数ID
     */
    private Long id;

    /**
     * 商品表的商品ID
     */
    private Long productId;

    /**
     * 商品参数名称
     */
    private String attribute;

    /**
     * 商品参数值
     */
    private String value;
}