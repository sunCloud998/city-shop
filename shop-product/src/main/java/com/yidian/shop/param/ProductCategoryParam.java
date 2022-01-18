package com.yidian.shop.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @function:
 * @description: ProductCategoryParam.java
 * @date: 2021/08/13
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class ProductCategoryParam {

    /**
     * 分类ID
     */
    private Long id;

    /**
     * 父级分类的编号
     */
    private Long parentId;

    /**
     * 商品分类名称
     */
    @NotBlank(message = "分类名称不能为空")
    private String name;

    /**
     * 分类级别：0->1级；1->2级
     */
    @NotNull(message = "分类级别不能为空")
    private Integer level;

    /**
     * 排序
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

}
