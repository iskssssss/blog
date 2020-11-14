package com.eternity.blog.common.enums;

/**
 * @Description 登陆类型
 * @Author eternity
 * @Date 2020/4/14 22:33
 */
public enum  LoginType {
    /**
     * 系统账号
     */
    UID(0, "系统账号"),
    /**
     * 手机号
     */
    PHONE(1, "手机号");

    private final int type;
    private final String strType;

    LoginType(int type, String strType) {
        this.type = type;
        this.strType = strType;

    }

    public int type() {
        return type;
    }

    public String strType() {
        return strType;
    }

    public static LoginType valueOf(int type) {
        for (LoginType value : LoginType.values()) {
            if (value.type == type) {
                return value;
            }
        }
        throw new IllegalArgumentException("该账号类型不存在.");
    }
}
