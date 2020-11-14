package com.eternity.blog.common.enums;

/**
 * @Description 状态码
 * @Author eternity
 * @Date 2020/3/29 17:14
 */
public enum WebStatusCode {
    /**
     * 请求成功
     */
    SUCCESS(200),

    /**
     * 请求失败
     */
    ERROR(500);

    private final int value;

    /**
     * @param code 状态码
     */
    WebStatusCode(int code) {
        this.value = code;
    }

    public int value() {
        return value;
    }
}
