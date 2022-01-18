package com.yidian.shop.vo;

import lombok.Data;

import java.util.List;

/**
 * 产品分类列表树返回结果
 *
 * @function:
 * @description: ProductCategoryTreeVo.java
 * @date: 2021/08/15
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class ProductCategoryTreeVo {

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
     * 子分类列表
     */
    private List<ProductCategoryTreeVo> children;

}
