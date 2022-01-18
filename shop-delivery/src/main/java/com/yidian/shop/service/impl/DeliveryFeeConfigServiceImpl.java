package com.yidian.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.yidian.shop.component.CurrentRequestComponent;
import com.yidian.shop.constant.CommonConstant;
import com.yidian.shop.entity.DeliveryFeeConfig;
import com.yidian.shop.mapper.DeliveryFeeConfigMapper;
import com.yidian.shop.param.DeliveryFeeConfigParam;
import com.yidian.shop.service.DeliveryFeeConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yidian.shop.vo.DeliveryFeeConfigVo;
import com.yidian.shop.vo.Result;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 物流费配置表 服务实现类
 * </p>
 *
 * @author dev
 * @since 2021-09-28
 */
@Service
public class DeliveryFeeConfigServiceImpl extends ServiceImpl<DeliveryFeeConfigMapper, DeliveryFeeConfig> implements DeliveryFeeConfigService {

    @Resource
    private CurrentRequestComponent currentRequestComponent;

    @Override
    public Result<String> saveOrUpdateDeliveryFeeConfig(DeliveryFeeConfigParam param) {
        Long merchantId = currentRequestComponent.getLoginUserId(CommonConstant.MERCHANT_USER_TYPE);
        if (merchantId == null) {
            return Result.notLoginError();
        }
        DeliveryFeeConfig deliveryFeeConfig = this.getOne(new LambdaQueryWrapper<DeliveryFeeConfig>().eq(DeliveryFeeConfig::getMerchantId, merchantId));
        if (deliveryFeeConfig == null) {
            deliveryFeeConfig = new DeliveryFeeConfig();
        }
        BeanUtils.copyProperties(param, deliveryFeeConfig);
        boolean result = this.saveOrUpdate(deliveryFeeConfig);
        if (!result) {
            return Result.dealError();
        }
        return Result.dealSuccess();
    }

    @Override
    public Result<DeliveryFeeConfigVo> getDeliveryFeeConfig() {
        Long merchantId = currentRequestComponent.getLoginUserId(CommonConstant.MERCHANT_USER_TYPE);
        if (merchantId == null) {
            return Result.notLoginError();
        }
        DeliveryFeeConfig deliveryFeeConfig = this.getOne(new LambdaQueryWrapper<DeliveryFeeConfig>().eq(DeliveryFeeConfig::getMerchantId, merchantId));
        if (deliveryFeeConfig == null) {
            return Result.noData();
        }
        DeliveryFeeConfigVo deliveryFeeConfigVo = new DeliveryFeeConfigVo();
        BeanUtils.copyProperties(deliveryFeeConfig, deliveryFeeConfigVo);
        return Result.success(deliveryFeeConfigVo);
    }

    @Override
    public DeliveryFeeConfigVo getMerchantDeliveryFeeConfig(Long merchantId) {
        if (merchantId == null) {
            return null;
        }
        DeliveryFeeConfig deliveryFeeConfig = this.getOne(new LambdaQueryWrapper<DeliveryFeeConfig>().eq(DeliveryFeeConfig::getMerchantId, merchantId));
        if (deliveryFeeConfig == null) {
            return null;
        }
        DeliveryFeeConfigVo deliveryFeeConfigVo = new DeliveryFeeConfigVo();
        BeanUtils.copyProperties(deliveryFeeConfig, deliveryFeeConfigVo);
        return deliveryFeeConfigVo;
    }

    @Override
    public List<DeliveryFeeConfigVo> getMerchantDeliveryFeeConfigList(List<Long> merchantIdList) {
        if (CollectionUtils.isEmpty(merchantIdList)) {
            return Collections.emptyList();
        }
        List<DeliveryFeeConfig> deliveryFeeConfigList = this.list(new LambdaQueryWrapper<DeliveryFeeConfig>().in(DeliveryFeeConfig::getMerchantId, merchantIdList));
        if (CollectionUtils.isEmpty(deliveryFeeConfigList)) {
            return Collections.emptyList();
        }
        return deliveryFeeConfigList.stream().map(deliveryFeeConfig -> {
            DeliveryFeeConfigVo deliveryFeeConfigVo = new DeliveryFeeConfigVo();
            BeanUtils.copyProperties(deliveryFeeConfig, deliveryFeeConfigVo);
            return deliveryFeeConfigVo;
        }).collect(Collectors.toList());
    }
}
