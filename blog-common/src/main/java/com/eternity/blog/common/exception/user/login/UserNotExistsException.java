package com.eternity.blog.common.exception.user.login;


import com.eternity.blog.common.exception.user.UserException;

/**
 * @Description 用户登录名不存在异常
 * @Author eternity
 * @Date 2020/4/13 22:41
 */
public class UserNotExistsException extends UserException {
    private static final long serialVersionUID = 42L;

    public UserNotExistsException() {
        super("user.login.not.exists");
    }
}
