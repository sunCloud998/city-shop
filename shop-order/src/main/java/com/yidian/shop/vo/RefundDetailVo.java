package com.yidian.shop.vo;

import lombok.Data;

/**
 * 退款订单详情
 *
 * @function:
 * @description: RefundDetailVo.java
 * @date: 2021/09/08
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class RefundDetailVo extends RefundInfoVo {

    /**
     * 退款商品
     */
    private OrderProductVo orderProductVo;

}
