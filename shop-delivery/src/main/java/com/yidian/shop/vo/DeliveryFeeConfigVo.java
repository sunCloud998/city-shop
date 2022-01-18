package com.yidian.shop.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 物流费配置返回结果
 *
 * @function:
 * @description: DeliveryFeeConfigVo.java
 * @date: 2021/09/28
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class DeliveryFeeConfigVo {

    private Long id;

    /**
     * 商家ID
     */
    private Long merchantId;

    /**
     * 配送地址
     */
    private String deliveryAddress;

    /**
     * 配送地址经度
     */
    private String addressLon;

    /**
     * 配送地址纬度
     */
    private String addressLat;

    /**
     * 配送说明
     */
    private String deliveryInstruction;

    /**
     * 免运费订单最小金额
     */
    private BigDecimal minOrderMoney;

    /**
     * 运费
     */
    private BigDecimal deliveryMoney;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
