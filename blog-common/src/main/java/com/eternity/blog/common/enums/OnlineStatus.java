package com.eternity.blog.common.enums;

/**
 * @Description 用户在线类型
 * @Author eternity
 * @Date 2020/4/16 12:23
 */
public enum OnlineStatus {
    /**
     * 在线
     */
    ON_LINE(1, "在线"),

    /**
     * 离线
     */
    OFF_LINE(2, "离线");

    private final int code;
    private final String strCode;

    /**
     * @param code 状态码
     * @param strCode 状态码（字符串）
     */
    OnlineStatus(int code, String strCode) {
        this.code = code;
        this.strCode = strCode;
    }

    public int code() {
        return code;
    }

    public String strCode() {
        return strCode;
    }

    public static OnlineStatus valueOf(int code) {
        for (OnlineStatus value : OnlineStatus.values()) {
            if (value.code == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("在线状态 不存在.");
    }
}
