package com.yidian.shop.interceptor;

import com.ijpay.alipay.AliPayApiConfigKit;
import com.yidian.shop.controller.AbstractAliPayApiController;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @function:
 * @description: AliPayInterceptor.java
 * @date: 2021/09/16
 * @author: sunfayun
 * @version: 1.0
 */
@Component
public class AliPayInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (HandlerMethod.class.equals(handler.getClass())) {
            HandlerMethod method = (HandlerMethod) handler;
            Object controller = method.getBean();
            if (!(controller instanceof AbstractAliPayApiController)) {
                throw new RuntimeException("控制器需要继承 AbstractAliPayApiController");
            }
            AliPayApiConfigKit.setThreadLocalAliPayApiConfig(((AbstractAliPayApiController) controller).getApiConfig());
            return true;
        }
        return false;
    }
}
