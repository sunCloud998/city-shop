package com.yidian.shop.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableLogic;

import java.io.Serializable;

import lombok.*;

/**
 * <p>
 * 订单进程表
 * </p>
 *
 * @author dev
 * @since 2021-08-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_order_process_info")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderProcessInfo extends Model<OrderProcessInfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

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
     * 用户名称
     */
    private String optUserName;

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
