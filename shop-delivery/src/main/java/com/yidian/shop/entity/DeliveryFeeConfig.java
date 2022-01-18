package com.yidian.shop.entity;

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
 * 物流费配置表
 * </p>
 *
 * @author dev
 * @since 2021-09-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_delivery_fee_config")
public class DeliveryFeeConfig extends Model<DeliveryFeeConfig> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商家ID
     */
    private Long merchantId;

    /**
     * 配送地址
     */
    private String deliveryAddress;

    /**
     * 配送地址经度
     */
    private String addressLon;

    /**
     * 配送地址纬度
     */
    private String addressLat;

    /**
     * 配送说明
     */
    private String deliveryInstruction;

    /**
     * 免运费订单最小金额
     */
    private BigDecimal minOrderMoney;

    /**
     * 运费
     */
    private BigDecimal deliveryMoney;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    @TableLogic
    private Integer delFlag;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
