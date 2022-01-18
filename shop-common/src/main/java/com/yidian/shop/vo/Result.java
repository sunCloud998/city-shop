package com.yidian.shop.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.Collections;

/**
 * 前端控制器返回封装类，除了第三方调用（cloud和http等请求）外，
 * 本系统内部尽量在controller层使用，不建议传递到service层（复用麻烦）
 *
 * @param <T>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {

    public static final int SUCCESS = 0;// 成功

    public static final int FAIL = -1;// 失败

    public static final int NOT_LOGIN = 1000; // 未登录

    private int code = 0;

    private String msg = "";

    private String userMsg = "";

    private T data = null;

    /**
     * 错误返回，不需要异常信息
     *
     * @param code
     * @param msg
     * @return
     */
    public static Result returnError(int code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    /**
     * 数据返回 自定义code和msg
     *
     * @param code
     * @param msg
     * @return
     */
    public static <T> Result<T> returnData(int code, String msg, T data) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    /**
     * 判断是否成功
     *
     * @return
     */
    @JsonIgnore
    public boolean isSucceed() {
        return Result.SUCCESS == this.getCode();
    }

    /**
     * 调用成功,且有可用数据
     *
     * @return
     */
    public boolean succeedData() {
        if (isSucceed() && this.getData() != null) {
            Object o = this.getData();
            if (o instanceof Collection) {
                return CollectionUtils.isNotEmpty((Collection<?>) o);
            }
            return true;
        }
        return false;
    }

    public boolean succeedTrueData() {
        if (isSucceed() && this.getData() != null) {
            Object o = this.getData();
            if (o instanceof Boolean) {
                return (Boolean) o;
            }
            return false;
        }
        return false;
    }

    public static <T> Result<T> success(String msg, String userMsg, T t) {
        if (t == null) {
            if (t instanceof Collection) {
                return new Result(Result.SUCCESS, msg, userMsg, Collections.emptyList());
            } else if (t instanceof Object) {
                return new Result(Result.SUCCESS, msg, userMsg, new Object());
            }
        }
        return new Result<>(Result.SUCCESS, msg, userMsg, t);
    }

    public static <T> Result<T> success(T t) {
        return new Result<>(Result.SUCCESS, "SUCCESS", "请求成功", t);
    }

    public static <T> Result<T> successInfo(String userMsg) {
        return new Result<>(Result.SUCCESS, "SUCCESS", userMsg, null);
    }

    public static <T> Result<T> dealSuccess() {
        return new Result<>(Result.SUCCESS, "SUCCESS", "请求成功", null);
    }

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> error(String msg, String userMsg, T t) {
        return new Result<>(Result.FAIL, msg, userMsg, t);
    }

    public static <T> Result<T> error(String msg, String userMsg) {
        return new Result<>(Result.FAIL, msg, userMsg, null);
    }

    public static <T> Result<T> error(String userMsg) {
        return new Result<>(Result.FAIL, "", userMsg, null);
    }

    public static <T> Result<T> error(T t) {
        return new Result<>(Result.FAIL, "", "", t);
    }

    public static <T> Result<T> error() {
        return new Result<>(Result.FAIL, "", "服务异常，稍后再试", null);
    }

    public static <T> Result<T> dealError() {
        return new Result<>(Result.FAIL, "", "请求处理失败", null);
    }

    public static <T> Result<T> noAuth() {
        return new Result<>(Result.FAIL, "", "账号异常，请登录正确账号", null);
    }

    public static <T> Result<T> errorInfo(String userMsg) {
        return new Result<T>(Result.FAIL, "FAILED", userMsg, null);
    }

    public static <T> Result<T> paramError() {
        return new Result<T>(Result.FAIL, "FAILED", "请求参数错误", null);
    }

    public static <T> Result<T> notLoginError() {
        return new Result<T>(Result.NOT_LOGIN, "FAILED", "请登录后再进行操作!", null);
    }

    public static <T> Result<T> noData() {
        return new Result<>(Result.SUCCESS, "", "暂无数据", null);
    }

    public static <T> Result<T> noData(T data) {
        return new Result<>(Result.SUCCESS, "", "暂无数据", data);
    }

    public static <T> Result<T> noData(String userMsg) {
        return new Result<>(Result.SUCCESS, "", userMsg, null);
    }

}
