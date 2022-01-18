package com.yidian.shop.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderProductVo {

    /**
     * 购买的商品记录ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 商品ID
     */
    private Long productId;

    /**
     * 商品sku ID
     */
    private Long productSkuId;

    /**
     * 商家ID
     */
    private Long merchantId;

    /**
     * 商家名称
     */
    private String merchantName;

    /**
     * 商品价格
     */
    private BigDecimal productPrice;

    /**
     * 购买数量
     */
    private Integer buyAmount;

    /**
     * 商品编号
     */
    private String productBarCode;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品规格值列表，采用JSON数组格式
     */
    private String productSpec;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}