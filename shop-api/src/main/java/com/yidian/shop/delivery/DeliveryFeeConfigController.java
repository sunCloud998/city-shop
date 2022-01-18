package com.yidian.shop.delivery;


import com.yidian.shop.annotation.AuthorizationAdmin;
import com.yidian.shop.constant.CommonConstant;
import com.yidian.shop.param.DeliveryFeeConfigParam;
import com.yidian.shop.service.DeliveryFeeConfigService;
import com.yidian.shop.vo.DeliveryFeeConfigVo;
import com.yidian.shop.vo.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 物流费配置接口
 *
 * @author dev
 * @since 2021-09-28
 */
@RestController
@RequestMapping("/api/delivery")
public class DeliveryFeeConfigController {

    @Resource
    private DeliveryFeeConfigService deliveryFeeConfigService;

    /**
     * 更新或者保存物流费配置信息
     *
     * @param param 物流费配置信息请求参数
     * @return 返回保存或者更新结果
     */
    @AuthorizationAdmin(userType = CommonConstant.MERCHANT_USER_TYPE)
    @PostMapping("/saveOrUpdateDeliveryFeeConfig/v1")
    public Result<String> saveOrUpdateDeliveryFeeConfig(@RequestBody @Valid DeliveryFeeConfigParam param) {
        return deliveryFeeConfigService.saveOrUpdateDeliveryFeeConfig(param);
    }

    /**
     * 获取物流费配置信息
     *
     * @return 返回供应商物流费配置信息
     */
    @AuthorizationAdmin(userType = CommonConstant.MERCHANT_USER_TYPE)
    @GetMapping("/getDeliveryFeeConfig/v1")
    public Result<DeliveryFeeConfigVo> getDeliveryFeeConfig() {
        return deliveryFeeConfigService.getDeliveryFeeConfig();
    }


}

