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
 * 订单退货申请
 * </p>
 *
 * @author dev
 * @since 2021-08-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_refund_info")
public class RefundInfo extends Model<RefundInfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商家id
     */
    private Long merchantId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 退款类型，1-退款退货,2-仅退金额
     */
    private Integer refundType;

    /**
     * 退款金额
     */
    private BigDecimal refundAmount;

    /**
     * 申请状态：0->待处理；1->退货中；2->已完成；3->已拒绝
     */
    private Integer status;

    /**
     * 退款原因
     */
    private String refundReason;

    /**
     * 描述描述
     */
    private String refundDescription;

    /**
     * 退款物流公司
     */
    private String refundDeliveryCompany;

    /**
     * 退款物流单号
     */
    private String refundDeliveryNo;

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

    @TableLogic
    private Integer delFlag;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
