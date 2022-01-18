package com.yidian.shop.exception;

/**
 * @function:
 * @description: BizException.java
 * @date: 2021/04/29
 * @author: sunfayun
 * @version: 1.0
 */
public class BizException extends RuntimeException {

    public BizException(String message) {
        super(message);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

}
