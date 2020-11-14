package com.eternity.blog.common.enums;

/**
 * @Description 登陆凭证 状态
 * @Author eternity
 * @Date 2020/4/17 11:25
 */
public enum CredentialStatus {
    /**
     * 登陆凭证已验证
     */
    VERIFIED(1, "已验证"),
    /**
     * 登陆凭证未验证
     */
    UNVERIFIED(0,"未验证");

    private int code;
    private String strCode;
    CredentialStatus(int code, String strCode){
        this.code= code;
        this.strCode= strCode;
    }


    public int code() {
        return code;
    }

    public String strCode() {
        return strCode;
    }

    public static CredentialStatus valueOf(int code) {
        for (CredentialStatus value : CredentialStatus.values()) {
            if (value.code == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("登陆凭证状态 不存在.");
    }

}
