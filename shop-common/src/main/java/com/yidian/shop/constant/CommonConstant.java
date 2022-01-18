package com.yidian.shop.constant;

/**
 * @function:
 * @description: CommonConstant.java
 * @date: 2021/07/12
 * @author: sunfayun
 * @version: 1.0
 */
public interface CommonConstant {

    /**
     * Token加密秘钥
     */
    String TOKEN_SECRET = "B2D62B864CD39D0EE60359FCE254B7DE";

    /**
     * Token前缀
     */
    String AUTHORIZATION = "Authorization";

    /**
     * 商城用户类型
     */
    int MALL_USER_TYPE = 1;

    /**
     * 管理员用户类型
     */
    int ADMIN_USER_TYPE = 2;

    /**
     * 商家用户类型
     */
    int MERCHANT_USER_TYPE = 3;

    /**
     * 支付超时时间
     */
    int PAY_TIME_OUT_HOURS = 2;

    /**
     * 收货超时时间
     */
    int RECEIVE_TIME_OUT_DAYS = 7;

}
