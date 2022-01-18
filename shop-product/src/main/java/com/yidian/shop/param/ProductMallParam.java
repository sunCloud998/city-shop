package com.yidian.shop.param;

import lombok.Data;

/**
 * 商城商品查询请求参数
 *
 * @function:
 * @description: ProductMallParam.java
 * @date: 2021/08/23
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class ProductMallParam extends BasePageParam {

    /**
     * 商品分类
     */
    private Integer categoryId;

    /**
     * 商品品牌
     */
    private Integer brandId;

    /**
     * 供应商ID
     */
    private Long merchantId;

    /**
     * 商品类型，1-新品，2-人气，3-默认
     */
    private Integer productType;

    /**
     * 搜索关键词
     */
    private String searchKey;

}
