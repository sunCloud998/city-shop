package com.yidian.shop.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @function:
 * @description: RefundInfoVo.java
 * @date: 2021/09/08
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class RefundInfoVo {

    /**
     * 退款ID
     */
    private Long id;

    /**
     * 商家id
     */
    private Long merchantId;

    /**
     * 商家名称
     */
    private String merchantName;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 退款类型，1-退款退货,2-仅退金额
     */
    private Integer refundType;

    /**
     * 退款类型名称
     */
    private String refundTypeStr;

    /**
     * 退款金额
     */
    private BigDecimal refundAmount;

    /**
     * 申请状态：0->待处理；1->退货中；2->已完成；3->已拒绝
     */
    private Integer status;

    /**
     * 状态名称
     */
    private String statusStr;

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

}
