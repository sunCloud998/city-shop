package com.yidian.shop.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;

import java.util.Map;

/**
 * @function: 字符串模板替换
 * @description: StringTemplateUtils.java
 * @date: 2021/04/23
 * @author: sunfayun
 * @version: 1.0
 */
@Slf4j
public class StringTemplateUtil {

    public static String renderStringTemplate(String source, Map<String, Object> templateValue) {
        if (StringUtils.isBlank(source) || MapUtils.isEmpty(templateValue)) {
            log.warn("renderStringTemplate param is error");
            return StringUtils.EMPTY;
        }
        try {
            return new StringSubstitutor(templateValue).replace(source);
        } catch (Exception e) {
            log.error("renderStringTemplate has exception:", e);
            return StringUtils.EMPTY;
        }
    }

}
