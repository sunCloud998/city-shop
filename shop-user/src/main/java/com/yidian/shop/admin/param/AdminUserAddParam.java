package com.yidian.shop.admin.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 添加管理员请求参数
 *
 * @function:
 * @description: AdminUserAddParam.java
 * @date: 2021/08/08
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class AdminUserAddParam {

    /**
     * 登录手机号
     */
    @NotBlank(message = "登录手机号不能为空")
    private String phone;

    /**
     * 用户名
     */
    @NotBlank(message = "用户不能为空")
    private String userName;

    /**
     * 密码
     */
    private String password;

}
