package com.yidian.shop.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @function:
 * @description: ProductSaveOrUpdateParam.java
 * @date: 2021/08/22
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class ProductSaveOrUpdateParam {

    private Long id;

    /**
     * 商品编号
     */
    private String barCode;

    /**
     * 商品名称
     */
    @NotBlank(message = "商品名称不能为空")
    private String name;

    /**
     * 商品所属类目ID
     */
    @NotNull(message = "商品分类不能为空")
    private Integer categoryId;

    /**
     * 品牌ID
     */
    @NotNull(message = "商品品牌不能为空")
    private Integer brandId;

    /**
     * 商品关键字，采用逗号间隔
     */
    private String keywords;

    /**
     * 排序值
     */
    private Integer sortOrder;

    /**
     * 是否新品首发
     */
    @JsonProperty("isNew")
    private Integer isNew;

    /**
     * 是否人气推荐
     */
    @JsonProperty("isHot")
    private Integer isHot;

    /**
     * 参考价格
     */
    @NotNull(message = "商品参考价不能为空")
    private BigDecimal advisePrice;

    /**
     * 成本价格
     */
    @NotNull(message = "商品成本价不能为空")
    private BigDecimal costPrice;

    /**
     * 售卖价格
     */
    @NotNull(message = "商品售卖价不能为空")
    private BigDecimal salePrice;

    /**
     * 销售总量
     */
    private Integer saleCount;

    /**
     * 商品宣传图片列表，采用JSON数组格式
     */
    private List<String> images;

    /**
     * 商品简介
     */
    private String introduction;

    /**
     * 商品详细介绍，是富文本格式
     */
    @NotBlank(message = "商品详情不能为空")
    private String detail;

    /**
     * 参数列表
     */
    private List<ProductAttributeParam> productAttributeList;

    /**
     * 规格列表
     */
    private List<ProductSpecParam> productSpecList;

    /**
     * sku列表
     */
    private List<ProductSkuParam> productSkuList;

    @Data
    public static class ProductAttributeParam {

        private Long id;

        /**
         * 商品参数名称
         */
        private String attribute;

        /**
         * 商品参数值
         */
        private String value;

        /**
         * 删除标识：0默认，1删除
         */
        private Integer delFlag;
    }

    @Data
    public static class ProductSpecParam {

        private Long id;

        /**
         * 商品规格名称
         */
        private String specName;

        /**
         * 商品规格值
         */
        private String specValue;

        /**
         * 商品规格图片
         */
        private String imageUrl;

        /**
         * 删除标识：0默认，1删除
         */
        private Integer delFlag;
    }

    @Data
    public static class ProductSkuParam {

        private Long id;

        /**
         * 商品规格值列表，采用JSON数组格式
         */
        private List<String> spec;

        /**
         * 商品货品价格
         */
        private BigDecimal price;

        /**
         * 商品货品数量
         */
        private Integer stock;

        /**
         * 商品货品图片
         */
        private String imageUrl;

        /**
         * 删除标识：0默认，1删除
         */
        private Integer delFlag;

    }


}
