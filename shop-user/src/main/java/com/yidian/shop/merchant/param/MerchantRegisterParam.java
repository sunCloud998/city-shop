package com.yidian.shop.merchant.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 供应商注册请求参数
 *
 * @function:
 * @description: MerchantRegisterParam.java
 * @date: 2021/08/08
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class MerchantRegisterParam {

    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")
    private String authCode;

    /**
     * 手机号
     */
    @NotBlank(message = "登录手机号不能为空")
    private String phone;

    /**
     * 商家名称
     */
    @NotBlank(message = "商家名称不能为空")
    private String merchantName;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 商家地址
     */
    @NotBlank(message = "商家地址不能为空")
    private String merchantAddress;

    /**
     * 商家简介
     */
    @NotBlank(message = "商家简介不能为空")
    private String introduction;

    /**
     * 联系人名字
     */
    @NotBlank(message = "联系人不能为空")
    private String contactName;

    /**
     * 联系人电话
     */
    private String contactMobile;

    /**
     * 商家地址(省)
     */
    private String provinceName;

    /**
     * 商家地址(省id)
     */
    private Integer provinceId;

    /**
     * 商家地址(市)
     */
    private String cityName;

    /**
     * 商家地址(市id)
     */
    private Integer cityId;

    /**
     * 商家地址(区)
     */
    private String district;

    /**
     * 商家地址(区id)
     */
    private Integer districtId;

}
