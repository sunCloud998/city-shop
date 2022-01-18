package com.yidian.shop.admin.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 管理员用户登录请求参数
 *
 * @function:
 * @description: AdminUserLoginParam.java
 * @date: 2021/08/08
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class AdminUserLoginParam {

    /**
     * 登录手机号
     */
    @NotBlank(message = "登录手机号不能为空")
    private String phone;

    /**
     * 登录密码
     */
    @NotBlank(message = "登录密码不能为空")
    private String password;

}
