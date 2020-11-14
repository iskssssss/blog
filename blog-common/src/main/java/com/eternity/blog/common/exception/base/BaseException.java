package com.eternity.blog.common.exception.base;

/**
 * @Description 异常基础类
 * @Author eternity
 * @Date 2020/4/11 23:40
 */
public class BaseException extends RuntimeException {
    private static final long serialVersionUID = 42L;

    public BaseException() {
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}