package com.yidian.shop.vo;

import lombok.Data;

import java.util.Collections;
import java.util.List;

/**
 * 分页数据返回对象
 *
 * @param <T>
 */
@Data
public class BasePageVo<T> {

    private List<T> records;
    private int total;
    private int size;
    private int current;

    public BasePageVo() {
        this.records = Collections.emptyList();
        this.total = 0;
        this.size = 10;
        this.current = 1;
    }

    public BasePageVo(int current, int size) {
        this.records = Collections.emptyList();
        this.total = 0;
        this.current = current;
        this.size = size;
    }

    public Integer getPages() {
        if (total == 0 || size == 0) {
            return 0;
        }

        Integer pages = this.getTotal() / this.getSize();
        if (this.getTotal() % this.getSize() != 0L) {
            ++pages;
        }
        return pages;
    }
}
