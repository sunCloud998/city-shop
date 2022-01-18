package com.yidian.shop.common.service;

/**
 * @function:
 * @description: UserAuthCodeService.java
 * @date: 2021/08/08
 * @author: sunfayun
 * @version: 1.0
 */
public interface UserAuthCodeService {

    /**
     * 生成验证码
     */
    String generateAuthCode(String telephone);

}
