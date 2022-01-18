package com.yidian.shop.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单列表返回数据对象
 *
 * @function:
 * @description: OrderInfoVo.java
 * @date: 2021/09/05
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class OrderInfoVo {

    /**
     * 订单id
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 商家ID
     */
    private Long merchantId;

    /**
     * 商家名称
     */
    private String merchantName;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 订单总金额
     */
    private BigDecimal totalPrice;

    /**
     * 实际支付金额
     */
    private BigDecimal realPrice;

    /**
     * 运费金额
     */
    private BigDecimal deliveryPrice;

    /**
     * 订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->已取消
     */
    private Integer status;

    /**
     * 订单状态名称
     */
    private String statusStr;

    /**
     * 订单类型：0->正常订单；1->预约订单
     */
    private Integer orderType;

    /**
     * 订单类型名称
     */
    private String orderTypeStr;

    /**
     * 配送方式，0->快递；1->同城配送
     */
    private Integer deliveryType;

    /**
     * 配送方式名称
     */
    private String deliveryTypeStr;

    /**
     * 物流公司
     */
    private String deliveryCompany;

    /**
     * 物流单号
     */
    private String deliveryNo;

    /**
     * 收货信息ID
     */
    private Long receiveAddressId;

    /**
     * 预约时间
     */
    private LocalDateTime reserveTime;

    /**
     * 订单备注
     */
    private String note;

    /**
     * 支付时间
     */
    private LocalDateTime paymentTime;

    /**
     * 发货时间
     */
    private LocalDateTime deliveryTime;

    /**
     * 确认收货时间
     */
    private LocalDateTime receiveTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
