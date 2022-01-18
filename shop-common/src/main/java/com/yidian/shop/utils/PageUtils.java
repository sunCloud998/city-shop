/**
 * Copyright 2018 人人开源 http://www.renren.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.yidian.shop.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 分页工具类
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月4日 下午12:59:00
 */
@Data
public class PageUtils implements Serializable {
    private static final long serialVersionUID = 1L;
    //总记录数
    private int totalCount;
    //每页记录数
    private long pageSize;
    //总页数
    private int totalPage;
    //当前页数
    private long currPage;
    //列表数据
    private List<?> list;

    /**
     * 分页
     *
     * @param list       列表数据
     * @param totalCount 总记录数
     * @param pageSize   每页记录数
     * @param currPage   当前页数
     */
    public PageUtils(List<?> list, int totalCount, int pageSize, int currPage) {
        this.list = list;
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.currPage = currPage;
        this.totalPage = (int) Math.ceil((double) totalCount / pageSize);
    }

    /**
     * 分页
     */
    public PageUtils(IPage<?> page) {
        this.list = page.getRecords();
        this.totalCount = (int) page.getTotal();
        this.pageSize = page.getSize();
        this.currPage = page.getCurrent();
        this.totalPage = (int) page.getPages();
    }

    /**
     * 逻辑分页获取截断区间方法
     *
     * @param size    每页大小
     * @param current 当前页
     * @param max     数据总量
     * @return 截断区间
     */
    public static String getStartAndEnd(int size, int current, int max) {
        if (max > size * current) {
            return size * (current - 1) + "-" + current * size;
        } else if (max < size * current && max > size * (current - 1)) {
            return size * (current - 1) + "-" + max;
        } else {
            return null;
        }
    }

    /**
     * 手动分页
     *
     * @param list
     * @param current
     * @param size
     * @param <T>
     * @return 返回分页后当前页的list
     */
    public static <T> List<T> getPageList(List<T> list, Integer current, Integer size) {
        if (list.size() == 0) {
            return list;
        }
        if (current == null || current == 0) {
            current = 1;
        }
        if (size == null || size == 0) {
            size = 10;
        }

        int fromIndex = (current - 1) * size;
        if (fromIndex >= list.size() || fromIndex < 0) {
            return Collections.emptyList();
        }

        int toIndex = current * size;
        if (toIndex > list.size()) {
            toIndex = list.size();
        }
        return list.subList(fromIndex, toIndex);
    }

}
