package com.eternity.blog.common.enums;

/**
 * @Description 用户账号状态
 * @Author eternity
 * @Date 2020/4/14 0:25
 */
public enum UserStatus {
    /**
     * 账号正常
     */
    NORMAL(1, "正常"),
    /**
     * 账号封禁
     */
    DISABLE(2, "封禁");
    private final int code;
    private final String info;

    UserStatus(int code, String info) {
        this.code = code;
        this.info = info;
    }

    public int getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}
