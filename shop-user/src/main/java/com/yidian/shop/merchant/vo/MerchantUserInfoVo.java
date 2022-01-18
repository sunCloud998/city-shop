package com.yidian.shop.merchant.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 供应商列表返回结果
 *
 * @function:
 * @description: MerchantListVo.java
 * @date: 2021/08/10
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class MerchantUserInfoVo {

    /**
     * 供应商ID
     */
    private Long id;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 商家名称
     */
    private String merchantName;

    /**
     * 商家地址
     */
    private String merchantAddress;

    /**
     * 商家logo
     */
    private String logo;

    /**
     * 商家简介
     */
    private String introduction;

    /**
     * 联系人名字
     */
    private String contactName;

    /**
     * 联系人电话
     */
    private String contactMobile;

    /**
     * 商家评分
     */
    private BigDecimal merchantScore;

    /**
     * 商家权重
     */
    private BigDecimal merchantWeight;

    /**
     * 帐号启用状态:0-待审核；1-审核通过；2-审核拒绝；3-禁用
     */
    private Integer status;

    /**
     * 状态名称
     */
    private String statusStr;

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

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
