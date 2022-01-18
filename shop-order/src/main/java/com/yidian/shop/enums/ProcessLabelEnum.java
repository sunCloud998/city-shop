package com.yidian.shop.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @function:
 * @description: ProcessLabelEnum.java
 * @date: 2021/08/30
 * @author: sunfayun
 * @version: 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ProcessLabelEnum {

    CREATE("创建订单"),
    PAY("订单支付"),
    DELIVERY("订单发货"),
    RECEIVE("订单收货"),
    COMPLETE("订单完成"),
    CANCEL("订单取消"),
    DEL("订单删除"),
    CLOSE("订单关闭"),
    REFUND_APPLY("申请退款"),
    REFUND_CANCEL("取消退款"),
    REFUND_EXAMINE("退款审核"),
    REFUND_BACK("退款商品退回"),
    REFUND_RECEIVE("退款商品收货"),
    REFUND_COMPLETE("退款完成");

    private String desc;

}
