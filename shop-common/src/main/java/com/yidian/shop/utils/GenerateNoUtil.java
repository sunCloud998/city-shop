package com.yidian.shop.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * @function:
 * @description: GenerateNoUtil.java
 * @date: 2021/02/01
 * @author: sunfayun
 * @version: 1.0
 */
@Slf4j
public class GenerateNoUtil {

    private final static Snowflake TRADE_SNOW_FLAKE = IdUtil.getSnowflake(1, 1);
    private final static Snowflake USER_SNOW_FLAKE = IdUtil.getSnowflake(1, 1);
    private final static Snowflake FILE_SNOW_FLAKE = IdUtil.getSnowflake(1, 1);

    public static String generateTradeNo() {
        StringBuffer tradeNo = new StringBuffer();
        try {
            tradeNo.append(DateUtil.stringParseLocalDateTime(LocalDateTime.now(), DateUtil.ZH_CN_DP_NO_TRIM));
            String flakeId = String.valueOf(TRADE_SNOW_FLAKE.nextId());
            tradeNo.append(flakeId.substring(flakeId.length() - 10));
        } catch (Exception e) {
            log.error("error", e);
        }
        return tradeNo.toString();
    }

    public static String generateUserId() {
        StringBuffer userId = new StringBuffer();
        try {
            userId.append(DateUtil.stringParseLocalDateTime(LocalDateTime.now(), DateUtil.ZH_CN_DP_NO_TRIM));
            String flakeId = String.valueOf(USER_SNOW_FLAKE.nextId());
            userId.append(flakeId.substring(flakeId.length() - 6));
        } catch (Exception e) {
            log.error("error", e);
        }
        return userId.toString();
    }

    public static String generateFileId() {
        StringBuffer fileId = new StringBuffer();
        try {
            fileId.append(DateUtil.stringParseLocalDateTime(LocalDateTime.now(), DateUtil.ZH_CN_DP_NO_TRIM));
            String flakeId = String.valueOf(FILE_SNOW_FLAKE.nextId());
            fileId.append(flakeId.substring(flakeId.length() - 6));
        } catch (Exception e) {
            log.error("error", e);
        }
        return fileId.toString();
    }

    /**
     * 生成随机用户名
     *
     * @return
     */
    public static String getUserNameRandom() {
        int length = 8;
        String val = "";
        Random random = new Random();
        //参数length，表示生成几位随机数
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                val += (char) (random.nextInt(26) + 97);
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

    public static void main(String[] args) {
        System.err.println(System.currentTimeMillis());
        //9223372036854775807
    }

}
