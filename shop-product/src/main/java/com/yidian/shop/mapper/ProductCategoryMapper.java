package com.yidian.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yidian.shop.entity.ProductCategory;
import com.yidian.shop.param.ProductCategoryListParam;
import com.yidian.shop.vo.ProductCategoryVo;
import org.springframework.data.repository.query.Param;

/**
 * <p>
 * 产品分类 Mapper 接口
 * </p>
 *
 * @author dev
 * @since 2021-08-13
 */
public interface ProductCategoryMapper extends BaseMapper<ProductCategory> {

    IPage<ProductCategory> getProductCategoryList(@Param("page") Page<ProductCategoryVo> page,
                                                  @Param("param") ProductCategoryListParam param);

}
