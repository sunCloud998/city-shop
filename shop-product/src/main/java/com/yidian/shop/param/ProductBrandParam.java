package com.yidian.shop.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @function:
 * @description: ProductBrandParam.java
 * @date: 2021/08/16
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class ProductBrandParam {

    /**
     * 品牌ID
     */
    private Long id;

    /**
     * 品牌名称
     */
    @NotBlank(message = "品牌名称不为空")
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

}
