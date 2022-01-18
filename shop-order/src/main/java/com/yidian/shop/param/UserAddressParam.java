package com.yidian.shop.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 用户收货地址添加、修改请求参数
 *
 * @function:
 * @description: UserAddressParam.java
 * @date: 2021/08/27
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class UserAddressParam {

    /**
     * 收货地址ID
     */
    private Long id;

    /**
     * 收货人名称
     */
    @NotBlank(message = "收货人姓名不能为空")
    private String receiveUserName;

    /**
     * 省ID
     */
    @NotNull(message = "省ID不能为空")
    private Integer provinceId;

    /**
     * 省名称
     */
    @NotBlank(message = "省名称不能为空")
    private String provinceName;

    /**
     * 市ID
     */
    @NotNull(message = "市ID不能为空")
    private Integer cityId;

    /**
     * 市名称
     */
    @NotBlank(message = "市名称不能为空")
    private String cityName;

    /**
     * 区县ID
     */
    @NotNull(message = "区县ID不能为空")
    private Integer areaId;

    /**
     * 区县名称
     */
    @NotBlank(message = "区县名称不能为空")
    private String areaName;

    /**
     * 具体收货地址
     */
    @NotBlank(message = "具体收货地址不能为空")
    private String address;

    /**
     * 手机号码
     */
    @NotBlank(message = "手机号码不能为空")
    private String mobile;

    /**
     * 是否默认地址，1：默认地址
     */
    @JsonProperty("isDefault")
    private Integer isDefault;

}
