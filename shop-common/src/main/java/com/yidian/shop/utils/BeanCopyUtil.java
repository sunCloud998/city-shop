package com.yidian.shop.utils;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author ：Lbj
 * @description： beanCopy 工具类
 * @date ：2021/1/12 14:54
 */
@Slf4j
public class BeanCopyUtil {

    /***
     * @description: 复制对象
     *               举例 copy(student,StudentVO.class) 将student对象实例复制成 studentVO对象实例
     * @Param: source 原始对象
     * @Param: cls 目标对象class类名
     * @return: T
     * @author: Lbj
     * @date: 2021/1/12 15:23
     */
    public static <T> T copy(Object source, Class cls) {
        if (Objects.isNull(source) || Objects.isNull(cls)) {
            return null;
        }
        T t = null;
        try {
            t = (T) cls.newInstance();
        } catch (Exception e) {
            log.error("beanCopy exception {}", e);
        }
        if (Objects.isNull(t)) {
            return null;
        }
        BeanUtils.copyProperties(source, t);
        return t;
    }

    /***
     * @description: 复制列表
     * @Param: list  原始列表
     * @Param: cls 目标对象class类名
     * @return: java.util.List<T>
     * @author: Lbj
     * @date: 2021/1/12 15:25
     */
    public static <S, T> List<T> copyList(List<S> list, Class cls) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<T> tartList = Lists.newArrayList();
        list.forEach(s -> {
            tartList.add(copy(s, cls));
        });
        return tartList;
    }
}
