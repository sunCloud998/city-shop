package com.yidian.shop.service.impl;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yidian.shop.component.CurrentRequestComponent;
import com.yidian.shop.constant.CommonConstant;
import com.yidian.shop.entity.*;
import com.yidian.shop.enums.ProductStatusEnum;
import com.yidian.shop.mapper.ProductInfoMapper;
import com.yidian.shop.merchant.entity.MerchantUserInfo;
import com.yidian.shop.merchant.service.MerchantUserInfoService;
import com.yidian.shop.param.ProductChangeStatusParam;
import com.yidian.shop.param.ProductListParam;
import com.yidian.shop.param.ProductSaveOrUpdateParam;
import com.yidian.shop.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yidian.shop.vo.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品基本信息表 服务实现类
 * </p>
 *
 * @author dev
 * @since 2021-08-19
 */
@Service
public class ProductInfoServiceImpl extends ServiceImpl<ProductInfoMapper, ProductInfo> implements ProductInfoService {

    @Resource
    private ProductDetailInfoService productDetailInfoService;
    @Resource
    private ProductAttributeInfoService productAttributeInfoService;
    @Resource
    private ProductSpecInfoService productSpecInfoService;
    @Resource
    private ProductSkuInfoService productSkuInfoService;
    @Resource
    private CurrentRequestComponent currentRequestComponent;
    @Resource
    private MerchantUserInfoService merchantUserInfoService;
    @Resource
    private ProductCategoryService productCategoryService;
    @Resource
    private ProductBrandService productBrandService;
    @Resource
    private DeliveryFeeConfigService deliveryFeeConfigService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<String> saveOrUpdateProductInfo(ProductSaveOrUpdateParam param) {
        Long userId = currentRequestComponent.getLoginUserId(CommonConstant.MERCHANT_USER_TYPE);
        if (userId == null) {
            return Result.notLoginError();
        }
        if (param.getId() != null) {
            return this.updateProductInfo(param);
        }
        ProductInfo productInfo = new ProductInfo();
        BeanUtils.copyProperties(param, productInfo);
        productInfo.setMerchantId(userId);
        this.saveOrUpdate(productInfo);
        // 保存商品详情
        ProductDetailInfo productDetailInfo = new ProductDetailInfo();
        productDetailInfo.setProductId(productInfo.getId());
        BeanUtils.copyProperties(param, productDetailInfo);
        if (CollectionUtils.isNotEmpty(param.getImages())) {
            productDetailInfo.setImages(JSONUtil.toJsonStr(param.getImages()));
        }
        productDetailInfoService.saveOrUpdate(productDetailInfo);
        // 保存商品参数
        if (CollectionUtils.isNotEmpty(param.getProductAttributeList())) {
            List<ProductAttributeInfo> productAttributeInfoList = param.getProductAttributeList().stream().map(attribute -> {
                ProductAttributeInfo productAttributeInfo = new ProductAttributeInfo();
                productAttributeInfo.setProductId(productInfo.getId());
                BeanUtils.copyProperties(attribute, productAttributeInfo);
                return productAttributeInfo;
            }).collect(Collectors.toList());
            productAttributeInfoService.saveOrUpdateBatch(productAttributeInfoList);
        }
        // 保存商品规格
        if (CollectionUtils.isNotEmpty(param.getProductSpecList())) {
            List<ProductSpecInfo> productSpecInfoList = param.getProductSpecList().stream().map(spec -> {
                ProductSpecInfo productSpecInfo = new ProductSpecInfo();
                productSpecInfo.setProductId(productInfo.getId());
                BeanUtils.copyProperties(spec, productSpecInfo);
                return productSpecInfo;
            }).collect(Collectors.toList());
            productSpecInfoService.saveOrUpdateBatch(productSpecInfoList);
        }
        // 保存商品sku
        if (CollectionUtils.isNotEmpty(param.getProductSkuList())) {
            List<ProductSkuInfo> productSkuInfoList = param.getProductSkuList().stream().map(sku -> {
                ProductSkuInfo productSkuInfo = new ProductSkuInfo();
                productSkuInfo.setProductId(productInfo.getId());
                BeanUtils.copyProperties(sku, productSkuInfo);
                if (CollectionUtils.isNotEmpty(sku.getSpec())) {
                    productSkuInfo.setSpec(JSONUtil.toJsonStr(sku.getSpec()));
                }
                return productSkuInfo;
            }).collect(Collectors.toList());
            productSkuInfoService.saveOrUpdateBatch(productSkuInfoList);
        }
        return Result.dealSuccess();
    }

    private Result<String> updateProductInfo(ProductSaveOrUpdateParam param) {
        ProductInfo productInfo = this.getById(param.getId());
        if (productInfo == null) {
            return Result.errorInfo("商品信息不存在");
        }
        BeanUtils.copyProperties(param, productInfo);
        this.saveOrUpdate(productInfo);
        // 更新商品详情
        if (CollectionUtils.isNotEmpty(param.getImages()) || StringUtils.isNotBlank(param.getDetail()) || StringUtils.isNotBlank(param.getIntroduction())) {
            ProductDetailInfo productDetailInfo = productDetailInfoService.getProductDetailInfoByProductId(param.getId());
            if (productDetailInfo == null) {
                productDetailInfo = new ProductDetailInfo();
                productDetailInfo.setProductId(productInfo.getId());
            }
            BeanUtils.copyProperties(param, productDetailInfo);
            if (CollectionUtils.isNotEmpty(param.getImages())) {
                productDetailInfo.setImages(JSONUtil.toJsonStr(param.getImages()));
            }
            productDetailInfoService.saveOrUpdate(productDetailInfo);
        }
        // 更新商品参数
        if (CollectionUtils.isNotEmpty(param.getProductAttributeList())) {
            List<ProductSaveOrUpdateParam.ProductAttributeParam> deleteParam = param.getProductAttributeList().stream().filter(attribute -> attribute.getDelFlag() != null && attribute.getDelFlag() == 1).collect(Collectors.toList());
            List<ProductSaveOrUpdateParam.ProductAttributeParam> updateParam = param.getProductAttributeList().stream().filter(attribute -> attribute.getDelFlag() == null || attribute.getDelFlag() == 0).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(deleteParam)) {
                List<Long> deleteIdList = deleteParam.stream().map(ProductSaveOrUpdateParam.ProductAttributeParam::getId).collect(Collectors.toList());
                productAttributeInfoService.removeByIds(deleteIdList);
            }
            if (CollectionUtils.isNotEmpty(updateParam)) {
                List<ProductAttributeInfo> targetList = updateParam.stream().map(attribute -> {
                    ProductAttributeInfo productAttributeInfo = new ProductAttributeInfo();
                    BeanUtils.copyProperties(attribute, productAttributeInfo);
                    return productAttributeInfo;
                }).collect(Collectors.toList());
                productAttributeInfoService.saveOrUpdateBatch(targetList);
            }
        }
        // 更新商品规格
        if (CollectionUtils.isNotEmpty(param.getProductSpecList())) {
            List<ProductSaveOrUpdateParam.ProductSpecParam> deleteParam = param.getProductSpecList().stream().filter(spec -> spec.getDelFlag() != null && spec.getDelFlag() == 1).collect(Collectors.toList());
            List<ProductSaveOrUpdateParam.ProductSpecParam> updateParam = param.getProductSpecList().stream().filter(spec -> spec.getDelFlag() == null || spec.getDelFlag() == 0).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(deleteParam)) {
                List<Long> deleteIdList = deleteParam.stream().map(ProductSaveOrUpdateParam.ProductSpecParam::getId).collect(Collectors.toList());
                productSpecInfoService.removeByIds(deleteIdList);
            }
            if (CollectionUtils.isNotEmpty(updateParam)) {
                List<ProductSpecInfo> productSpecInfoList = updateParam.stream().map(spec -> {
                    ProductSpecInfo productSpecInfo = new ProductSpecInfo();
                    BeanUtils.copyProperties(spec, productSpecInfo);
                    return productSpecInfo;
                }).collect(Collectors.toList());
                productSpecInfoService.saveOrUpdateBatch(productSpecInfoList);
            }
        }
        // 更新商品sku
        if (CollectionUtils.isNotEmpty(param.getProductSkuList())) {
            List<ProductSaveOrUpdateParam.ProductSkuParam> deleteParam = param.getProductSkuList().stream().filter(sku -> sku.getDelFlag() != null && sku.getDelFlag() == 1).collect(Collectors.toList());
            List<ProductSaveOrUpdateParam.ProductSkuParam> updateParam = param.getProductSkuList().stream().filter(sku -> sku.getDelFlag() == null || sku.getDelFlag() == 0).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(deleteParam)) {
                List<Long> deleteIdList = deleteParam.stream().map(ProductSaveOrUpdateParam.ProductSkuParam::getId).collect(Collectors.toList());
                productSkuInfoService.removeByIds(deleteIdList);
            }
            if (CollectionUtils.isNotEmpty(updateParam)) {
                List<ProductSkuInfo> productSkuInfoList = updateParam.stream().map(sku -> {
                    ProductSkuInfo productSkuInfo = new ProductSkuInfo();
                    BeanUtils.copyProperties(sku, productSkuInfo);
                    if (CollectionUtils.isNotEmpty(sku.getSpec())) {
                        productSkuInfo.setSpec(JSONUtil.toJsonStr(sku.getSpec()));
                    }
                    return productSkuInfo;
                }).collect(Collectors.toList());
                productSkuInfoService.saveOrUpdateBatch(productSkuInfoList);
            }
        }
        return Result.dealSuccess();
    }

    @Override
    public Result<ProductAllInfoVo> getProductAllInfo(Long id) {
        ProductInfo productInfo = this.getById(id);
        if (productInfo == null) {
            return Result.noData();
        }
        MerchantUserInfo merchantUserInfo = merchantUserInfoService.getById(productInfo.getMerchantId());
        if (merchantUserInfo == null) {
            return Result.noData();
        }
        ProductAllInfoVo productAllInfoVo = new ProductAllInfoVo();
        BeanUtils.copyProperties(productInfo, productAllInfoVo);
        productAllInfoVo.setMerchantName(merchantUserInfo.getMerchantName());

        ProductDetailInfo productDetailInfo = productDetailInfoService.getProductDetailInfoByProductId(id);
        if (productDetailInfo != null) {
            if (StringUtils.isNotBlank(productDetailInfo.getImages())) {
                productAllInfoVo.setImages(JSONUtil.toBean(productDetailInfo.getImages(), new TypeReference<List<String>>() {
                }, Boolean.FALSE));
            }
            productAllInfoVo.setIntroduction(productDetailInfo.getIntroduction());
            productAllInfoVo.setDetail(productDetailInfo.getDetail());
        }

        ProductCategory productCategory = productCategoryService.getById(productInfo.getCategoryId());
        if (productCategory != null) {
            productAllInfoVo.setCategoryName(productCategory.getName());
        }

        ProductBrand productBrand = productBrandService.getById(productInfo.getBrandId());
        if (productBrand != null) {
            productAllInfoVo.setBrandName(productBrand.getName());
        }

        List<ProductAttributeInfo> productAttributeInfoList = productAttributeInfoService.list(new LambdaQueryWrapper<ProductAttributeInfo>().eq(ProductAttributeInfo::getProductId, id));
        if (CollectionUtils.isNotEmpty(productAttributeInfoList)) {
            List<ProductAttributeVo> productAttributeVoList = productAttributeInfoList.stream().map(attribute -> {
                ProductAttributeVo productAttributeVo = new ProductAttributeVo();
                BeanUtils.copyProperties(attribute, productAttributeVo);
                return productAttributeVo;
            }).collect(Collectors.toList());
            productAllInfoVo.setProductAttributeList(productAttributeVoList);
        }

        List<ProductSpecInfo> productSpecInfoList = productSpecInfoService.list(new LambdaQueryWrapper<ProductSpecInfo>().eq(ProductSpecInfo::getProductId, id));
        if (CollectionUtils.isNotEmpty(productSpecInfoList)) {
            List<ProductSpecVo> productSpecVoList = productSpecInfoList.stream().map(spec -> {
                ProductSpecVo productSpecVo = new ProductSpecVo();
                BeanUtils.copyProperties(spec, productSpecVo);
                return productSpecVo;
            }).collect(Collectors.toList());
            productAllInfoVo.setProductSpecList(productSpecVoList);
        }

        List<ProductSkuInfo> productSkuInfoList = productSkuInfoService.list(new LambdaQueryWrapper<ProductSkuInfo>().eq(ProductSkuInfo::getProductId, id));
        if (CollectionUtils.isNotEmpty(productSkuInfoList)) {
            List<ProductSkuVo> productSkuVoList = productSkuInfoList.stream().map(sku -> {
                ProductSkuVo productSkuVo = new ProductSkuVo();
                BeanUtils.copyProperties(sku, productSkuVo);
                if (StringUtils.isNotBlank(sku.getSpec())) {
                    productSkuVo.setSpec(JSONUtil.toBean(sku.getSpec(), new TypeReference<List<String>>() {
                    }, Boolean.FALSE));
                }
                return productSkuVo;
            }).collect(Collectors.toList());
            productAllInfoVo.setProductSkuList(productSkuVoList);
        }

        // 获取商家发货地址
        DeliveryFeeConfigVo deliveryFeeConfigVo = deliveryFeeConfigService.getMerchantDeliveryFeeConfig(productInfo.getMerchantId());
        if (deliveryFeeConfigVo != null) {
            productAllInfoVo.setDeliveryFeeConfigVo(deliveryFeeConfigVo);
        }
        return Result.success(productAllInfoVo);
    }

    @Override
    public Result<Page<ProductInfoVo>> getProductInfoList(ProductListParam param) {
        UserTokenInfoVo userTokenInfoVo = currentRequestComponent.getUserTokenInfo();
        if (userTokenInfoVo == null) {
            return Result.notLoginError();
        }
        if (Objects.equals(userTokenInfoVo.getUserType(), CommonConstant.MERCHANT_USER_TYPE)) {
            param.setMerchantId(userTokenInfoVo.getUserId());
        }
        Page<ProductInfoVo> productInfoPage = baseMapper.getProductInfoListByPage(new Page<>(param.getCurrentPage(), param.getSize()), param);
        if (productInfoPage == null || CollectionUtils.isEmpty(productInfoPage.getRecords())) {
            return Result.noData();
        }
        return Result.success(productInfoPage);
    }

    @Override
    public Result<String> productChangeStatus(ProductChangeStatusParam param) {
        ProductStatusEnum productStatusEnum = ProductStatusEnum.getProductStatusEnum(param.getStatus());
        if (productStatusEnum == null) {
            return Result.paramError();
        }
        ProductInfo productInfo = this.getById(param.getId());
        if (productInfo == null) {
            return Result.errorInfo("商品信息不存在");
        }
        productInfo.setStatus(param.getStatus());
        productInfo.setUpdateTime(LocalDateTime.now());
        if (Objects.equals(param.getStatus(), ProductStatusEnum.UP.getCode())) {
            productInfo.setUpTime(LocalDateTime.now());
        }
        boolean dealResult = this.saveOrUpdate(productInfo);
        if (!dealResult) {
            return Result.dealError();
        }
        return Result.dealSuccess();
    }
}
