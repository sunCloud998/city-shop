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
 * 商品基本信息表
 * </p>
 *
 * @author dev
 * @since 2021-08-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_product_info")
public class ProductInfo extends Model<ProductInfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商品编号
     */
    private String barCode;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品所属类目ID
     */
    private Integer categoryId;

    /**
     * 品牌ID
     */
    private Integer brandId;

    /**
     * 商家ID
     */
    private Long merchantId;

    /**
     * 商品关键字，采用逗号间隔
     */
    private String keywords;

    /**
     * 商品状态，1-待上架，2-上架，3-下架
     */
    private Integer status;

    /**
     * 排序值
     */
    private Integer sortOrder;

    /**
     * 是否新品首发
     */
    private Integer isNew;

    /**
     * 是否人气推荐
     */
    private Integer isHot;

    /**
     * 参考价格
     */
    private BigDecimal advisePrice;

    /**
     * 成本价格
     */
    private BigDecimal costPrice;

    /**
     * 售卖价格
     */
    private BigDecimal salePrice;

    /**
     * 上架时间
     */
    private LocalDateTime upTime;

    /**
     * 销售总量
     */
    private Integer saleCount;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 逻辑删除标志
     */
    @TableLogic
    private Integer delFlag;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
