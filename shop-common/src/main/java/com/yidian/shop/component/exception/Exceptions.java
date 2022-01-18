package com.yidian.shop.component.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

public final class Exceptions {

    /**
     * 私有构造器
     */
    private Exceptions() {
    }

    /**
     * @param e 异常
     * @return 将CheckedException转换为UnCheckedException.
     */
    public static RuntimeException unchecked(final Exception e) {
        if (e instanceof RuntimeException) {
            return (RuntimeException) e;
        }
        return new RuntimeException(e.getMessage(), e);
    }

    /**
     * @param exception 异常
     * @return 将异常的ErrorStack转化为String.
     */
    public static String getStackTraceAsString(final Throwable exception) {
        StringWriter sw = null;
        PrintWriter pw = null;
        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            exception.printStackTrace(pw);
            return sw.toString();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    /**
     * @param ex                    异常
     * @param causeExceptionClasses 底层异常
     * @return 判断异常是否由某些底层的异常引起.
     */
    @SafeVarargs
    public static boolean isCausedBy(final Exception ex, final Class<? extends Exception>... causeExceptionClasses) {
        Throwable cause = ex.getCause();
        while (cause != null) {
            for (Class<? extends Exception> causeClass : causeExceptionClasses) {
                if (causeClass.isInstance(cause)) {
                    return true;
                }
            }
            cause = cause.getCause();
        }
        return false;
    }
}