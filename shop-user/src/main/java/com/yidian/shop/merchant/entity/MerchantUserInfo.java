package com.yidian.shop.merchant.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableLogic;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 商家用户表
 * </p>
 *
 * @author dev
 * @since 2021-08-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_merchant_user_info")
public class MerchantUserInfo extends Model<MerchantUserInfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
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
     * 密码
     */
    private String password;

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
     * 帐号启用状态:0-禁用；1-启用
     */
    private Integer status;

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
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 逻辑删除标志
     */
    @TableLogic
    private Boolean delFlag;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
