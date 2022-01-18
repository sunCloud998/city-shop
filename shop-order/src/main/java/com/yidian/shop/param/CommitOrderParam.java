package com.yidian.shop.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 提交订单请求参数
 *
 * @function:
 * @description: CommitOrderParam.java
 * @date: 2021/08/30
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class CommitOrderParam {

    /**
     * 购物车ID列表，json数组
     */
    @NotEmpty(message = "购物车ID列表不能为空")
    private List<Long> cartIdList;

    /**
     * 订单类型，0：正常订单，1：预约订单
     */
    @NotNull(message = "订单类型不能为空")
    private Integer orderType;

    /**
     * 预约时间,预约订单必填
     */
    private LocalDateTime reserveTime;

    /**
     * 配送方式，0：快递，1：同城配送
     */
    @NotNull(message = "配送方式不能为空")
    private Integer deliveryType;

    /**
     * 订单来源，0：pc，1：APP，2：h5
     */
    private Integer sourceType;

    /**
     * 收货地址ID
     */
    @NotNull(message = "收货地址ID不能为空")
    private Long receiveAddressId;

    /**
     * 订单备注
     */
    private String note;

}
