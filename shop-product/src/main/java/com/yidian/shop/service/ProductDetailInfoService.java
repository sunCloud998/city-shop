package com.yidian.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yidian.shop.entity.ProductDetailInfo;

/**
 * <p>
 * 商品详细信息表 服务类
 * </p>
 *
 * @author dev
 * @since 2021-08-19
 */
public interface ProductDetailInfoService extends IService<ProductDetailInfo> {

    ProductDetailInfo getProductDetailInfoByProductId(Long productId);

}
