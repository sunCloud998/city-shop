package com.yidian.shop.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品基础信息返回结果
 *
 * @function:
 * @description: ProductInfoVo.java
 * @date: 2021/08/22
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class ProductInfoVo {

    private Long id;

    /**
     * 商品编号
     */
    private String barCode;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品所属类目ID
     */
    private Integer categoryId;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 品牌ID
     */
    private Integer brandId;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 商家ID
     */
    private Long merchantId;

    /**
     * 商家名称
     */
    private String merchantName;

    /**
     * 商品关键字，采用逗号间隔
     */
    private String keywords;

    /**
     * 商品状态，1-待上架，2-上架，3-下架
     */
    private Integer status;

    /**
     * 排序值
     */
    private Integer sortOrder;

    /**
     * 是否新品首发
     */
    private Integer isNew;

    /**
     * 是否人气推荐
     */
    private Integer isHot;

    /**
     * 参考价格
     */
    private BigDecimal advisePrice;

    /**
     * 成本价格
     */
    private BigDecimal costPrice;

    /**
     * 售卖价格
     */
    private BigDecimal salePrice;

    /**
     * 上架时间
     */
    private LocalDateTime upTime;

    /**
     * 销售总量
     */
    private Integer saleCount;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


}
