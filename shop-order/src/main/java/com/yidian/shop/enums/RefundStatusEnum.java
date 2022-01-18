package com.yidian.shop.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * @function:
 * @description: RefundStatusEnum.java
 * @date: 2021/09/08
 * @author: sunfayun
 * @version: 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum RefundStatusEnum {

    WAIT_DEAL(0, "待处理"),
    REFUND_BACKING(1, "退货中"),
    COMPLETE(2, "已完成"),
    REFUSE(3, "已拒绝"),
    CANCEL(4, "已取消");

    private int code;
    private String desc;

    public static String getDesc(int code) {
        for (RefundStatusEnum refundStatusEnum : RefundStatusEnum.values()) {
            if (refundStatusEnum.getCode() == code) {
                return refundStatusEnum.getDesc();
            }
        }
        return StringUtils.EMPTY;
    }

}
