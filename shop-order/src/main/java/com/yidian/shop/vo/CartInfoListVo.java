package com.yidian.shop.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 购物车列表
 *
 * @function:
 * @description: CartInfoListVo.java
 * @date: 2021/08/27
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class CartInfoListVo {

    /**
     * 商家ID
     */
    private Long merchantId;

    /**
     * 商家名称
     */
    private String merchantName;

    /**
     * 商家地址ID（用来判断配送方式）
     */
    private Integer merchantCityId;

    /**
     * 物流费配置信息
     */
    private DeliveryFeeConfigVo deliveryFeeConfigVo;

    /**
     * 购物车商品列表
     */
    private List<CartInfoVo> cartInfoList;

}
