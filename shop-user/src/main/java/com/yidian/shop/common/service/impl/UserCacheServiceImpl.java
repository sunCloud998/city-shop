package com.yidian.shop.common.service.impl;

import com.yidian.shop.common.service.UserCacheService;
import com.yidian.shop.constant.UserConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @function:
 * @description: UserCacheServiceImpl.java
 * @date: 2021/07/30
 * @author: sunfayun
 * @version: 1.0
 */
@Service
@Slf4j
public class UserCacheServiceImpl implements UserCacheService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void setAuthCode(String telephone, String authCode) {
        String key = UserConstant.USER_AUTH_CODE_REDIS_KEY + telephone;
        stringRedisTemplate.opsForValue().set(key, authCode, 10, TimeUnit.MINUTES);
    }

    @Override
    public String getAuthCode(String telephone) {
        String key = UserConstant.USER_AUTH_CODE_REDIS_KEY + telephone;
        return stringRedisTemplate.opsForValue().get(key);
    }

}
