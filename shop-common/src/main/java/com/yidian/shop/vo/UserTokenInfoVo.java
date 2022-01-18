package com.yidian.shop.vo;

import lombok.Data;

/**
 * @function:
 * @description: UserTokenInfoVo.java
 * @date: 2021/08/22
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class UserTokenInfoVo {

    private Long userId;
    private String userName;
    private Integer userType;

}
