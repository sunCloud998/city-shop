package com.yidian.shop.annotation;

import java.lang.annotation.*;

/**
 * @function:
 * @description: AuthorizationAdmin.java
 * @date: 2021/08/13
 * @author: sunfayun
 * @version: 1.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthorizationAdmin {

    /**
     * 用户类型
     *
     * @return
     */
    int userType();

}
