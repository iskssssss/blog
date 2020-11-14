package com.eternity.blog.common.exception.user;

/**
 * @Description 账号锁定异常
 * @Author eternity
 * @Date 2020/4/14 0:17
 */
public class UserAccountLockException extends UserException {
    private static final long serialVersionUID = 42L;

    public UserAccountLockException() {
        super("user.account.lock");
    }
}
