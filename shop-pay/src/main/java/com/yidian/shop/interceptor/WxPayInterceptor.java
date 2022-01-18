package com.yidian.shop.interceptor;

import com.ijpay.wxpay.WxPayApiConfigKit;
import com.yidian.shop.controller.AbstractWxPayApiController;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @function:
 * @description: WxPayInterceptor.java
 * @date: 2021/09/16
 * @author: sunfayun
 * @version: 1.0
 */
@Component
public class WxPayInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (HandlerMethod.class.equals(handler.getClass())) {
            HandlerMethod method = (HandlerMethod) handler;
            Object controller = method.getBean();
            if (!(controller instanceof AbstractWxPayApiController)) {
                throw new RuntimeException("控制器需要继承 AbstractWxPayApiController");
            }
            WxPayApiConfigKit.setThreadLocalWxPayApiConfig(((AbstractWxPayApiController) controller).getApiConfig());
            return true;
        }
        return false;
    }
}
