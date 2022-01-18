package com.yidian.shop.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yidian.shop.entity.ProductCategory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yidian.shop.param.ProductCategoryListParam;
import com.yidian.shop.param.ProductCategoryParam;
import com.yidian.shop.vo.ProductCategoryTreeVo;
import com.yidian.shop.vo.ProductCategoryVo;
import com.yidian.shop.vo.Result;

import java.util.List;

/**
 * <p>
 * 产品分类 服务类
 * </p>
 *
 * @author dev
 * @since 2021-08-13
 */
public interface ProductCategoryService extends IService<ProductCategory> {

    /**
     * 添加或者修改商品分类
     *
     * @param param
     * @return
     */
    Result<String> addOrUpdateProductCategory(ProductCategoryParam param);

    /**
     * 产品分类列表查询
     *
     * @param param
     * @return
     */
    Result<Page<ProductCategoryVo>> getProductCategoryList(ProductCategoryListParam param);

    /**
     * 删除产品分类ID
     *
     * @param id
     * @return
     */
    Result<String> delProductCategory(Long id);

    /**
     * 获取树形结构的产品分类
     *
     * @return
     */
    Result<List<ProductCategoryTreeVo>> getProductCategoryTree();

    /**
     * 根据ID获取商品分类信息
     *
     * @param id
     * @return
     */
    Result<ProductCategoryVo> getProductCategoryById(Long id);
}
