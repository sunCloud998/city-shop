package com.yidian.shop.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * @function:
 * @description: DeliveryTypeEnum.java
 * @date: 2021/08/30
 * @author: sunfayun
 * @version: 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum DeliveryTypeEnum {

    EXPRESS(0, "快递"),
    DISTRIBUTION(1, "同城配送");

    private int code;
    private String desc;

    public static String getDesc(int code) {
        for (DeliveryTypeEnum deliveryTypeEnum : DeliveryTypeEnum.values()) {
            if (deliveryTypeEnum.getCode() == code) {
                return deliveryTypeEnum.getDesc();
            }
        }
        return StringUtils.EMPTY;
    }

}
