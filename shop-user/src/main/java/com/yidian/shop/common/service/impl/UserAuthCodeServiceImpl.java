package com.yidian.shop.common.service.impl;

import com.yidian.shop.common.service.UserAuthCodeService;
import com.yidian.shop.common.service.UserCacheService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Random;

/**
 * @function:
 * @description: UserAuthCodeServiceImpl.java
 * @date: 2021/08/08
 * @author: sunfayun
 * @version: 1.0
 */
@Service
public class UserAuthCodeServiceImpl implements UserAuthCodeService {

    @Resource
    private UserCacheService userCacheService;

    @Override
    public String generateAuthCode(String telephone) {
        // TODO 替换成调用短信发送接口
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }
        userCacheService.setAuthCode(telephone, sb.toString());
        return sb.toString();
    }

}
