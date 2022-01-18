package com.yidian.shop.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @function:
 * @description: SaveOrUpdateDeliveryFeeConfigParam.java
 * @date: 2021/09/28
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class DeliveryFeeConfigParam {

    /**
     * 商家ID
     */
    private Long merchantId;

    /**
     * 配送地址
     */
    @NotBlank(message = "配送地址不能为空")
    private String deliveryAddress;

    /**
     * 配送地址经度
     */
    @NotBlank(message = "配送地址经度不能为空")
    private String addressLon;

    /**
     * 配送地址纬度
     */
    @NotBlank(message = "配送地址纬度不能为空")
    private String addressLat;

    /**
     * 配送说明
     */
    private String deliveryInstruction;

    /**
     * 免运费订单最小金额
     */
    @NotNull(message = "免运费订单最小金额不能为空")
    private BigDecimal minOrderMoney;

    /**
     * 运费
     */
    @NotNull(message = "运费不能为空")
    private BigDecimal deliveryMoney;

}
