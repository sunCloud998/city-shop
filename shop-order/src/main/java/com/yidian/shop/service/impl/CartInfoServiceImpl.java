package com.yidian.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yidian.shop.component.CurrentRequestComponent;
import com.yidian.shop.constant.CommonConstant;
import com.yidian.shop.entity.CartInfo;
import com.yidian.shop.entity.DeliveryFeeConfig;
import com.yidian.shop.entity.ProductInfo;
import com.yidian.shop.entity.ProductSkuInfo;
import com.yidian.shop.enums.ProductStatusEnum;
import com.yidian.shop.mapper.CartInfoMapper;
import com.yidian.shop.merchant.entity.MerchantUserInfo;
import com.yidian.shop.merchant.enums.MerchantStatusEnum;
import com.yidian.shop.merchant.service.MerchantUserInfoService;
import com.yidian.shop.param.AddCartInfoParam;
import com.yidian.shop.param.UpdateCartInfoParam;
import com.yidian.shop.service.CartInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yidian.shop.service.DeliveryFeeConfigService;
import com.yidian.shop.service.ProductInfoService;
import com.yidian.shop.service.ProductSkuInfoService;
import com.yidian.shop.vo.CartInfoListVo;
import com.yidian.shop.vo.CartInfoVo;
import com.yidian.shop.vo.DeliveryFeeConfigVo;
import com.yidian.shop.vo.Result;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 购物车表 服务实现类
 * </p>
 *
 * @author dev
 * @since 2021-08-26
 */
@Service
public class CartInfoServiceImpl extends ServiceImpl<CartInfoMapper, CartInfo> implements CartInfoService {

    @Resource
    private CurrentRequestComponent currentRequestComponent;
    @Resource
    private ProductInfoService productInfoService;
    @Resource
    private ProductSkuInfoService productSkuInfoService;
    @Resource
    private MerchantUserInfoService merchantUserInfoService;
    @Resource
    private DeliveryFeeConfigService deliveryFeeConfigService;

    @Override
    public Result<Long> addCartInfo(AddCartInfoParam param) {
        Long userId = currentRequestComponent.getLoginUserId(CommonConstant.MALL_USER_TYPE);
        if (userId == null) {
            return Result.notLoginError();
        }
        ProductInfo productInfo = productInfoService.getById(param.getProductId());
        if (productInfo == null) {
            return Result.errorInfo("商品不存在，请重新选择");
        }
        if (productInfo.getStatus() != ProductStatusEnum.UP.getCode()) {
            return Result.errorInfo("商品未上架，请重新选择");
        }
        ProductSkuInfo productSkuInfo = productSkuInfoService.getById(param.getProductSkuId());
        if (productSkuInfo == null) {
            return Result.errorInfo("商品不存在，请重新选择");
        }
        if (productSkuInfo.getStock() < param.getNumber()) {
            return Result.errorInfo("商品库存不足，请修改购买数量");
        }
        MerchantUserInfo merchantUserInfo = merchantUserInfoService.getById(productInfo.getMerchantId());
        if (merchantUserInfo == null || merchantUserInfo.getStatus() != MerchantStatusEnum.PASS.getCode()) {
            return Result.errorInfo("该商品商家已下线，请重新选择");
        }
        CartInfo cartInfo = this.getOne(new LambdaQueryWrapper<CartInfo>().eq(CartInfo::getUserId, userId).eq(CartInfo::getProductId, param.getProductId()).eq(CartInfo::getProductSkuId, param.getProductSkuId()).last("limit 1"));
        if (cartInfo != null) {
            cartInfo.setNumber(param.getNumber());
            boolean updateResult = this.saveOrUpdate(cartInfo);
            if (!updateResult) {
                return Result.dealError();
            }
            return Result.success(cartInfo.getId());
        }
        cartInfo = new CartInfo();
        cartInfo.setUserId(userId);
        cartInfo.setMerchantId(productInfo.getMerchantId());
        cartInfo.setProductId(productInfo.getId());
        cartInfo.setBarCode(productInfo.getBarCode());
        cartInfo.setProductName(productInfo.getName());
        cartInfo.setProductSkuId(productSkuInfo.getId());
        cartInfo.setPrice(productSkuInfo.getPrice());
        cartInfo.setNumber(param.getNumber());
        cartInfo.setSpec(productSkuInfo.getSpec());
        boolean saveResult = this.saveOrUpdate(cartInfo);
        if (!saveResult) {
            return Result.dealError();
        }
        return Result.success(cartInfo.getId());
    }

    @Override
    public Result<String> updateCartInfo(UpdateCartInfoParam param) {
        Long userId = currentRequestComponent.getLoginUserId(CommonConstant.MALL_USER_TYPE);
        if (userId == null) {
            return Result.notLoginError();
        }
        CartInfo cartInfo = this.getById(param.getCartId());
        if (cartInfo == null) {
            return Result.errorInfo("修改的购物车商品不存在");
        }
        if (!Objects.equals(userId, cartInfo.getUserId())) {
            return Result.noAuth();
        }
        cartInfo.setNumber(param.getNumber());
        cartInfo.setChecked(param.getChecked());
        boolean updateResult = this.saveOrUpdate(cartInfo);
        if (!updateResult) {
            return Result.dealError();
        }
        return Result.dealSuccess();
    }

    @Override
    public Result<String> delCartInfo(Long cartId) {
        Long userId = currentRequestComponent.getLoginUserId(CommonConstant.MALL_USER_TYPE);
        if (userId == null) {
            return Result.notLoginError();
        }
        CartInfo cartInfo = this.getById(cartId);
        if (cartInfo == null) {
            return Result.errorInfo("修改的购物车商品不存在");
        }
        if (!Objects.equals(userId, cartInfo.getUserId())) {
            return Result.noAuth();
        }
        boolean delResult = this.removeById(cartId);
        if (!delResult) {
            return Result.dealError();
        }
        return Result.dealSuccess();
    }

    @Override
    public Result<List<CartInfoListVo>> getCartInfoList() {
        Long userId = currentRequestComponent.getLoginUserId(CommonConstant.MALL_USER_TYPE);
        if (userId == null) {
            return Result.notLoginError();
        }
        List<CartInfo> cartInfoList = this.list(new LambdaQueryWrapper<CartInfo>().eq(CartInfo::getUserId, userId));
        if (CollectionUtils.isEmpty(cartInfoList)) {
            return Result.noData();
        }
        return Result.success(this.buildCartInfoListVoList(cartInfoList));
    }

    @Override
    public Result<List<CartInfoListVo>> getOrderConfirmInfo(List<Long> cartIdList) {
        Long userId = currentRequestComponent.getLoginUserId(CommonConstant.MALL_USER_TYPE);
        if (userId == null) {
            return Result.notLoginError();
        }
        List<CartInfo> cartInfoList;
        if (CollectionUtils.isEmpty(cartIdList)) {
            cartInfoList = this.list(new LambdaQueryWrapper<CartInfo>().eq(CartInfo::getUserId, userId).eq(CartInfo::getChecked, 1));
        } else {
            cartInfoList = this.listByIds(cartIdList);
        }
        if (CollectionUtils.isEmpty(cartInfoList)) {
            return Result.noData();
        }
        return Result.success(this.buildCartInfoListVoList(cartInfoList));
    }

    @Override
    public Result<String> clearCart() {
        Long userId = currentRequestComponent.getLoginUserId(CommonConstant.MALL_USER_TYPE);
        if (userId == null) {
            return Result.notLoginError();
        }
        List<CartInfo> cartInfoList = this.list(new LambdaQueryWrapper<CartInfo>().eq(CartInfo::getUserId, userId));
        if (CollectionUtils.isEmpty(cartInfoList)) {
            return Result.dealSuccess();
        }
        List<Long> cartIdList = cartInfoList.stream().map(CartInfo::getId).collect(Collectors.toList());
        boolean delResult = this.removeByIds(cartIdList);
        if (!delResult) {
            return Result.dealError();
        }
        return Result.dealSuccess();
    }

    private List<CartInfoListVo> buildCartInfoListVoList(List<CartInfo> cartInfoList) {
        List<Long> productIdList = cartInfoList.stream().map(CartInfo::getProductId).collect(Collectors.toList());
        List<ProductInfo> productInfoList = productInfoService.listByIds(productIdList);
        Map<Long, ProductInfo> productInfoMap = Maps.newHashMap();
        if (CollectionUtils.isNotEmpty(productInfoList)) {
            productInfoMap = productInfoList.stream().collect(Collectors.toMap(ProductInfo::getId, Function.identity()));
        }
        List<Long> productSkuIdList = cartInfoList.stream().map(CartInfo::getProductSkuId).collect(Collectors.toList());
        List<ProductSkuInfo> productSkuInfoList = productSkuInfoService.listByIds(productSkuIdList);
        Map<Long, ProductSkuInfo> productSkuInfoMap = Maps.newHashMap();
        if (CollectionUtils.isNotEmpty(productSkuInfoList)) {
            productSkuInfoMap = productSkuInfoList.stream().collect(Collectors.toMap(ProductSkuInfo::getId, Function.identity()));
        }
        List<Long> merchantIdList = cartInfoList.stream().map(CartInfo::getMerchantId).distinct().collect(Collectors.toList());
        List<MerchantUserInfo> merchantUserInfoList = merchantUserInfoService.listByIds(merchantIdList);
        Map<Long, MerchantUserInfo> merchantUserInfoMap = Maps.newHashMap();
        if (CollectionUtils.isNotEmpty(merchantUserInfoList)) {
            merchantUserInfoMap = merchantUserInfoList.stream().collect(Collectors.toMap(MerchantUserInfo::getId, Function.identity()));
        }
        Map<Long, List<CartInfo>> cartInfoMap = cartInfoList.stream().collect(Collectors.groupingBy(CartInfo::getMerchantId));
        // 获取物流费配置
        List<DeliveryFeeConfigVo> deliveryFeeConfigVoList = deliveryFeeConfigService.getMerchantDeliveryFeeConfigList(merchantIdList);
        Map<Long, DeliveryFeeConfigVo> deliveryFeeConfigVoMap = Maps.newHashMap();
        if (CollectionUtils.isNotEmpty(deliveryFeeConfigVoList)) {
            deliveryFeeConfigVoMap = deliveryFeeConfigVoList.stream().collect(Collectors.toMap(DeliveryFeeConfigVo::getMerchantId, Function.identity()));
        }
        // 组装数据
        List<CartInfoListVo> cartInfoListVoList = Lists.newArrayList();
        for (Map.Entry<Long, List<CartInfo>> entry : cartInfoMap.entrySet()) {
            CartInfoListVo cartInfoListVo = new CartInfoListVo();
            cartInfoListVo.setMerchantId(entry.getKey());
            cartInfoListVo.setMerchantName(merchantUserInfoMap.get(entry.getKey()) == null ? "" : merchantUserInfoMap.get(entry.getKey()).getMerchantName());
            cartInfoListVo.setMerchantCityId(merchantUserInfoMap.get(entry.getKey()) == null ? null : merchantUserInfoMap.get(entry.getKey()).getCityId());
            cartInfoListVo.setDeliveryFeeConfigVo(deliveryFeeConfigVoMap.get(entry.getKey()));
            List<CartInfoVo> cartInfoVoList = Lists.newArrayList();
            for (CartInfo cartInfo : entry.getValue()) {
                CartInfoVo cartInfoVo = new CartInfoVo();
                BeanUtils.copyProperties(cartInfo, cartInfoVo);
                if (!this.checkCartProductIsValid(merchantUserInfoMap.get(cartInfo.getMerchantId()), productInfoMap.get(cartInfo.getProductId()), productSkuInfoMap.get(cartInfo.getProductSkuId()), cartInfo)) {
                    cartInfoVo.setInvalid(1);
                }
                cartInfoVoList.add(cartInfoVo);
            }
            cartInfoListVo.setCartInfoList(cartInfoVoList);
            cartInfoListVoList.add(cartInfoListVo);
        }
        return cartInfoListVoList;
    }

    private boolean checkCartProductIsValid(MerchantUserInfo merchantUserInfo, ProductInfo productInfo, ProductSkuInfo productSkuInfo, CartInfo cartInfo) {
        if (merchantUserInfo == null || productInfo == null || productSkuInfo == null || cartInfo == null) {
            return Boolean.FALSE;
        }
        if (merchantUserInfo.getStatus() != MerchantStatusEnum.PASS.getCode()) {
            return Boolean.FALSE;
        }
        if (productInfo.getStatus() != ProductStatusEnum.UP.getCode()) {
            return Boolean.FALSE;
        }
        if (productSkuInfo.getStock() < cartInfo.getNumber()) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
