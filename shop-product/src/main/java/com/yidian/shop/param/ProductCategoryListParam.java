package com.yidian.shop.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 分类列表查询请求参数
 *
 * @function:
 * @description: ProductCategoryListParam.java
 * @date: 2021/08/13
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class ProductCategoryListParam extends BasePageParam {

    /**
     * 父级分类ID
     */
    @NotNull(message = "父级分类ID不能为空")
    private Long parentId;

}
