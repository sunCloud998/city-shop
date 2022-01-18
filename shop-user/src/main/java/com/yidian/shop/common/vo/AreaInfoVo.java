package com.yidian.shop.common.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * 省市区返回结果
 *
 * @function:
 * @description: AreaInfoVo.java
 * @date: 2021/08/08
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class AreaInfoVo {

    /**
     * 名字
     */
    private String name;

    /**
     * 编码
     */
    private String code;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<AreaInfoVo> subList;

}
