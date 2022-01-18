package com.yidian.shop.component.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * @function:
 * @description: BigDecimalSerializer.java
 * @date: 2021/07/12
 * @author: sunfayun
 * @version: 1.0
 */
public class BigDecimalSerializer extends JsonSerializer<BigDecimal> {

    private BigDecimalSerializer() {
    }

    public static BigDecimalSerializer getInstance() {
        return Singleton.INSTANCE.getInstance();
    }

    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(String.format("%.2f", value));
    }

    private enum Singleton {
        INSTANCE;

        private BigDecimalSerializer singleton;

        Singleton() {
            singleton = new BigDecimalSerializer();
        }

        public BigDecimalSerializer getInstance() {
            return singleton;
        }
    }

}
