package com.yidian.shop.common.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 省市区字典表
 * </p>
 *
 * @author dev
 * @since 2021-08-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_area_info")
public class AreaInfo extends Model<AreaInfo> {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 类型 0省 1直辖市
     */
    private Integer type;

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 父级ID
     */
    private Integer parentId;

    /**
     * 经度
     */
    private Double lng;

    /**
     * 纬度
     */
    private Double lat;

    /**
     * 层级 0省、直辖市、港澳台 1市 2县/区 3镇 4村
     */
    private Integer level;

    /**
     * 排序序号
     */
    private Integer sortNo;

    /**
     * 逻辑删除标志
     */
    @TableLogic
    private Boolean delFlag;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
