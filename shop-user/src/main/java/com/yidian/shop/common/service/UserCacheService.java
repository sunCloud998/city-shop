package com.yidian.shop.common.service;

/**
 * @function:
 * @description: UserCacheService.java
 * @date: 2021/07/30
 * @author: sunfayun
 * @version: 1.0
 */
public interface UserCacheService {

    /**
     * 设置验证码
     */
    void setAuthCode(String telephone, String authCode);

    /**
     * 获取验证码
     */
    String getAuthCode(String telephone);

}
