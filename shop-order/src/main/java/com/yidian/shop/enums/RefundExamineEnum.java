package com.yidian.shop.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @function:
 * @description: RefundExamineEnum.java
 * @date: 2021/09/08
 * @author: sunfayun
 * @version: 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum RefundExamineEnum {

    PASS(1, "ιθΏ"),
    REFUSE(2, "ζη»");

    private int code;
    private String desc;

}
