package com.yidian.shop.param;

import lombok.Data;

/**
 * 商品品牌分页列表查询请求参数
 *
 * @function:
 * @description: ProductBrandListParam.java
 * @date: 2021/08/16
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class ProductBrandListParam extends BasePageParam {

    /**
     * 商品品牌名称
     */
    private String name;

}
