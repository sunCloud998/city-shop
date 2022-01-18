package com.yidian.shop.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * @function:
 * @description: RefundTypeEnum.java
 * @date: 2021/09/08
 * @author: sunfayun
 * @version: 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum RefundTypeEnum {

    REFUND_GOODS(1, "退货退款"),
    REFUND_MONEY(2, "仅退金额");

    private int code;
    private String desc;

    public static String getDesc(int code) {
        for (RefundTypeEnum refundTypeEnum : RefundTypeEnum.values()) {
            if (refundTypeEnum.getCode() == code) {
                return refundTypeEnum.getDesc();
            }
        }
        return StringUtils.EMPTY;
    }

}
