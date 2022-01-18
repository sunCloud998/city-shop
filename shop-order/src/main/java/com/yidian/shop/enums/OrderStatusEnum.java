package com.yidian.shop.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * @function:
 * @description: OrderStatusEnum.java
 * @date: 2021/08/30
 * @author: sunfayun
 * @version: 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum OrderStatusEnum {

    WAIT_PAY(0, "待付款"),
    WAIT_DELIVERY(1, "待发货"),
    HAS_DELIVERY(2, "已发货"),
    COMPLETE(3, "已完成"),
    CLOSE(4, "已关闭"),
    CANCEL(5, "已取消");

    private int code;
    private String desc;

    public static String getDesc(int code) {
        for (OrderStatusEnum orderStatusEnum : OrderStatusEnum.values()) {
            if (orderStatusEnum.getCode() == code) {
                return orderStatusEnum.getDesc();
            }
        }
        return StringUtils.EMPTY;
    }

}
