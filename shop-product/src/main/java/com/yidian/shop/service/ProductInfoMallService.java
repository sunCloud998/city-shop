package com.yidian.shop.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yidian.shop.param.ProductMallParam;
import com.yidian.shop.vo.ProductAllInfoVo;
import com.yidian.shop.vo.ProductIndexVo;
import com.yidian.shop.vo.ProductInfoVo;
import com.yidian.shop.vo.Result;

/**
 * @function:
 * @description: ProductInfoMallService.java
 * @date: 2021/08/23
 * @author: sunfayun
 * @version: 1.0
 */
public interface ProductInfoMallService {

    /**
     * 获取首页列表数据
     *
     * @return
     */
    Result<ProductIndexVo> getIndexProductInfo();

    /**
     * 获取指定区域商品列表
     *
     * @param param
     * @return
     */
    Result<Page<ProductInfoVo>> getConditionProductInfo(ProductMallParam param);

    /**
     * 获取商品详细信息
     *
     * @param id
     * @return
     */
    Result<ProductAllInfoVo> getProductDetailInfo(Long id);
}
