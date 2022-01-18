package com.yidian.shop.annotation;

import java.lang.annotation.*;

/**
 * @function: 用于记录对象的字段别名
 * @description: LogFieldInfo.java
 * @date: 2021/07/12
 * @author: sunfayun
 * @version: 1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogFieldInfo {

    /**
     * 字段名称
     */
    String fieldName() default "";

    /**
     * 是否需要特殊处理
     */
    boolean special() default false;

}
