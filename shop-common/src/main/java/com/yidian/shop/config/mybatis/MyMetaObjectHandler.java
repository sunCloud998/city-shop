package com.yidian.shop.config.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {

        Object createDate = getFieldValByName("create_time", metaObject);
        if (createDate == null) {
            setFieldValByName("create_time", LocalDateTime.now(), metaObject);
        }

        Object modifiedDate = getFieldValByName("update_time", metaObject);
        if (modifiedDate == null) {
            setFieldValByName("update_time", LocalDateTime.now(), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Object modifiedDate = getFieldValByName("update_time", metaObject);
        if (modifiedDate == null) {
            setFieldValByName("update_time", LocalDateTime.now(), metaObject);
        }
    }
}
