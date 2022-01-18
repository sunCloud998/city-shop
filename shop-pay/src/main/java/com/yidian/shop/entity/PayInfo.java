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
 * 支付记录信息表
 * </p>
 *
 * @author dev
 * @since 2021-09-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_pay_info")
public class PayInfo extends Model<PayInfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 商家id
     */
    private Long merchantId;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 外部支付单号
     */
    private String outTradeNo;

    /**
     * 支付方式：0->未支付；1->微信；2->支付宝;3->paypal
     */
    private Integer payType;

    /**
     * 支付金额
     */
    private BigDecimal payAmount;

    /**
     * 商家应收金额
     */
    private BigDecimal merchantAmount;

    /**
     * 平台应收金额
     */
    private BigDecimal platformAmount;

    /**
     * 支付状态：0->未支付；1->支付成功；2->支付失败；3->支付中；
     */
    private Integer payStatus;

    /**
     * 支付时间
     */
    private LocalDateTime paymentTime;

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
