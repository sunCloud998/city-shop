package com.yidian.shop.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @function:
 * @description: ProductShowTypeEnum.java
 * @date: 2021/08/23
 * @author: sunfayun
 * @version: 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ProductShowTypeEnum {

    NEW_AREA(1, "新品专区"),
    HOT_AREA(2, "爆品专区"),
    DEFAULT_AREA(3, "乐物专区");


    private int code;
    private String desc;

}
