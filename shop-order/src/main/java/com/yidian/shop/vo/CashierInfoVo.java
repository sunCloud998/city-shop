package com.yidian.shop.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 收银台信息
 *
 * @function:
 * @description: CashierInfoVo.java
 * @date: 2021/09/18
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class CashierInfoVo {

    @Data
    public static class CashierInfo {
        /**
         * 订单号
         */
        private String orderNo;

        /**
         * 订单支付金额
         */
        private BigDecimal orderPrice;

        /**
         * 商家ID
         */
        private Long merchantId;

        /**
         * 商家名称
         */
        private String merchantName;

        /**
         * 支付截止时间
         */
        private LocalDateTime endTime;
    }

    /**
     * 待支付订单信息
     */
    private List<CashierInfo> cashierInfoList;

    /**
     * 总支付金额
     */
    private BigDecimal totalPrice;

}
