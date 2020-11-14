package com.eternity.blog.common.exception.user.login;

import com.eternity.blog.common.exception.user.UserException;

/**
 * @Description 用户登录密码不匹配异常
 * @Author eternity
 * @Date 2020/4/14 0:09
 */
public class UserPasswordNotMatchesException extends UserException {
    private static final long serialVersionUID = 42L;

    public UserPasswordNotMatchesException() {
        super("user.login.password.not.matches");
    }
}
