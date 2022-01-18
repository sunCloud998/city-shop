package com.yidian.shop.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yidian.shop.entity.ProductBrand;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yidian.shop.param.ProductBrandListParam;
import com.yidian.shop.vo.ProductBrandVo;
import org.springframework.data.repository.query.Param;

/**
 * <p>
 * 商品品牌 Mapper 接口
 * </p>
 *
 * @author dev
 * @since 2021-08-15
 */
public interface ProductBrandMapper extends BaseMapper<ProductBrand> {

    IPage<ProductBrand> getProductBrandList(@Param("page") Page<ProductBrandVo> page,
                                            @Param("param") ProductBrandListParam param);

}
