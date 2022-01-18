package com.yidian.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yidian.shop.component.CurrentRequestComponent;
import com.yidian.shop.constant.CommonConstant;
import com.yidian.shop.entity.*;
import com.yidian.shop.enums.*;
import com.yidian.shop.mapper.RefundInfoMapper;
import com.yidian.shop.merchant.entity.MerchantUserInfo;
import com.yidian.shop.merchant.service.MerchantUserInfoService;
import com.yidian.shop.param.*;
import com.yidian.shop.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yidian.shop.vo.*;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 订单退货申请 服务实现类
 * </p>
 *
 * @author dev
 * @since 2021-08-26
 */
@Service
public class RefundInfoServiceImpl extends ServiceImpl<RefundInfoMapper, RefundInfo> implements RefundInfoService {

    @Resource
    private RefundReasonTagService refundReasonTagService;
    @Resource
    private OrderInfoService orderInfoService;
    @Resource
    private CurrentRequestComponent currentRequestComponent;
    @Resource
    private OrderProductVersionService orderProductVersionService;
    @Resource
    private ProductSkuInfoService productSkuInfoService;
    @Resource
    private OrderProcessInfoService orderProcessInfoService;
    @Resource
    private MerchantUserInfoService merchantUserInfoService;

    @Override
    public Result<List<RefundReasonTagVo>> getRefundReasonTagInfo() {
        List<RefundReasonTag> refundReasonTagList = refundReasonTagService.list();
        if (CollectionUtils.isEmpty(refundReasonTagList)) {
            return Result.noData();
        }
        List<RefundReasonTagVo> refundReasonTagVoList = refundReasonTagList.stream().map(tag -> {
            RefundReasonTagVo refundReasonTagVo = new RefundReasonTagVo();
            BeanUtils.copyProperties(tag, refundReasonTagVo);
            return refundReasonTagVo;
        }).collect(Collectors.toList());
        return Result.success(refundReasonTagVoList);
    }

    @Override
    public Result<String> refundApply(RefundApplyParam param) {
        UserTokenInfoVo userTokenInfoVo = currentRequestComponent.getUserTokenInfo();
        if (userTokenInfoVo == null) {
            return Result.notLoginError();
        }
        List<RefundInfo> refundInfoList = this.list(new LambdaQueryWrapper<RefundInfo>().eq(RefundInfo::getOrderNo, param.getOrderNo()).eq(RefundInfo::getProductId, param.getProductId()));
        if (CollectionUtils.isNotEmpty(refundInfoList)) {
            Optional<RefundInfo> refundInfoOptional = refundInfoList.stream().filter(refundInfo -> refundInfo.getStatus() == RefundStatusEnum.WAIT_DEAL.getCode() || refundInfo.getStatus() == RefundStatusEnum.REFUND_BACKING.getCode()).findAny();
            if (refundInfoOptional.isPresent()) {
                return Result.errorInfo("您有进行中的退款申请，不能重复提交");
            }
        }
        OrderInfo orderInfo = orderInfoService.getOne(new LambdaQueryWrapper<OrderInfo>().eq(OrderInfo::getOrderNo, param.getOrderNo()));
        if (orderInfo == null) {
            return Result.errorInfo("订单不存在");
        }
        if (orderInfo.getStatus() != OrderStatusEnum.WAIT_DELIVERY.getCode() && orderInfo.getStatus() != OrderStatusEnum.COMPLETE.getCode()) {
            return Result.errorInfo("订单暂不允许申请退款");
        }
        OrderProductVersion orderProductVersion = orderProductVersionService.getOne(new LambdaQueryWrapper<OrderProductVersion>().eq(OrderProductVersion::getOrderNo, param.getOrderNo()).eq(OrderProductVersion::getProductId, param.getProductId()));
        if (orderProductVersion == null) {
            return Result.errorInfo("申请退款商品不存在");
        }
        // 记录订单进程
        OrderProcessInfo orderProcessInfo = OrderProcessInfo.builder()
                .userId(userTokenInfoVo.getUserId()).orderNo(orderInfo.getOrderNo()).label(ProcessLabelEnum.REFUND_APPLY.getDesc())
                .content("用户申请退款，退款方式：" + (orderInfo.getStatus() == OrderStatusEnum.WAIT_DELIVERY.getCode() ? RefundTypeEnum.REFUND_MONEY.getDesc() : RefundTypeEnum.REFUND_GOODS.getDesc()))
                .optUserName(userTokenInfoVo.getUserName()).build();
        orderProcessInfoService.saveOrUpdate(orderProcessInfo);
        // 生成退款记录
        RefundInfo refundInfo = new RefundInfo();
        BeanUtils.copyProperties(param, refundInfo);
        refundInfo.setUserId(userTokenInfoVo.getUserId());
        refundInfo.setUserName(userTokenInfoVo.getUserName());
        if (orderInfo.getStatus() == OrderStatusEnum.WAIT_DELIVERY.getCode()) {
            refundInfo.setRefundType(RefundTypeEnum.REFUND_MONEY.getCode());
        } else {
            refundInfo.setRefundType(RefundTypeEnum.REFUND_GOODS.getCode());
        }
        refundInfo.setRefundAmount(orderProductVersion.getProductPrice().multiply(new BigDecimal(orderProductVersion.getBuyAmount())));
        boolean saveResult = this.saveOrUpdate(refundInfo);
        if (!saveResult) {
            return Result.dealError();
        }
        return Result.dealSuccess();
    }

    @Override
    public Result<String> refundCancel(Long refundId) {
        UserTokenInfoVo userTokenInfoVo = currentRequestComponent.getUserTokenInfo();
        if (userTokenInfoVo == null) {
            return Result.notLoginError();
        }
        RefundInfo refundInfo = this.getById(refundId);
        if (refundInfo == null) {
            return Result.errorInfo("退款记录不存在");
        }
        // 记录订单进程
        OrderProcessInfo orderProcessInfo = OrderProcessInfo.builder()
                .userId(userTokenInfoVo.getUserId()).orderNo(refundInfo.getOrderNo()).label(ProcessLabelEnum.REFUND_CANCEL.getDesc())
                .content("用户取消退款申请").optUserName(userTokenInfoVo.getUserName()).build();
        orderProcessInfoService.saveOrUpdate(orderProcessInfo);
        // 修改退款单状态
        refundInfo.setStatus(RefundStatusEnum.CANCEL.getCode());
        refundInfo.setUpdateTime(LocalDateTime.now());
        boolean saveResult = this.saveOrUpdate(refundInfo);
        if (!saveResult) {
            return Result.dealError();
        }
        return Result.dealSuccess();
    }

    @Override
    public Result<String> refundExamine(RefundExamineParam param) {
        UserTokenInfoVo userTokenInfoVo = currentRequestComponent.getUserTokenInfo();
        if (userTokenInfoVo == null) {
            return Result.notLoginError();
        }
        if (!Objects.equals(userTokenInfoVo.getUserType(), CommonConstant.ADMIN_USER_TYPE) && !Objects.equals(userTokenInfoVo.getUserType(), CommonConstant.MERCHANT_USER_TYPE)) {
            return Result.noAuth();
        }
        RefundInfo refundInfo = this.getById(param.getRefundId());
        if (refundInfo == null) {
            return Result.errorInfo("退款订单不存在");
        }
        if (Objects.equals(userTokenInfoVo.getUserType(), CommonConstant.MERCHANT_USER_TYPE)) {
            if (!Objects.equals(userTokenInfoVo.getUserId(), refundInfo.getMerchantId())) {
                return Result.noAuth();
            }
        }
        OrderInfo orderInfo = orderInfoService.getOne(new LambdaQueryWrapper<OrderInfo>().eq(OrderInfo::getOrderNo, refundInfo.getOrderNo()));
        if (orderInfo == null) {
            return Result.errorInfo("订单不存在");
        }
        OrderProductVersion orderProductVersion = orderProductVersionService.getOne(new LambdaQueryWrapper<OrderProductVersion>().eq(OrderProductVersion::getOrderNo, orderInfo.getOrderNo()).eq(OrderProductVersion::getProductId, refundInfo.getProductId()));
        if (orderProductVersion == null) {
            return Result.errorInfo("退款商品购买记录不存在");
        }
        // 审核拒绝
        if (Objects.equals(param.getExamineType(), RefundExamineEnum.REFUSE.getCode())) {
            // 记录订单进程
            OrderProcessInfo orderProcessInfo = OrderProcessInfo.builder()
                    .userId(userTokenInfoVo.getUserId()).orderNo(refundInfo.getOrderNo()).label(ProcessLabelEnum.REFUND_EXAMINE.getDesc())
                    .content("退款审核拒绝").optUserName(userTokenInfoVo.getUserName()).build();
            orderProcessInfoService.saveOrUpdate(orderProcessInfo);
            // 修改退款单状态
            refundInfo.setStatus(RefundStatusEnum.REFUSE.getCode());
            refundInfo.setRemark(param.getRemark());
            refundInfo.setUpdateTime(LocalDateTime.now());
            this.saveOrUpdate(refundInfo);
            return Result.dealSuccess();
        }
        // 审核通过
        OrderProcessInfo orderProcessInfo = OrderProcessInfo.builder()
                .userId(userTokenInfoVo.getUserId()).orderNo(refundInfo.getOrderNo()).label(ProcessLabelEnum.REFUND_EXAMINE.getDesc())
                .content("退款审核通过").optUserName(userTokenInfoVo.getUserName()).build();
        orderProcessInfoService.saveOrUpdate(orderProcessInfo);
        if (Objects.equals(refundInfo.getRefundType(), RefundTypeEnum.REFUND_MONEY.getCode()) && Objects.equals(orderInfo.getStatus(), OrderStatusEnum.WAIT_DELIVERY.getCode())) {
            // TODO 待发货，直接退款

            // 修改退款单状态
            refundInfo.setStatus(RefundStatusEnum.COMPLETE.getCode());
            refundInfo.setUpdateTime(LocalDateTime.now());
            this.saveOrUpdate(refundInfo);
            // 修改订单状态
            orderInfo.setStatus(OrderStatusEnum.COMPLETE.getCode());
            orderInfo.setUpdateTime(LocalDateTime.now());
            orderInfoService.saveOrUpdate(orderInfo);
            // 还库存
            ProductSkuInfo productSkuInfo = productSkuInfoService.getById(orderProductVersion.getProductSkuId());
            if (productSkuInfo != null) {
                productSkuInfo.setStock(productSkuInfo.getStock() + orderProductVersion.getBuyAmount());
                productSkuInfo.setUpdateTime(LocalDateTime.now());
                productSkuInfoService.saveOrUpdate(productSkuInfo);
            }
        } else if (Objects.equals(refundInfo.getRefundType(), RefundTypeEnum.REFUND_GOODS.getCode()) && orderInfo.getStatus() == OrderStatusEnum.COMPLETE.getCode()) {
            // 修改退款单状态
            refundInfo.setStatus(RefundStatusEnum.REFUND_BACKING.getCode());
            refundInfo.setUpdateTime(LocalDateTime.now());
            this.saveOrUpdate(refundInfo);
        } else {
            return Result.errorInfo("退款类型错误");
        }
        return Result.dealSuccess();
    }

    @Override
    public Result<String> refundBack(RefundBackParam param) {
        UserTokenInfoVo userTokenInfoVo = currentRequestComponent.getUserTokenInfo();
        if (userTokenInfoVo == null) {
            return Result.notLoginError();
        }
        if (!Objects.equals(userTokenInfoVo.getUserType(), CommonConstant.MALL_USER_TYPE)) {
            return Result.noAuth();
        }
        RefundInfo refundInfo = this.getById(param.getRefundId());
        if (refundInfo == null) {
            return Result.errorInfo("退款订单不存在");
        }
        if (!Objects.equals(refundInfo.getUserId(), userTokenInfoVo.getUserId())) {
            return Result.noAuth();
        }
        // 订单进程
        OrderProcessInfo orderProcessInfo = OrderProcessInfo.builder()
                .userId(userTokenInfoVo.getUserId()).orderNo(refundInfo.getOrderNo()).label(ProcessLabelEnum.REFUND_BACK.getDesc())
                .content("用户寄回退款商品").optUserName(userTokenInfoVo.getUserName()).build();
        orderProcessInfoService.saveOrUpdate(orderProcessInfo);
        // 更新退款单
        refundInfo.setRefundDeliveryCompany(param.getRefundDeliveryCompany());
        refundInfo.setRefundDeliveryNo(param.getRefundDeliveryNo());
        refundInfo.setUpdateTime(LocalDateTime.now());
        boolean saveResult = this.saveOrUpdate(refundInfo);
        if (!saveResult) {
            return Result.dealError();
        }
        return Result.dealSuccess();
    }

    @Override
    public Result<String> refundReceive(RefundReceiveParam param) {
        UserTokenInfoVo userTokenInfoVo = currentRequestComponent.getUserTokenInfo();
        if (userTokenInfoVo == null) {
            return Result.notLoginError();
        }
        if (!Objects.equals(userTokenInfoVo.getUserType(), CommonConstant.MERCHANT_USER_TYPE)) {
            return Result.noAuth();
        }
        RefundInfo refundInfo = this.getById(param.getRefundId());
        if (refundInfo == null) {
            return Result.errorInfo("退款订单不存在");
        }
        if (!Objects.equals(refundInfo.getMerchantId(), userTokenInfoVo.getUserId())) {
            return Result.noAuth();
        }
        // 订单进程
        OrderProcessInfo orderProcessInfo = OrderProcessInfo.builder()
                .userId(userTokenInfoVo.getUserId()).orderNo(refundInfo.getOrderNo()).label(ProcessLabelEnum.REFUND_RECEIVE.getDesc())
                .content("商家确认收货用户寄回退款商品").optUserName(userTokenInfoVo.getUserName()).build();
        orderProcessInfoService.saveOrUpdate(orderProcessInfo);
        // 拒绝退款
        if (Objects.equals(param.getOptType(), RefundExamineEnum.REFUSE.getCode())) {
            // 修改退款单状态
            refundInfo.setStatus(RefundStatusEnum.REFUSE.getCode());
            refundInfo.setRemark(param.getRemark());
            refundInfo.setUpdateTime(LocalDateTime.now());
            this.saveOrUpdate(refundInfo);
            return Result.dealSuccess();
        }
        // TODO 同意退款 调用退款接口

        // 修改退款单状态
        refundInfo.setStatus(RefundStatusEnum.COMPLETE.getCode());
        refundInfo.setUpdateTime(LocalDateTime.now());
        this.saveOrUpdate(refundInfo);
        // 还库存
        OrderProductVersion orderProductVersion = orderProductVersionService.getOne(new LambdaQueryWrapper<OrderProductVersion>().eq(OrderProductVersion::getOrderNo, refundInfo.getOrderNo()).eq(OrderProductVersion::getProductId, refundInfo.getProductId()));
        if (orderProductVersion == null) {
            return Result.dealSuccess();
        }
        ProductSkuInfo productSkuInfo = productSkuInfoService.getById(orderProductVersion.getProductSkuId());
        if (productSkuInfo != null) {
            productSkuInfo.setStock(productSkuInfo.getStock() + orderProductVersion.getBuyAmount());
            productSkuInfo.setUpdateTime(LocalDateTime.now());
            productSkuInfoService.saveOrUpdate(productSkuInfo);
        }
        return Result.dealSuccess();
    }

    @Override
    public Result<Page<RefundInfoVo>> getRefundList(RefundListParam param) {
        UserTokenInfoVo userTokenInfoVo = currentRequestComponent.getUserTokenInfo();
        if (userTokenInfoVo == null) {
            return Result.notLoginError();
        }
        if (Objects.equals(userTokenInfoVo.getUserType(), CommonConstant.MERCHANT_USER_TYPE)) {
            param.setMerchantId(userTokenInfoVo.getUserId());
        } else if (Objects.equals(userTokenInfoVo.getUserType(), CommonConstant.MALL_USER_TYPE)) {
            param.setUserId(userTokenInfoVo.getUserId());
        }
        Page<RefundInfoVo> refundInfoVoPage = baseMapper.getRefundList(new Page<>(param.getCurrentPage(), param.getSize()), param);
        if (refundInfoVoPage == null || CollectionUtils.isEmpty(refundInfoVoPage.getRecords())) {
            return Result.noData();
        }
        refundInfoVoPage.getRecords().forEach(refundInfoVo -> {
            refundInfoVo.setRefundTypeStr(RefundTypeEnum.getDesc(refundInfoVo.getRefundType()));
            refundInfoVo.setStatusStr(RefundStatusEnum.getDesc(refundInfoVo.getStatus()));
        });
        return Result.success(refundInfoVoPage);
    }

    @Override
    public Result<RefundDetailVo> getRefundDetail(Long refundId) {
        UserTokenInfoVo userTokenInfoVo = currentRequestComponent.getUserTokenInfo();
        if (userTokenInfoVo == null) {
            return Result.notLoginError();
        }
        RefundInfo refundInfo = this.getById(refundId);
        if (refundInfo == null) {
            return Result.errorInfo("退款订单不存在");
        }
        if (Objects.equals(userTokenInfoVo.getUserType(), CommonConstant.MALL_USER_TYPE) && !Objects.equals(userTokenInfoVo.getUserId(), refundInfo.getUserId())) {
            return Result.noAuth();
        }
        if (Objects.equals(userTokenInfoVo.getUserType(), CommonConstant.MERCHANT_USER_TYPE) && !Objects.equals(userTokenInfoVo.getUserId(), refundInfo.getMerchantId())) {
            return Result.noAuth();
        }
        RefundDetailVo refundDetailVo = new RefundDetailVo();
        BeanUtils.copyProperties(refundInfo, refundDetailVo);
        refundDetailVo.setRefundTypeStr(RefundTypeEnum.getDesc(refundInfo.getRefundType()));
        refundDetailVo.setStatusStr(RefundStatusEnum.getDesc(refundInfo.getStatus()));
        // 查询商家信息
        MerchantUserInfo merchantUserInfo = merchantUserInfoService.getById(refundInfo.getMerchantId());
        if (merchantUserInfo != null) {
            refundDetailVo.setMerchantName(merchantUserInfo.getMerchantName());
        }
        // 查询退款商品
        OrderProductVersion orderProductVersion = orderProductVersionService.getOne(new LambdaQueryWrapper<OrderProductVersion>().eq(OrderProductVersion::getOrderNo, refundInfo.getOrderNo()).eq(OrderProductVersion::getProductId, refundInfo.getProductId()));
        if (orderProductVersion != null) {
            OrderProductVo orderProductVo = new OrderProductVo();
            BeanUtils.copyProperties(orderProductVersion, orderProductVo);
            refundDetailVo.setProductName(orderProductVersion.getProductName());
            refundDetailVo.setOrderProductVo(orderProductVo);
        }
        return Result.success(refundDetailVo);
    }
}
