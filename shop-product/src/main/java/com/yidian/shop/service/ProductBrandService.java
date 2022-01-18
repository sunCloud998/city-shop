package com.yidian.shop.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yidian.shop.entity.ProductBrand;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yidian.shop.param.ProductBrandListParam;
import com.yidian.shop.param.ProductBrandParam;
import com.yidian.shop.vo.ProductBrandVo;
import com.yidian.shop.vo.Result;

/**
 * <p>
 * 商品品牌 服务类
 * </p>
 *
 * @author dev
 * @since 2021-08-15
 */
public interface ProductBrandService extends IService<ProductBrand> {

    /**
     * 添加或者修改商品品牌
     *
     * @param param
     * @return
     */
    Result<String> saveOrUpdateProductBrand(ProductBrandParam param);

    /**
     * 根据ID删除商品品牌
     *
     * @param id
     * @return
     */
    Result<String> delProductBrand(Long id);

    /**
     * 根据ID获取商品品牌信息
     *
     * @param id
     * @return
     */
    Result<ProductBrandVo> getProductBrandById(Long id);

    /**
     * 分页获取商品品牌信息
     *
     * @param param
     * @return
     */
    Result<Page<ProductBrandVo>> getProductBrandList(ProductBrandListParam param);
}
