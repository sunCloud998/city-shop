package com.yidian.shop.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 商品品牌返回对象
 *
 * @function:
 * @description: ProductBrandVo.java
 * @date: 2021/08/16
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class ProductBrandVo {

    /**
     * 商品品牌ID
     */
    private Long id;

    /**
     * 品牌名称
     */
    private String name;

    /**
     * 品牌描述
     */
    private String description;

    /**
     * LOGO地址
     */
    private String logo;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
