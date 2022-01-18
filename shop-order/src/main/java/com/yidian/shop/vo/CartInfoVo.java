package com.yidian.shop.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 购物车商品返回对象
 *
 * @function:
 * @description: CartInfoVo.java
 * @date: 2021/08/27
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class CartInfoVo {

    /**
     * 购物车ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 商家ID
     */
    private Long merchantId;

    /**
     * 商品ID
     */
    private Long productId;

    /**
     * 商品编号
     */
    private String barCode;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品sku ID
     */
    private Long productSkuId;

    /**
     * 商品价格
     */
    private BigDecimal price;

    /**
     * 商品数量
     */
    private Integer number;

    /**
     * 商品规格值列表，采用JSON数组格式
     */
    private String spec;

    /**
     * 购物车中商品是否选择状态，1：选中
     */
    private Integer checked;

    /**
     * 商品是否失效，1：失效
     */
    private Integer invalid;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
