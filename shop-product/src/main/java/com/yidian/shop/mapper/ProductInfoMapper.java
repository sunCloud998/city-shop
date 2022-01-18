package com.yidian.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yidian.shop.entity.ProductInfo;
import com.yidian.shop.param.ProductListParam;
import com.yidian.shop.param.ProductMallParam;
import com.yidian.shop.vo.ProductInfoVo;
import org.springframework.data.repository.query.Param;

/**
 * <p>
 * 商品基本信息表 Mapper 接口
 * </p>
 *
 * @author dev
 * @since 2021-08-19
 */
public interface ProductInfoMapper extends BaseMapper<ProductInfo> {

    Page<ProductInfoVo> getProductInfoListByPage(@Param("page") Page<ProductInfoVo> page,
                                                 @Param("param") ProductListParam param);

    IPage<ProductInfoVo> getIndexProductInfoList(@Param("page") Page<ProductInfoVo> page,
                                                 @Param("productType") Integer productType);

    Page<ProductInfoVo> getProductInfoListByCondition(@Param("page") Page<ProductInfoVo> page,
                                                      @Param("param") ProductMallParam param);

}
