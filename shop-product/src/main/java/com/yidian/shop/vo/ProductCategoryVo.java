package com.yidian.shop.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 产品分类返回结果
 *
 * @function:
 * @description: ProductCategoryVo.java
 * @date: 2021/08/13
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class ProductCategoryVo {

    /**
     * 当前分类ID
     */
    private Long id;

    /**
     * 上级分类的编号：0表示一级分类
     */
    private Long parentId;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 分类级别：0->1级；1->2级
     */
    private Integer level;

    /**
     * 排序值
     */
    private Integer sort;

    /**
     * 图标
     */
    private String icon;

    /**
     * 关键字
     */
    private String keywords;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


}
