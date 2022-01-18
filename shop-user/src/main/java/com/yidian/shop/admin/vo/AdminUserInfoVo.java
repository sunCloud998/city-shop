package com.yidian.shop.admin.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 管理员用户信息返回结果
 *
 * @function:
 * @description: AdminUserInfoVo.java
 * @date: 2021/08/13
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class AdminUserInfoVo {

    /**
     * 管理员用户ID
     */
    private Long id;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 帐号启用状态:0-禁用；1-启用
     */
    private Integer status;

    /**
     * 头像
     */
    private String icon;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
