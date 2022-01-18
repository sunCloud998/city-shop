package com.yidian.shop.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 订单进程对象
 *
 * @function:
 * @description: OrderProcessVo.java
 * @date: 2021/09/09
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class OrderProcessVo {

    /**
     * 进程ID
     */
    private Long id;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 标签
     */
    private String label;

    /**
     * 进程内容
     */
    private String content;

    /**
     * 操作用户名称
     */
    private String optUserName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
