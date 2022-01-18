package com.yidian.shop.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品sku返回结果
 *
 * @function:
 * @description: ProductSkuVo.java
 * @date: 2021/08/22
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class ProductSkuVo {

    /**
     * 商品sku ID
     */
    private Long id;

    /**
     * 商品表的商品ID
     */
    private Long productId;

    /**
     * 商品规格值列表，采用JSON数组格式
     */
    private List<String> spec;

    /**
     * 商品货品价格
     */
    private BigDecimal price;

    /**
     * 商品货品数量
     */
    private Integer stock;

    /**
     * 商品货品图片
     */
    private String imageUrl;

}
