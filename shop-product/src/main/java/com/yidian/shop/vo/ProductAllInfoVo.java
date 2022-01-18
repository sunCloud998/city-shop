package com.yidian.shop.vo;

import lombok.Data;

import java.util.List;

/**
 * 商品所有信息返回结果
 *
 * @function:
 * @description: ProductAllInfoVo.java
 * @date: 2021/08/22
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class ProductAllInfoVo extends ProductInfoVo {

    /**
     * 商品宣传图片列表，采用JSON数组格式
     */
    private List<String> images;

    /**
     * 商品简介
     */
    private String introduction;

    /**
     * 商品详细介绍，是富文本格式
     */
    private String detail;

    /**
     * 商家物流费配置信息
     */
    private DeliveryFeeConfigVo deliveryFeeConfigVo;

    /**
     * 商品参数列表
     */
    private List<ProductAttributeVo> productAttributeList;

    /**
     * 商品规格列表
     */
    private List<ProductSpecVo> productSpecList;

    /**
     * 商品sku列表
     */
    private List<ProductSkuVo> productSkuList;

}
