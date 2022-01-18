package com.yidian.shop.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * @function:
 * @description: OrderTypeEnum.java
 * @date: 2021/08/30
 * @author: sunfayun
 * @version: 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum OrderTypeEnum {

    NORMAL(0, "普通订单"),
    RESERVE(1, "预约订单");

    private int code;
    private String desc;

    public static String getDesc(int code) {
        for (OrderTypeEnum orderTypeEnum : OrderTypeEnum.values()) {
            if (orderTypeEnum.getCode() == code) {
                return orderTypeEnum.getDesc();
            }
        }
        return StringUtils.EMPTY;
    }

}
