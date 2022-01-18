package com.yidian.shop.vo;

import lombok.Data;

/**
 * 退款原因返回对象
 *
 * @function:
 * @description: RefundReasonTagVo.java
 * @date: 2021/09/06
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class RefundReasonTagVo {

    /**
     * 退款原因ID
     */
    private Long id;

    /**
     * 退货类型
     */
    private String name;

}
