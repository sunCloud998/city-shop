package com.yidian.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yidian.shop.entity.ProductDetailInfo;
import com.yidian.shop.mapper.ProductDetailInfoMapper;
import com.yidian.shop.service.ProductDetailInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品详细信息表 服务实现类
 * </p>
 *
 * @author dev
 * @since 2021-08-19
 */
@Service
public class ProductDetailInfoServiceImpl extends ServiceImpl<ProductDetailInfoMapper, ProductDetailInfo> implements ProductDetailInfoService {

    @Override
    public ProductDetailInfo getProductDetailInfoByProductId(Long productId) {
        if (productId == null) {
            return null;
        }
        return this.getOne(new LambdaQueryWrapper<ProductDetailInfo>().eq(ProductDetailInfo::getProductId, productId).last("limit 1"));
    }
}
