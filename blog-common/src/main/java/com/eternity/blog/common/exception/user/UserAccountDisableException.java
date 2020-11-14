package com.eternity.blog.common.exception.user;

/**
 * @Description 用户帐户封禁异常
 * @Author eternity
 * @Date 2020/4/14 0:36
 */
public class UserAccountDisableException extends UserException {
    private static final long serialVersionUID = 42L;

    public UserAccountDisableException() {
        super("user.account.disable");
    }
}