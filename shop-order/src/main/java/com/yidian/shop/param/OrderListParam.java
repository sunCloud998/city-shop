package com.yidian.shop.param;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 订单列表查询请求参数
 *
 * @function:
 * @description: OrderListParam.java
 * @date: 2021/09/05
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class OrderListParam extends BasePageParam {

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 订单状态,0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->已取消
     */
    private Integer status;

    /**
     * 订单类型：0->正常订单；1->预约订单
     */
    private Integer orderType;

    /**
     * 配送方式，0->快递；1->同城配送
     */
    private Integer deliveryType;

    /**
     * 用户ID，不需要传，后台根据Token解析
     */
    private Long userId;

    /**
     * 商家ID，不需要传，后台根据Token解析
     */
    private Long merchantId;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

}
