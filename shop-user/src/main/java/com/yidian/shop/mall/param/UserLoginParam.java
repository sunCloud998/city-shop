package com.yidian.shop.mall.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 用户登录请求参数
 *
 * @function:
 * @description: UserLoginParam.java
 * @date: 2021/08/02
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class UserLoginParam {

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    private String phone;

    /**
     * 登录密码
     */
    @NotBlank(message = "登录密码不能为空")
    private String password;

}
