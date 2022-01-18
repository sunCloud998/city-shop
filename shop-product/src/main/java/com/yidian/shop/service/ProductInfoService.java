package com.yidian.shop.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yidian.shop.entity.ProductInfo;
import com.yidian.shop.param.ProductChangeStatusParam;
import com.yidian.shop.param.ProductListParam;
import com.yidian.shop.param.ProductSaveOrUpdateParam;
import com.yidian.shop.vo.ProductAllInfoVo;
import com.yidian.shop.vo.ProductInfoVo;
import com.yidian.shop.vo.Result;

/**
 * <p>
 * 商品基本信息表 服务类
 * </p>
 *
 * @author dev
 * @since 2021-08-19
 */
public interface ProductInfoService extends IService<ProductInfo> {

    /**
     * 添加或者修改商品
     *
     * @param param
     * @return
     */
    Result<String> saveOrUpdateProductInfo(ProductSaveOrUpdateParam param);

    /**
     * 查询商品所有信息
     *
     * @param id
     * @return
     */
    Result<ProductAllInfoVo> getProductAllInfo(Long id);

    /**
     * 商品列表查询
     *
     * @param param
     * @return
     */
    Result<Page<ProductInfoVo>> getProductInfoList(ProductListParam param);

    /**
     * 商品上下架
     *
     * @param param
     * @return
     */
    Result<String> productChangeStatus(ProductChangeStatusParam param);
}
