package com.yidian.shop.mall.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 用户注册请求参数
 *
 * @function:
 * @description: UserRegisterParam.java
 * @date: 2021/07/30
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class UserRegisterParam {

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String userName;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 确认密码
     */
    @NotBlank(message = "确认密码不能为空")
    private String confirmPassword;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    private String phone;

    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")
    private String authCode;

}
