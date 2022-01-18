package com.yidian.shop.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * @function:
 * @description: ProductStatusEnum.java
 * @date: 2021/08/22
 * @author: sunfayun
 * @version: 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ProductStatusEnum {

    WAIT(1, "待上架"),
    UP(2, "上架"),
    DOWN(3, "下架");

    private int code;
    private String desc;

    public static ProductStatusEnum getProductStatusEnum(Integer code) {
        if (code == null) {
            return null;
        }
        for (ProductStatusEnum productStatusEnum : ProductStatusEnum.values()) {
            if (Objects.equals(productStatusEnum.getCode(), code)) {
                return productStatusEnum;
            }
        }
        return null;
    }

}
