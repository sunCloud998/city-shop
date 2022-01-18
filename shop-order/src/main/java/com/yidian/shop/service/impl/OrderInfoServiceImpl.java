package com.yidian.shop.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yidian.shop.component.CurrentRequestComponent;
import com.yidian.shop.constant.CommonConstant;
import com.yidian.shop.entity.*;
import com.yidian.shop.enums.*;
import com.yidian.shop.mall.entity.UserInfo;
import com.yidian.shop.mall.service.UserInfoService;
import com.yidian.shop.mapper.OrderInfoMapper;
import com.yidian.shop.merchant.entity.MerchantUserInfo;
import com.yidian.shop.merchant.enums.MerchantStatusEnum;
import com.yidian.shop.merchant.service.MerchantUserInfoService;
import com.yidian.shop.param.*;
import com.yidian.shop.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yidian.shop.utils.GenerateNoUtil;
import com.yidian.shop.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author dev
 * @since 2021-08-26
 */
@Service
@Slf4j
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {

    @Resource
    private CurrentRequestComponent currentRequestComponent;
    @Resource
    private TransactionTemplate transactionTemplate;
    @Resource
    private CartInfoService cartInfoService;
    @Resource
    private ProductInfoService productInfoService;
    @Resource
    private ProductSkuInfoService productSkuInfoService;
    @Resource
    private MerchantUserInfoService merchantUserInfoService;
    @Resource
    private OrderDetailInfoService orderDetailInfoService;
    @Resource
    private OrderProcessInfoService orderProcessInfoService;
    @Resource
    private OrderProductVersionService orderProductVersionService;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private UserAddressService userAddressService;
    @Resource
    private DeliveryFeeConfigService deliveryFeeConfigService;

    @Override
    public Result<List<String>> commitOrderInfo(CommitOrderParam param) {
        if (CollectionUtils.isEmpty(param.getCartIdList())) {
            return Result.paramError();
        }
        if (param.getOrderType() == OrderTypeEnum.RESERVE.getCode() && param.getReserveTime() == null) {
            return Result.paramError();
        }
        Long userId = currentRequestComponent.getLoginUserId(CommonConstant.MALL_USER_TYPE);
        if (userId == null) {
            return Result.notLoginError();
        }
        List<CartInfo> cartInfoList = cartInfoService.listByIds(param.getCartIdList());
        if (CollectionUtils.isEmpty(cartInfoList)) {
            return Result.errorInfo("购物车数据为空，提交订单失败");
        }
        // 判断商品
        List<Long> productIdList = cartInfoList.stream().map(CartInfo::getProductId).collect(Collectors.toList());
        List<ProductInfo> productInfoList = productInfoService.listByIds(productIdList);
        if (CollectionUtils.isEmpty(productInfoList)) {
            return Result.errorInfo("商品信息不存在，提交订单失败");
        }
        Optional<CartInfo> productCartInfoOptional = cartInfoList.stream().filter(cartInfo -> {
            Optional<ProductInfo> productInfoOptional = productInfoList.stream().filter(productInfo -> Objects.equals(cartInfo.getProductId(), productInfo.getId())).findAny();
            if (productInfoOptional.isPresent()) {
                return productInfoOptional.get().getStatus() != ProductStatusEnum.UP.getCode();
            }
            return true;
        }).findAny();
        if (productCartInfoOptional.isPresent()) {
            return Result.errorInfo(productCartInfoOptional.get().getProductName() + " 商品已下架");
        }
        // 判断库存
        List<Long> productSkuIdList = cartInfoList.stream().map(CartInfo::getProductSkuId).collect(Collectors.toList());
        List<ProductSkuInfo> productSkuInfoList = productSkuInfoService.listByIds(productSkuIdList);
        if (CollectionUtils.isEmpty(productSkuInfoList)) {
            return Result.errorInfo("商品库存信息不存在，提交订单失败");
        }
        Optional<CartInfo> skuCartInfoOptional = cartInfoList.stream().filter(cartInfo -> {
            Optional<ProductSkuInfo> productSkuInfoOptional = productSkuInfoList.stream().filter(productSkuInfo -> Objects.equals(productSkuInfo.getId(), cartInfo.getProductSkuId())).findAny();
            if (productSkuInfoOptional.isPresent()) {
                return productSkuInfoOptional.get().getStock() < cartInfo.getNumber();
            }
            return true;
        }).findAny();
        if (skuCartInfoOptional.isPresent()) {
            return Result.errorInfo(skuCartInfoOptional.get().getProductName() + " 商品已售罄");
        }
        // 判断商家
        List<Long> merchantIdList = cartInfoList.stream().map(CartInfo::getMerchantId).collect(Collectors.toList());
        List<MerchantUserInfo> merchantUserInfoList = merchantUserInfoService.listByIds(merchantIdList);
        if (CollectionUtils.isEmpty(merchantUserInfoList)) {
            return Result.errorInfo("商家信息不存在");
        }
        Optional<CartInfo> merchantCartInfoOptional = cartInfoList.stream().filter(cartInfo -> {
            Optional<MerchantUserInfo> merchantUserInfoOptional = merchantUserInfoList.stream().filter(merchantUserInfo -> Objects.equals(cartInfo.getMerchantId(), merchantUserInfo.getId())).findAny();
            if (merchantUserInfoOptional.isPresent()) {
                return merchantUserInfoOptional.get().getStatus() != MerchantStatusEnum.PASS.getCode();
            }
            return true;
        }).findAny();
        if (merchantCartInfoOptional.isPresent()) {
            return Result.errorInfo(merchantCartInfoOptional.get().getProductName() + " 的商品供应的商家已下线");
        }
        // 扣减库存
        Map<Long, CartInfo> skuCartInfoMap = cartInfoList.stream().collect(Collectors.toMap(CartInfo::getProductSkuId, Function.identity()));
        productSkuInfoList.forEach(skuInfo -> {
            CartInfo cartInfo = skuCartInfoMap.get(skuInfo.getId());
            if (cartInfo != null) {
                skuInfo.setStock(skuInfo.getStock() - cartInfo.getNumber());
                skuInfo.setUpdateTime(LocalDateTime.now());
            }
        });
        productSkuInfoService.saveOrUpdateBatch(productSkuInfoList);
        // 查询物流配置信息
        List<DeliveryFeeConfigVo> deliveryFeeConfigVoList = deliveryFeeConfigService.getMerchantDeliveryFeeConfigList(merchantIdList);
        Map<Long, DeliveryFeeConfigVo> deliveryFeeConfigVoMap = Maps.newHashMap();
        if (CollectionUtils.isNotEmpty(deliveryFeeConfigVoList)) {
            deliveryFeeConfigVoMap = deliveryFeeConfigVoList.stream().collect(Collectors.toMap(DeliveryFeeConfigVo::getMerchantId, Function.identity()));
        }
        // 生成订单
        Map<Long, List<CartInfo>> merchantCartInfoMap = cartInfoList.stream().collect(Collectors.groupingBy(CartInfo::getMerchantId));
        List<OrderInfo> orderInfoList = Lists.newArrayList();
        List<OrderDetailInfo> orderDetailInfoList = Lists.newArrayList();
        List<OrderProcessInfo> orderProcessInfoList = Lists.newArrayList();
        List<OrderProductVersion> orderProductVersionList = Lists.newArrayList();
        for (Map.Entry<Long, List<CartInfo>> entry : merchantCartInfoMap.entrySet()) {
            OrderInfo orderInfo = this.buildOrderInfo(param, entry.getValue(), deliveryFeeConfigVoMap.get(entry.getKey()));
            orderInfoList.add(orderInfo);
            orderDetailInfoList.addAll(this.buildOrderDetailInfoList(orderInfo, entry.getValue()));
            orderProcessInfoList.add(this.buildOrderProcessInfo(orderInfo));
            orderProductVersionList.addAll(this.buildOrderProductVersion(orderInfo, entry.getValue(), productInfoList, productSkuInfoList, merchantUserInfoList));
        }
        Boolean saveResult = transactionTemplate.execute(transactionStatus -> {
            try {
                this.saveOrUpdateBatch(orderInfoList);
                orderDetailInfoService.saveOrUpdateBatch(orderDetailInfoList);
                orderProcessInfoService.saveOrUpdateBatch(orderProcessInfoList);
                orderProductVersionService.saveOrUpdateBatch(orderProductVersionList);
                return Boolean.TRUE;
            } catch (Exception e) {
                transactionStatus.setRollbackOnly();
                log.error("提交订单保存数据发生异常，param:{},exception:", JSONUtil.toJsonStr(param), e);
                return Boolean.FALSE;
            }
        });
        // 删除购物车
        cartInfoService.removeByIds(param.getCartIdList());
        if (saveResult == null || !saveResult) {
            return Result.dealError();
        }
        List<String> orderNoList = orderInfoList.stream().map(OrderInfo::getOrderNo).collect(Collectors.toList());
        return Result.success(orderNoList);
    }

    @Override
    public Result<String> cancelOrder(CancelOrderParam param) {
        UserTokenInfoVo userTokenInfoVo = currentRequestComponent.getUserTokenInfo();
        if (userTokenInfoVo == null) {
            return Result.notLoginError();
        }
        OrderInfo orderInfo = this.getOne(new LambdaQueryWrapper<OrderInfo>().eq(OrderInfo::getOrderNo, param.getOrderNo()).last("limit 1"));
        if (orderInfo == null) {
            return Result.errorInfo("订单不存在");
        }
        orderInfo.setStatus(OrderStatusEnum.CANCEL.getCode());
        orderInfo.setUpdateTime(LocalDateTime.now());
        this.saveOrUpdate(orderInfo);
        OrderProcessInfo orderProcessInfo = OrderProcessInfo.builder()
                .userId(userTokenInfoVo.getUserId()).orderNo(orderInfo.getOrderNo()).label(ProcessLabelEnum.CANCEL.getDesc())
                .content("订单已取消").optUserName(userTokenInfoVo.getUserName()).build();
        orderProcessInfoService.saveOrUpdate(orderProcessInfo);
        // 还库存
        List<OrderDetailInfo> orderDetailInfoList = orderDetailInfoService.list(new LambdaQueryWrapper<OrderDetailInfo>().eq(OrderDetailInfo::getOrderNo, orderInfo.getOrderNo()));
        if (CollectionUtils.isEmpty(orderDetailInfoList)) {
            return Result.dealSuccess();
        }
        List<Long> skuIdList = orderDetailInfoList.stream().map(OrderDetailInfo::getProductSkuId).collect(Collectors.toList());
        List<ProductSkuInfo> productSkuInfoList = productSkuInfoService.listByIds(skuIdList);
        Map<Long, OrderDetailInfo> orderDetailInfoMap = orderDetailInfoList.stream().collect(Collectors.toMap(OrderDetailInfo::getProductSkuId, Function.identity()));
        productSkuInfoList.forEach(sku -> {
            OrderDetailInfo orderDetailInfo = orderDetailInfoMap.get(sku.getId());
            if (orderDetailInfo != null) {
                sku.setStock(sku.getStock() + orderDetailInfo.getBuyAmount());
                sku.setUpdateTime(LocalDateTime.now());
            }
        });
        productSkuInfoService.saveOrUpdateBatch(productSkuInfoList);
        return Result.dealSuccess();
    }

    @Override
    public Result<String> delOrder(DelOrderParam param) {
        UserTokenInfoVo userTokenInfoVo = currentRequestComponent.getUserTokenInfo();
        if (userTokenInfoVo == null) {
            return Result.notLoginError();
        }
        this.remove(new LambdaQueryWrapper<OrderInfo>().eq(OrderInfo::getOrderNo, param.getOrderNo()));
        orderDetailInfoService.remove(new LambdaQueryWrapper<OrderDetailInfo>().eq(OrderDetailInfo::getOrderNo, param.getOrderNo()));
        OrderProcessInfo orderProcessInfo = OrderProcessInfo.builder()
                .userId(userTokenInfoVo.getUserId()).orderNo(param.getOrderNo()).label(ProcessLabelEnum.DEL.getDesc())
                .content("用户删除订单").optUserName(userTokenInfoVo.getUserName()).build();
        orderProcessInfoService.saveOrUpdate(orderProcessInfo);
        return Result.dealSuccess();
    }

    @Override
    public Result<String> closeOrder() {
        List<OrderInfo> orderInfoList = this.list(new LambdaQueryWrapper<OrderInfo>().eq(OrderInfo::getStatus, OrderStatusEnum.WAIT_PAY.getCode()).lt(OrderInfo::getCreateTime, LocalDateTime.now().minusHours(CommonConstant.PAY_TIME_OUT_HOURS)));
        if (CollectionUtils.isEmpty(orderInfoList)) {
            return Result.dealSuccess();
        }
        List<OrderProcessInfo> orderProcessInfoList = Lists.newArrayList();
        orderInfoList.forEach(orderInfo -> {
            orderInfo.setStatus(OrderStatusEnum.CLOSE.getCode());
            orderInfo.setUpdateTime(LocalDateTime.now());
            OrderProcessInfo orderProcessInfo = OrderProcessInfo.builder()
                    .userId(0L).orderNo(orderInfo.getOrderNo()).label(ProcessLabelEnum.CLOSE.getDesc())
                    .content("超时未支付，订单自动关闭").optUserName("系统").build();
            orderProcessInfoList.add(orderProcessInfo);
        });
        this.saveOrUpdateBatch(orderInfoList);
        orderProcessInfoService.saveOrUpdateBatch(orderProcessInfoList);
        // 释放库存
        List<String> orderNoList = orderInfoList.stream().map(OrderInfo::getOrderNo).collect(Collectors.toList());
        List<OrderDetailInfo> orderDetailInfoList = orderDetailInfoService.list(new LambdaQueryWrapper<OrderDetailInfo>().in(OrderDetailInfo::getOrderNo, orderNoList));
        if (CollectionUtils.isEmpty(orderDetailInfoList)) {
            return Result.dealSuccess();
        }
        List<Long> skuIdList = orderDetailInfoList.stream().map(OrderDetailInfo::getProductSkuId).distinct().collect(Collectors.toList());
        List<ProductSkuInfo> productSkuInfoList = productSkuInfoService.listByIds(skuIdList);
        Map<Long, List<OrderDetailInfo>> orderDetailInfoMap = orderDetailInfoList.stream().collect(Collectors.groupingBy(OrderDetailInfo::getProductSkuId));
        productSkuInfoList.forEach(sku -> {
            List<OrderDetailInfo> detailInfoList = orderDetailInfoMap.get(sku.getId());
            if (CollectionUtils.isNotEmpty(detailInfoList)) {
                int totalBuyAmount = detailInfoList.stream().collect(Collectors.summingInt(OrderDetailInfo::getBuyAmount));
                sku.setStock(sku.getStock() + totalBuyAmount);
                sku.setUpdateTime(LocalDateTime.now());
            }
        });
        productSkuInfoService.saveOrUpdateBatch(productSkuInfoList);
        return Result.dealSuccess();
    }

    @Override
    public Result<Page<OrderInfoVo>> getOrderList(OrderListParam param) {
        UserTokenInfoVo userTokenInfoVo = currentRequestComponent.getUserTokenInfo();
        if (userTokenInfoVo == null) {
            return Result.notLoginError();
        }
        if (Objects.equals(userTokenInfoVo.getUserType(), CommonConstant.MERCHANT_USER_TYPE)) {
            param.setMerchantId(userTokenInfoVo.getUserId());
        } else if (Objects.equals(userTokenInfoVo.getUserType(), CommonConstant.MALL_USER_TYPE)) {
            param.setUserId(userTokenInfoVo.getUserId());
        }
        Page<OrderInfoVo> orderInfoVoPage = baseMapper.getOrderListPage(new Page<>(param.getCurrentPage(), param.getSize()), param);
        if (orderInfoVoPage == null || CollectionUtils.isEmpty(orderInfoVoPage.getRecords())) {
            return Result.noData();
        }
        orderInfoVoPage.getRecords().forEach(orderInfoVo -> {
            orderInfoVo.setStatusStr(OrderStatusEnum.getDesc(orderInfoVo.getStatus()));
            orderInfoVo.setOrderTypeStr(OrderTypeEnum.getDesc(orderInfoVo.getOrderType()));
            orderInfoVo.setDeliveryTypeStr(DeliveryTypeEnum.getDesc(orderInfoVo.getDeliveryType()));
        });
        return Result.success(orderInfoVoPage);
    }

    @Override
    public Result<OrderDetailVo> getOrderDetail(String orderNo) {
        if (StringUtils.isBlank(orderNo)) {
            return Result.paramError();
        }
        OrderInfo orderInfo = this.getOne(new LambdaQueryWrapper<OrderInfo>().eq(OrderInfo::getOrderNo, orderNo).last("limit 1"));
        if (orderInfo == null) {
            return Result.noData();
        }
        OrderDetailVo orderDetailVo = new OrderDetailVo();
        BeanUtils.copyProperties(orderInfo, orderDetailVo);
        orderDetailVo.setStatusStr(OrderStatusEnum.getDesc(orderInfo.getStatus()));
        orderDetailVo.setOrderTypeStr(OrderTypeEnum.getDesc(orderInfo.getOrderType()));
        orderDetailVo.setDeliveryTypeStr(DeliveryTypeEnum.getDesc(orderInfo.getDeliveryType()));
        // 查询用户信息
        UserInfo userInfo = userInfoService.getById(orderInfo.getUserId());
        if (userInfo != null) {
            orderDetailVo.setUserName(userInfo.getUserName());
        }
        // 查询商家信息
        MerchantUserInfo merchantUserInfo = merchantUserInfoService.getById(orderInfo.getMerchantId());
        if (merchantUserInfo != null) {
            orderDetailVo.setMerchantName(merchantUserInfo.getMerchantName());
        }
        // 查询用户收货地址
        UserAddress userAddress = userAddressService.getById(orderInfo.getReceiveAddressId());
        if (userAddress != null) {
            UserAddressVo userAddressVo = new UserAddressVo();
            BeanUtils.copyProperties(userAddress, userAddressVo);
            orderDetailVo.setUserAddressVo(userAddressVo);
        }
        // 查询购买商品列表
        List<OrderProductVersion> orderProductVersionList = orderProductVersionService.list(new LambdaQueryWrapper<OrderProductVersion>().eq(OrderProductVersion::getOrderNo, orderNo));
        if (CollectionUtils.isEmpty(orderProductVersionList)) {
            return Result.success(orderDetailVo);
        }
        List<OrderProductVo> orderProductList = Lists.newArrayList();
        orderProductVersionList.forEach(orderProductVersion -> {
            OrderProductVo orderProduct = new OrderProductVo();
            BeanUtils.copyProperties(orderProductVersion, orderProduct);
            orderProductList.add(orderProduct);
        });
        orderDetailVo.setOrderProductList(orderProductList);
        // 查询订单进程
        List<OrderProcessVo> orderProcessVoList = this.getOrderProcessInfo(orderNo);
        orderDetailVo.setOrderProcessVoList(orderProcessVoList);
        return Result.success(orderDetailVo);
    }

    @Override
    public List<OrderProcessVo> getOrderProcessInfo(String orderNo) {
        List<OrderProcessInfo> orderProcessInfoList = orderProcessInfoService.list(new LambdaQueryWrapper<OrderProcessInfo>().eq(OrderProcessInfo::getOrderNo, orderNo));
        if (CollectionUtils.isEmpty(orderProcessInfoList)) {
            return Collections.emptyList();
        }
        List<OrderProcessVo> orderProcessVoList = orderProcessInfoList.stream().map(process -> {
            OrderProcessVo orderProcessVo = new OrderProcessVo();
            BeanUtils.copyProperties(process, orderProcessVo);
            return orderProcessVo;
        }).collect(Collectors.toList());
        return orderProcessVoList;
    }

    @Override
    public Result<String> orderDelivery(OrderDeliveryParam param) {
        UserTokenInfoVo userTokenInfoVo = currentRequestComponent.getUserTokenInfo();
        if (userTokenInfoVo == null) {
            return Result.notLoginError();
        }
        if (!Objects.equals(userTokenInfoVo.getUserType(), CommonConstant.MERCHANT_USER_TYPE)) {
            return Result.noAuth();
        }
        OrderInfo orderInfo = this.getOne(new LambdaQueryWrapper<OrderInfo>().eq(OrderInfo::getOrderNo, param.getOrderNo()));
        if (orderInfo == null) {
            return Result.errorInfo("订单不存在");
        }
        // 记录订单进程
        OrderProcessInfo orderProcessInfo = OrderProcessInfo.builder()
                .userId(userTokenInfoVo.getUserId()).orderNo(param.getOrderNo()).label(ProcessLabelEnum.DELIVERY.getDesc())
                .content("订单发货，物流单号：" + param.getDeliveryNo()).optUserName(userTokenInfoVo.getUserName()).build();
        orderProcessInfoService.saveOrUpdate(orderProcessInfo);
        // 修改订单数据
        orderInfo.setDeliveryCompany(param.getDeliveryCompany());
        orderInfo.setDeliveryNo(param.getDeliveryNo());
        orderInfo.setStatus(OrderStatusEnum.HAS_DELIVERY.getCode());
        orderInfo.setDeliveryTime(LocalDateTime.now());
        orderInfo.setUpdateTime(LocalDateTime.now());
        boolean saveResult = this.saveOrUpdate(orderInfo);
        if (!saveResult) {
            return Result.dealError();
        }
        return Result.dealSuccess();
    }

    @Override
    public Result<String> orderReceive(String orderNo) {
        UserTokenInfoVo userTokenInfoVo = currentRequestComponent.getUserTokenInfo();
        if (userTokenInfoVo == null) {
            return Result.notLoginError();
        }
        if (!Objects.equals(userTokenInfoVo.getUserType(), CommonConstant.MALL_USER_TYPE)) {
            return Result.noAuth();
        }
        OrderInfo orderInfo = this.getOne(new LambdaQueryWrapper<OrderInfo>().eq(OrderInfo::getOrderNo, orderNo));
        if (orderInfo == null) {
            return Result.errorInfo("订单不存在");
        }
        // 记录订单进程
        OrderProcessInfo orderProcessInfo = OrderProcessInfo.builder()
                .userId(userTokenInfoVo.getUserId()).orderNo(orderNo).label(ProcessLabelEnum.COMPLETE.getDesc())
                .content("用户确认收货，订单完成").optUserName(userTokenInfoVo.getUserName()).build();
        orderProcessInfoService.saveOrUpdate(orderProcessInfo);
        // 修改订单数据
        orderInfo.setStatus(OrderStatusEnum.COMPLETE.getCode());
        orderInfo.setReceiveTime(LocalDateTime.now());
        orderInfo.setUpdateTime(LocalDateTime.now());
        boolean saveResult = this.saveOrUpdate(orderInfo);
        if (!saveResult) {
            return Result.dealError();
        }
        return Result.dealSuccess();
    }

    @Override
    public Result<String> orderAutoComplete() {
        List<OrderInfo> orderInfoList = this.list(new LambdaQueryWrapper<OrderInfo>().eq(OrderInfo::getStatus, OrderStatusEnum.HAS_DELIVERY.getCode()).lt(OrderInfo::getDeliveryTime, LocalDateTime.now().minusDays(CommonConstant.RECEIVE_TIME_OUT_DAYS)));
        if (CollectionUtils.isEmpty(orderInfoList)) {
            return Result.dealSuccess();
        }
        List<OrderProcessInfo> orderProcessInfoList = Lists.newArrayList();
        orderInfoList.forEach(orderInfo -> {
            orderInfo.setStatus(OrderStatusEnum.COMPLETE.getCode());
            orderInfo.setReceiveTime(LocalDateTime.now());
            orderInfo.setUpdateTime(LocalDateTime.now());
            OrderProcessInfo orderProcessInfo = OrderProcessInfo.builder()
                    .userId(0L).orderNo(orderInfo.getOrderNo()).label(ProcessLabelEnum.COMPLETE.getDesc())
                    .content("超时未确认收货，系统自动收货，订单完成").optUserName("系统").build();
            orderProcessInfoList.add(orderProcessInfo);
        });
        this.saveOrUpdateBatch(orderInfoList);
        orderProcessInfoService.saveOrUpdateBatch(orderProcessInfoList);
        return Result.dealSuccess();
    }

    @Override
    public Result<CashierInfoVo> getCashierInfo(List<String> orderNoList) {
        if (CollectionUtils.isEmpty(orderNoList)) {
            return Result.paramError();
        }
        List<OrderInfo> orderInfoList = this.list(Wrappers.<OrderInfo>lambdaQuery().in(OrderInfo::getOrderNo, orderNoList));
        if (CollectionUtils.isEmpty(orderInfoList)) {
            return Result.errorInfo("订单不存在");
        }
        List<Long> merchantIdList = orderInfoList.stream().map(OrderInfo::getMerchantId).collect(Collectors.toList());
        List<MerchantUserInfo> merchantUserInfoList = merchantUserInfoService.listByIds(merchantIdList);
        if (CollectionUtils.isEmpty(merchantUserInfoList)) {
            return Result.errorInfo("供应商不存在");
        }
        Map<Long, MerchantUserInfo> merchantUserInfoMap = merchantUserInfoList.stream().collect(Collectors.toMap(MerchantUserInfo::getId, Function.identity()));
        CashierInfoVo cashierInfoVo = new CashierInfoVo();
        List<CashierInfoVo.CashierInfo> cashierInfoList = Lists.newArrayList();
        orderInfoList.forEach(orderInfo -> {
            CashierInfoVo.CashierInfo cashierInfo = new CashierInfoVo.CashierInfo();
            cashierInfo.setOrderNo(orderInfo.getOrderNo());
            cashierInfo.setOrderPrice(orderInfo.getTotalPrice());
            if (orderInfo.getDeliveryPrice() != null) {
                cashierInfo.setOrderPrice(orderInfo.getTotalPrice().add(orderInfo.getDeliveryPrice()));
            }
            cashierInfo.setMerchantId(orderInfo.getMerchantId());
            MerchantUserInfo merchantUserInfo = merchantUserInfoMap.get(orderInfo.getMerchantId());
            if (merchantUserInfo != null) {
                cashierInfo.setMerchantName(merchantUserInfo.getMerchantName());
            }
            cashierInfo.setEndTime(orderInfo.getCreateTime().plusHours(CommonConstant.PAY_TIME_OUT_HOURS));
            cashierInfoList.add(cashierInfo);
        });
        cashierInfoVo.setCashierInfoList(cashierInfoList);
        BigDecimal orderTotalPrice = cashierInfoList.stream().map(CashierInfoVo.CashierInfo::getOrderPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        cashierInfoVo.setTotalPrice(orderTotalPrice);
        return Result.success(cashierInfoVo);
    }

    private List<OrderProductVersion> buildOrderProductVersion(OrderInfo orderInfo, List<CartInfo> cartInfoList, List<ProductInfo> productInfoList, List<ProductSkuInfo> productSkuInfoList, List<MerchantUserInfo> merchantUserInfoList) {
        List<OrderProductVersion> list = Lists.newArrayList();
        for (CartInfo cartInfo : cartInfoList) {
            OrderProductVersion orderProductVersion = new OrderProductVersion();
            orderProductVersion.setUserId(cartInfo.getUserId());
            orderProductVersion.setOrderNo(orderInfo.getOrderNo());
            orderProductVersion.setProductId(cartInfo.getProductId());
            orderProductVersion.setProductSkuId(cartInfo.getProductSkuId());
            orderProductVersion.setMerchantId(cartInfo.getMerchantId());
            orderProductVersion.setProductPrice(cartInfo.getPrice());
            orderProductVersion.setBuyAmount(cartInfo.getNumber());
            Optional<ProductInfo> productInfoOptional = productInfoList.stream().filter(productInfo -> Objects.equals(cartInfo.getProductId(), productInfo.getId())).findAny();
            if (productInfoOptional.isPresent()) {
                orderProductVersion.setProductBarCode(productInfoOptional.get().getBarCode());
                orderProductVersion.setProductName(productInfoOptional.get().getName());
            }
            Optional<ProductSkuInfo> productSkuInfoOptional = productSkuInfoList.stream().filter(sku -> Objects.equals(cartInfo.getProductSkuId(), sku.getId())).findAny();
            if (productSkuInfoOptional.isPresent()) {
                orderProductVersion.setProductSpec(productSkuInfoOptional.get().getSpec());
            }
            Optional<MerchantUserInfo> merchantUserInfoOptional = merchantUserInfoList.stream().filter(merchantUserInfo -> Objects.equals(cartInfo.getMerchantId(), merchantUserInfo.getId())).findAny();
            if (merchantUserInfoOptional.isPresent()) {
                orderProductVersion.setMerchantName(merchantUserInfoOptional.get().getMerchantName());
            }
            list.add(orderProductVersion);
        }
        return list;
    }

    private OrderProcessInfo buildOrderProcessInfo(OrderInfo orderInfo) {
        OrderProcessInfo orderProcessInfo = new OrderProcessInfo();
        orderProcessInfo.setUserId(orderInfo.getUserId());
        orderProcessInfo.setOrderNo(orderInfo.getOrderNo());
        orderProcessInfo.setLabel(ProcessLabelEnum.CREATE.getDesc());
        orderProcessInfo.setContent("用户提交订单，订单号：" + orderInfo.getOrderNo());
        orderProcessInfo.setOptUserName(currentRequestComponent.getLoginUserName(CommonConstant.MALL_USER_TYPE));
        return orderProcessInfo;
    }

    private List<OrderDetailInfo> buildOrderDetailInfoList(OrderInfo orderInfo, List<CartInfo> cartInfoList) {
        List<OrderDetailInfo> orderDetailInfoList = cartInfoList.stream().map(cartInfo -> {
            OrderDetailInfo orderDetailInfo = new OrderDetailInfo();
            orderDetailInfo.setUserId(cartInfo.getUserId());
            orderDetailInfo.setOrderNo(orderInfo.getOrderNo());
            orderDetailInfo.setProductId(cartInfo.getProductId());
            orderDetailInfo.setProductSkuId(cartInfo.getProductSkuId());
            orderDetailInfo.setMerchantId(cartInfo.getMerchantId());
            orderDetailInfo.setProductPrice(cartInfo.getPrice());
            orderDetailInfo.setBuyAmount(cartInfo.getNumber());
            return orderDetailInfo;
        }).collect(Collectors.toList());
        return orderDetailInfoList;
    }

    private OrderInfo buildOrderInfo(CommitOrderParam param, List<CartInfo> cartInfoList, DeliveryFeeConfigVo deliveryFeeConfigVo) {
        BigDecimal totalPrice = new BigDecimal(0);
        for (CartInfo cartInfo : cartInfoList) {
            totalPrice = totalPrice.add(cartInfo.getPrice().multiply(new BigDecimal(cartInfo.getNumber())));
        }
        BigDecimal deliveryFee = BigDecimal.ZERO;
        if (deliveryFeeConfigVo != null) {
            if (deliveryFeeConfigVo.getMinOrderMoney() != null && totalPrice.compareTo(deliveryFeeConfigVo.getMinOrderMoney()) < 0) {
                deliveryFee = deliveryFeeConfigVo.getDeliveryMoney();
            }
        }
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setUserId(cartInfoList.get(0).getUserId());
        orderInfo.setMerchantId(cartInfoList.get(0).getMerchantId());
        orderInfo.setOrderNo(GenerateNoUtil.generateTradeNo());
        orderInfo.setTotalPrice(totalPrice);
        orderInfo.setRealPrice(totalPrice);
        orderInfo.setDeliveryPrice(deliveryFee);
        orderInfo.setSourceType(param.getSourceType());
        orderInfo.setStatus(OrderStatusEnum.WAIT_PAY.getCode());
        orderInfo.setOrderType(param.getOrderType());
        orderInfo.setDeliveryType(param.getDeliveryType());
        orderInfo.setReceiveAddressId(param.getReceiveAddressId());
        orderInfo.setReserveTime(param.getReserveTime());
        orderInfo.setNote(param.getNote());
        return orderInfo;
    }
}
