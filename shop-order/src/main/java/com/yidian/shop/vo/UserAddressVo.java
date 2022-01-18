package com.yidian.shop.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户地址返回对象
 *
 * @function:
 * @description: UserAddressVo.java
 * @date: 2021/08/27
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class UserAddressVo {

    /**
     * 收货地址ID
     */
    private Long id;

    /**
     * 收货人名称
     */
    private String receiveUserName;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 省ID
     */
    private Integer provinceId;

    /**
     * 省名称
     */
    private String provinceName;

    /**
     * 市ID
     */
    private Integer cityId;

    /**
     * 市名称
     */
    private String cityName;

    /**
     * 区县ID
     */
    private Integer areaId;

    /**
     * 区县名称
     */
    private String areaName;

    /**
     * 具体收货地址
     */
    private String address;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 是否默认地址，1：默认地址
     */
    private Integer isDefault;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
