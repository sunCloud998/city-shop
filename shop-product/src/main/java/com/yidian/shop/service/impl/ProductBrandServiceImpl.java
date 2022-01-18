package com.yidian.shop.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yidian.shop.entity.ProductBrand;
import com.yidian.shop.mapper.ProductBrandMapper;
import com.yidian.shop.param.ProductBrandListParam;
import com.yidian.shop.param.ProductBrandParam;
import com.yidian.shop.service.ProductBrandService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yidian.shop.vo.ProductBrandVo;
import com.yidian.shop.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品品牌 服务实现类
 * </p>
 *
 * @author dev
 * @since 2021-08-15
 */
@Service
@Slf4j
public class ProductBrandServiceImpl extends ServiceImpl<ProductBrandMapper, ProductBrand> implements ProductBrandService {

    @Override
    public Result<String> saveOrUpdateProductBrand(ProductBrandParam param) {
        if (param == null) {
            return Result.paramError();
        }
        if (param.getId() != null) {
            // 更新
            ProductBrand productBrand = this.getById(param.getId());
            if (productBrand == null) {
                return Result.errorInfo("更新的商品品牌不存在");
            }
            BeanUtils.copyProperties(param, productBrand);
            this.saveOrUpdate(productBrand);
            return Result.dealSuccess();
        }
        // 添加
        ProductBrand productBrand = new ProductBrand();
        BeanUtils.copyProperties(param, productBrand);
        this.saveOrUpdate(productBrand);
        return Result.dealSuccess();
    }

    @Override
    public Result<String> delProductBrand(Long id) {
        ProductBrand productBrand = this.getById(id);
        if (productBrand == null) {
            return Result.errorInfo("需要删除的商品品牌不存在");
        }
        boolean delResult = this.removeById(productBrand.getId());
        if (!delResult) {
            return Result.dealError();
        }
        return Result.dealSuccess();
    }

    @Override
    public Result<ProductBrandVo> getProductBrandById(Long id) {
        if (id == null) {
            return Result.paramError();
        }
        ProductBrand productBrand = this.getById(id);
        if (productBrand == null) {
            return Result.errorInfo("商品品牌信息不存在");
        }
        ProductBrandVo productBrandVo = new ProductBrandVo();
        BeanUtils.copyProperties(productBrand, productBrandVo);
        return Result.success(productBrandVo);
    }

    @Override
    public Result<Page<ProductBrandVo>> getProductBrandList(ProductBrandListParam param) {
        Page<ProductBrandVo> page = new Page<>(param.getCurrentPage(), param.getSize());
        IPage<ProductBrand> productBrandIPage = baseMapper.getProductBrandList(page, param);
        if (productBrandIPage == null || CollectionUtils.isEmpty(productBrandIPage.getRecords())) {
            return Result.noData();
        }
        List<ProductBrandVo> productBrandVoList = productBrandIPage.getRecords().stream().map(productBrand -> {
            ProductBrandVo productBrandVo = new ProductBrandVo();
            BeanUtils.copyProperties(productBrand, productBrandVo);
            return productBrandVo;
        }).collect(Collectors.toList());
        page.setTotal(productBrandIPage.getTotal());
        page.setRecords(productBrandVoList);
        return Result.success(page);
    }
}
