package com.yidian.shop.param;

import lombok.Data;

/**
 * 商品列表请求参数
 *
 * @function:
 * @description: ProductListParam.java
 * @date: 2021/08/22
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class ProductListParam extends BasePageParam {

    /**
     * 分类ID
     */
    private Integer categoryId;

    /**
     * 品牌ID
     */
    private Integer brandId;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品状态
     */
    private Integer status;

    /**
     * 供应商ID
     */
    private Long merchantId;

}
