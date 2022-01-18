package com.yidian.shop.component;

import cn.hutool.jwt.JWT;
import com.google.common.collect.Maps;
import com.yidian.shop.constant.CommonConstant;
import com.yidian.shop.utils.DateUtil;
import com.yidian.shop.vo.UserTokenInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @function:
 * @description: CurrentRequestComponent.java
 * @date: 2021/08/02
 * @author: sunfayun
 * @version: 1.0
 */
@Slf4j
@Component
public class CurrentRequestComponent {

    /**
     * 生成用户Token
     *
     * @param userId
     * @param userName
     * @return
     */
    public String generateUserInfoToken(Long userId, String userName, Integer userType) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("userId", userId);
        map.put("userName", userName);
        map.put("userType", userType);
        return JWT.create().addPayloads(map)
                .setExpiresAt(DateUtil.addDays(DateUtil.getCurrentDate(), 7))
                .setKey(CommonConstant.TOKEN_SECRET.getBytes())
                .sign();
    }

    /**
     * 获取商城登录用户名
     *
     * @return
     */
    public String getLoginUserName(Integer userType) {
        try {
            if (userType == null) {
                return StringUtils.EMPTY;
            }
            String token = this.getAuthorization();
            if (StringUtils.isBlank(token)) {
                return StringUtils.EMPTY;
            }
            // 判断Token是否过期
            boolean isValid = JWT.of(token).setKey(CommonConstant.TOKEN_SECRET.getBytes()).verify();
            if (!isValid) {
                log.info("获取登录用户名用户Token失效,token:{}", token);
                return StringUtils.EMPTY;
            }
            JWT jwt = JWT.of(token);
            // 获取用户类型
            Object type = jwt.getPayload("userType");
            if (!Objects.equals(userType, type)) {
                return StringUtils.EMPTY;
            }
            // 获取用户名
            Object userName = jwt.getPayload("userName");
            return userName == null ? StringUtils.EMPTY : userName.toString();
        } catch (Exception e) {
            log.error("从Token获取登录用户名发生异常，exception:", e);
            return StringUtils.EMPTY;
        }
    }

    /**
     * 获取登录用户ID
     *
     * @return
     */
    public Long getLoginUserId(Integer userType) {
        try {
            if (userType == null) {
                return null;
            }
            String token = this.getAuthorization();
            if (StringUtils.isBlank(token)) {
                return null;
            }
            // 判断Token是否过期
            boolean isValid = JWT.of(token).setKey(CommonConstant.TOKEN_SECRET.getBytes()).verify();
            if (!isValid) {
                log.info("获取登录用户ID用户Token失效,token:{}", token);
                return null;
            }
            JWT jwt = JWT.of(token);
            // 获取用户类型
            Object type = jwt.getPayload("userType");
            if (!Objects.equals(userType, type)) {
                return null;
            }
            Object userId = jwt.getPayload("userId");
            return userId == null ? null : Long.valueOf(userId.toString());
        } catch (Exception e) {
            log.error("从Token获取登录用户的ID发生异常，exception:", e);
            return null;
        }
    }

    public UserTokenInfoVo getUserTokenInfo() {
        try {
            String token = this.getAuthorization();
            if (StringUtils.isBlank(token)) {
                return null;
            }
            // 判断Token是否过期
            boolean isValid = JWT.of(token).setKey(CommonConstant.TOKEN_SECRET.getBytes()).verify();
            if (!isValid) {
                log.info("获取登录用户ID用户Token失效,token:{}", token);
                return null;
            }
            JWT jwt = JWT.of(token);
            UserTokenInfoVo userTokenInfoVo = new UserTokenInfoVo();
            userTokenInfoVo.setUserId(Long.valueOf(jwt.getPayload("userId").toString()));
            userTokenInfoVo.setUserName(jwt.getPayload("userName").toString());
            userTokenInfoVo.setUserType(Integer.valueOf(jwt.getPayload("userType").toString()));
            return userTokenInfoVo;
        } catch (Exception e) {
            log.error("从Token获取登录用户的ID发生异常，exception:", e);
            return null;
        }
    }

    /**
     * 获取Authorization
     */
    public String getAuthorization() {
        return getRequestAttributes().getRequest().getHeader(CommonConstant.AUTHORIZATION);
    }

    private ServletRequestAttributes getRequestAttributes() {
        return Optional
                .ofNullable((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .orElseThrow(() -> new RuntimeException("未获取到用户请求信息"));
    }

}
