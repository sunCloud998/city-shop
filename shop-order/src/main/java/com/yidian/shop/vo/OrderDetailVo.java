package com.yidian.shop.vo;

import lombok.Data;

import java.util.List;

/**
 * 订单详情返回结果
 *
 * @function:
 * @description: OrderDetailVo.java
 * @date: 2021/09/05
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class OrderDetailVo extends OrderInfoVo {

    /**
     * 订单包含的商品列表
     */
    private List<OrderProductVo> orderProductList;

    /**
     * 订单进程列表
     */
    private List<OrderProcessVo> orderProcessVoList;

    /**
     * 收货地址
     */
    private UserAddressVo userAddressVo;

}
