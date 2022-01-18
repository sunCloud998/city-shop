package com.yidian.shop.merchant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @function:
 * @description: MerchantStatusEnum.java
 * @date: 2021/08/11
 * @author: sunfayun
 * @version: 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum MerchantStatusEnum {

    UNCHECKED(0, "待审核"),
    PASS(1, "审核通过"),
    REJECT(2, "审核拒绝"),
    DISABLED(3, "禁用");

    private int code;
    private String desc;

    public static String pareDesc(int status) {
        for (MerchantStatusEnum merchantStatusEnum : MerchantStatusEnum.values()) {
            if (Objects.equals(status, merchantStatusEnum.getCode())) {
                return merchantStatusEnum.getDesc();
            }
        }
        return StringUtils.EMPTY;
    }

    public static MerchantStatusEnum pare(int status) {
        for (MerchantStatusEnum merchantStatusEnum : MerchantStatusEnum.values()) {
            if (Objects.equals(status, merchantStatusEnum.getCode())) {
                return merchantStatusEnum;
            }
        }
        return null;
    }

}
