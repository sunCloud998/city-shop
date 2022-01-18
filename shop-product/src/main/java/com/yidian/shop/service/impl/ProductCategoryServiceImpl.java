package com.yidian.shop.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yidian.shop.entity.ProductCategory;
import com.yidian.shop.mapper.ProductCategoryMapper;
import com.yidian.shop.param.ProductCategoryListParam;
import com.yidian.shop.param.ProductCategoryParam;
import com.yidian.shop.service.ProductCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yidian.shop.vo.ProductCategoryTreeVo;
import com.yidian.shop.vo.ProductCategoryVo;
import com.yidian.shop.vo.Result;
import org.apache.commons.collections4.CollectionUtils;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 产品分类 服务实现类
 * </p>
 *
 * @author dev
 * @since 2021-08-13
 */
@Service
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory> implements ProductCategoryService {

    @Override
    public Result<String> addOrUpdateProductCategory(ProductCategoryParam param) {
        if (param.getId() != null) {
            //修改
            ProductCategory productCategory = this.getById(param.getId());
            if (productCategory == null) {
                return Result.errorInfo("修改的分类数据不存在");
            }
            BeanUtils.copyProperties(param, productCategory);
            this.updateById(productCategory);
            return Result.success();
        }
        // 新增
        ProductCategory productCategory = new ProductCategory();
        BeanUtils.copyProperties(param, productCategory);
        if (StringUtils.isBlank(productCategory.getKeywords())) {
            productCategory.setKeywords(productCategory.getName());
        }
        this.saveOrUpdate(productCategory);
        return Result.success();
    }

    @Override
    public Result<Page<ProductCategoryVo>> getProductCategoryList(ProductCategoryListParam param) {
        Page<ProductCategoryVo> page = new Page<>(param.getCurrentPage(), param.getSize());
        IPage<ProductCategory> productCategoryIPage = baseMapper.getProductCategoryList(page, param);
        if (productCategoryIPage == null || CollectionUtils.isEmpty(productCategoryIPage.getRecords())) {
            return Result.noData();
        }
        List<ProductCategoryVo> productCategoryVoList = productCategoryIPage.getRecords().stream().map(productCategory -> {
            ProductCategoryVo productCategoryVo = new ProductCategoryVo();
            BeanUtils.copyProperties(productCategory, productCategoryVo);
            return productCategoryVo;
        }).collect(Collectors.toList());
        page.setTotal(productCategoryIPage.getTotal());
        page.setRecords(productCategoryVoList);
        return Result.success(page);
    }

    @Override
    public Result<String> delProductCategory(Long id) {
        if (id == null) {
            return Result.paramError();
        }
        boolean delResult = this.removeById(id);
        if (!delResult) {
            return Result.dealError();
        }
        return Result.dealSuccess();
    }

    @Override
    public Result<ProductCategoryVo> getProductCategoryById(Long id) {
        if (id == null) {
            return Result.paramError();
        }
        ProductCategory productCategory = this.getById(id);
        if (productCategory == null) {
            return Result.errorInfo("商品分类信息不存在");
        }
        ProductCategoryVo productCategoryVo = new ProductCategoryVo();
        BeanUtils.copyProperties(productCategory, productCategoryVo);
        return Result.success(productCategoryVo);
    }

    @Override
    public Result<List<ProductCategoryTreeVo>> getProductCategoryTree() {
        return Result.success(this.buildAreaInfoVoList());
    }

    private List<ProductCategoryTreeVo> buildAreaInfoVoList() {
        List<ProductCategory> productCategoryList = this.list();
        if (CollectionUtils.isEmpty(productCategoryList)) {
            return Collections.emptyList();
        }
        return getProductCategoryChildTree(productCategoryList, 0);
    }

    private List<ProductCategoryTreeVo> getProductCategoryChildTree(List<ProductCategory> allProductCategoryList, long parentId) {
        List<ProductCategoryTreeVo> productCategoryTreeList = new ArrayList<>();
        List<ProductCategory> childCategoryList = getChildCategory(allProductCategoryList, parentId);
        if (CollectionUtils.isNotEmpty(childCategoryList)) {
            for (ProductCategory category : childCategoryList) {
                ProductCategoryTreeVo categoryTreeVo = new ProductCategoryTreeVo();
                categoryTreeVo.setId(category.getId());
                categoryTreeVo.setParentId(category.getParentId());
                categoryTreeVo.setName(category.getName());
                List<ProductCategoryTreeVo> subList = getProductCategoryChildTree(allProductCategoryList, category.getId());
                if (CollectionUtils.isNotEmpty(subList)) {
                    categoryTreeVo.setChildren(subList);
                }
                productCategoryTreeList.add(categoryTreeVo);
            }
        }
        return productCategoryTreeList;
    }

    /**
     * 过滤出父分类中的子分类
     *
     * @param allCategoryList
     * @param parentId
     * @return
     */
    private List<ProductCategory> getChildCategory(List<ProductCategory> allCategoryList, long parentId) {
        if (CollectionUtils.isEmpty(allCategoryList)) {
            return Collections.emptyList();
        }
        List<ProductCategory> categoryList = allCategoryList.stream().filter(e -> e.getParentId() == parentId).collect(Collectors.toList());
        allCategoryList.removeAll(categoryList);
        return categoryList;
    }
}
