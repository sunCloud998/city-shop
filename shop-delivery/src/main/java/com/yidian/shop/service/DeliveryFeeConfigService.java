package com.yidian.shop.service;

import com.yidian.shop.entity.DeliveryFeeConfig;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yidian.shop.param.DeliveryFeeConfigParam;
import com.yidian.shop.vo.DeliveryFeeConfigVo;
import com.yidian.shop.vo.Result;

import java.util.List;

/**
 * <p>
 * 物流费配置表 服务类
 * </p>
 *
 * @author dev
 * @since 2021-09-28
 */
public interface DeliveryFeeConfigService extends IService<DeliveryFeeConfig> {

    /**
     * 添加或保存物流费配置信息
     *
     * @param param
     * @return
     */
    Result<String> saveOrUpdateDeliveryFeeConfig(DeliveryFeeConfigParam param);

    /**
     * 获取物流费配置信息
     *
     * @return
     */
    Result<DeliveryFeeConfigVo> getDeliveryFeeConfig();

    /**
     * 获取商家物流费配置信息
     *
     * @param merchantId 商家ID
     * @return
     */
    DeliveryFeeConfigVo getMerchantDeliveryFeeConfig(Long merchantId);

    /**
     * 获取商家物流配置信息列表
     *
     * @param merchantIdList
     * @return
     */
    List<DeliveryFeeConfigVo> getMerchantDeliveryFeeConfigList(List<Long> merchantIdList);
}
