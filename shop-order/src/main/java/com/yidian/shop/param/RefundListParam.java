package com.yidian.shop.param;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 退款列表请求参数
 *
 * @function:
 * @description: RefundListParam.java
 * @date: 2021/09/08
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class RefundListParam extends BasePageParam {

    /**
     * 商家id
     */
    private Long merchantId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 退款类型，1-退款退货,2-仅退金额
     */
    private Integer refundType;

    /**
     * 申请状态：0->待处理；1->退货中；2->已完成；3->已拒绝
     */
    private Integer status;

}
