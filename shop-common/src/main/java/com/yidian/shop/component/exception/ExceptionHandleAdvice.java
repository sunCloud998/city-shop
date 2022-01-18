package com.yidian.shop.component.exception;

import cn.hutool.json.JSONUtil;
import com.yidian.shop.exception.BizException;
import com.yidian.shop.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import redis.clients.jedis.exceptions.JedisConnectionException;

import javax.servlet.http.HttpServletRequest;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @function:
 * @description: ExceptionHandleAdvice.java
 * @date: 2021/07/12
 * @author: sunfayun
 * @version: 1.0
 */
@RestControllerAdvice(annotations = RestController.class)
@ResponseStatus(HttpStatus.ACCEPTED)
@Slf4j
public class ExceptionHandleAdvice {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
    }


    @ExceptionHandler(BindException.class)
    public Result<Object> exception(BindException e) {
        log.error("参数异常：{}", Exceptions.getStackTraceAsString(e));
        String message = "";
        if (e.getFieldError() != null) {
            message = e.getFieldError().getDefaultMessage();
        }
        return new Result<>(Result.FAIL, getOutMsg(e), message, null);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Result<Object> paramExceptionHandler(MethodArgumentNotValidException e) {
        BindingResult exceptions = e.getBindingResult();
        // 判断异常中是否有错误信息，如果存在就使用异常中的消息，否则使用默认消息
        if (exceptions.hasErrors()) {
            List<ObjectError> errors = exceptions.getAllErrors();
            if (!errors.isEmpty()) {
                // 这里列出了全部错误参数，按正常逻辑，只需要第一条错误即可
                FieldError fieldError = (FieldError) errors.get(0);
                return Result.errorInfo(fieldError.getDefaultMessage());
            }
        }
        return Result.paramError();
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result<Object> exception(MissingServletRequestParameterException e) {
        log.error("参数异常：{}", Exceptions.getStackTraceAsString(e));
        return new Result<>(Result.FAIL, getOutMsg(e), "缺少必要参数", null);
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result<Object> exception(HttpMessageNotReadableException e) {
        logParam();
        log.error("参数异常：{}", Exceptions.getStackTraceAsString(e));
        return new Result<>(Result.FAIL, getOutMsg(e), "参数错误", null);
    }

    @ExceptionHandler(JedisConnectionException.class)
    public Result<Object> exception(JedisConnectionException e) {
        logParam();
        log.error("redis链接异常：{}", Exceptions.getStackTraceAsString(e));
        return new Result<>(Result.FAIL, getOutMsg(e), "Redis连接异常", null);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Result<Object> exception(IllegalArgumentException e) {
        logParam();
        log.error("参数异常：{}", Exceptions.getStackTraceAsString(e));
        return Result.error(getOutMsg(e), "缺少必要参数");
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public Result<Object> exception(HttpMediaTypeNotSupportedException e) {
        log.error("入参异常", e);
        Map<String, String> parameters = e.getContentType().getParameters();
        for (String s : parameters.keySet()) {
            log.error("param:{}", s);
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String s = request.getRequestURL().toString();
        log.error("url:{}", s);
        return new Result<>(Result.FAIL, getOutMsg(e), "请求数据类型不支持", null);
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Result<Object> exception(MethodArgumentTypeMismatchException e) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String s = request.getRequestURL().toString();
        final String platform = request.getHeader("Platform");
        log.error("url:{},客户端:{},参数名：{},参数值：{}，\n栈信息：{}", s, platform, e.getName(), e.getValue(), Exceptions.getStackTraceAsString(e));
        return new Result<>(Result.FAIL, getOutMsg(e), "服务器开小差了", null);
    }

    @ExceptionHandler(Exception.class)
    public Result<Object> exception(Exception e) {
        logParam();
        log.error("服务器异常:{}", Exceptions.getStackTraceAsString(e));
        return new Result<>(Result.FAIL, getOutMsg(e), "服务器开小差了", null);
    }

    private void logParam() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String s = request.getRequestURL().toString();
        final String platform = request.getHeader("Platform");
        Map<String, String[]> parameterMap = request.getParameterMap();
        log.error("url:{},客户端:{},参数：{}", s, platform, JSONUtil.toJsonStr(parameterMap));
    }

    @ExceptionHandler(RemoteException.class)
    public Result<Object> exception(RemoteException e) {
        logParam();
        log.error("调用远程服务器常：{}", Exceptions.getStackTraceAsString(e));
        return new Result<>(Result.FAIL, getOutMsg(e), "远程调用发生异常", null);
    }

    @ExceptionHandler(BizException.class)
    public Result<Object> exception(BizException e) {
        logParam();
        if (e.getCause() != null) {
            log.error("业务抛出异常：{}", Exceptions.getStackTraceAsString(e));
        }
        return Result.error("fail", e.getMessage());
    }

    private static String getOutMsg(final Throwable e) {
        if (e != null && e.getClass() != null) {
            return e.getClass().getCanonicalName();
        }
        return "";
    }

}
