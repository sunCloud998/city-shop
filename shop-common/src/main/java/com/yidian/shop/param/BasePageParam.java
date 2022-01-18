package com.yidian.shop.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @function:
 * @description: BasePageParam.java
 * @date: 2021/07/12
 * @author: sunfayun
 * @version: 1.0
 */
@Data
public class BasePageParam {

    private static final Integer DEFAULT_CURRENT = 1;
    private static final Integer DEFAULT_SIZE = 10;

    /**
     * 当前页
     */
    @NotNull(message = "当前页不能为空")
    private Integer pageNumber;

    /**
     * 每页记录条数
     */
    @NotNull(message = "请输入每页显示记录条数")
    private Integer pageSize;

    public Integer getCurrentPage() {
        return pageNumber == null ? DEFAULT_CURRENT : pageNumber;
    }

    public Integer getSize() {
        return pageSize == null ? DEFAULT_SIZE : pageSize;
    }

}
