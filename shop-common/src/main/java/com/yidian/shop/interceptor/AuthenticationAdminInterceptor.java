package com.yidian.shop.interceptor;


import cn.hutool.json.JSONUtil;
import com.yidian.shop.annotation.AuthorizationAdmin;
import com.yidian.shop.component.CurrentRequestComponent;
import com.yidian.shop.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @function: 管理员登录拦截器
 * @description: AuthenticationAdminInterceptor.java
 * @date: 2021/04/25
 * @author: sunfayun
 * @version: 1.0
 */
@Slf4j
@Component
public class AuthenticationAdminInterceptor implements AsyncHandlerInterceptor {

    private final static String NULL_STR = "null";
    private final static String UNDEFINED_STR = "undefined";

    @Resource
    private CurrentRequestComponent currentRequestComponent;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        AuthorizationAdmin authorizationAdmin;
        if (handler instanceof HandlerMethod) {
            authorizationAdmin = ((HandlerMethod) handler).getMethodAnnotation(AuthorizationAdmin.class);
        } else {
            return true;
        }
        if (authorizationAdmin == null) {
            return true;
        }
        //从header中获取token
        String token = currentRequestComponent.getAuthorization();
        if (StringUtils.isBlank(token) || NULL_STR.equals(token) || UNDEFINED_STR.equals(token)) {
            //重置response
            response.reset();
            //设置编码格式
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter pw = response.getWriter();
            pw.println(JSONUtil.toJsonStr(Result.notLoginError()));
            return false;
        }
        String userName = currentRequestComponent.getLoginUserName(authorizationAdmin.userType());
        if (StringUtils.isBlank(userName)) {
            //重置response
            response.reset();
            //设置编码格式
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter pw = response.getWriter();
            pw.println(JSONUtil.toJsonStr(Result.notLoginError()));
            return false;
        }
        return true;
    }
}
