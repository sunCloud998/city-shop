package com.yidian.shop.config.redis;

import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.lang.Nullable;

import java.nio.charset.StandardCharsets;

/**
 * @function:
 * @description: MyStringSerializer.java
 * @date: 2021/07/30
 * @author: sunfayun
 * @version: 1.0
 */
public class MyStringSerializer extends StringRedisSerializer {

    @Override
    public byte[] serialize(@Nullable String string) {
        if (string == null) {
            return null;
        }
        return string.getBytes(StandardCharsets.UTF_8);
    }

}
