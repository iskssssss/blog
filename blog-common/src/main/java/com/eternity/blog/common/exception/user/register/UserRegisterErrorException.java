package com.eternity.blog.common.exception.user.register;

import com.eternity.blog.common.exception.user.UserException;

/**
 * @Description 注册失败异常
 * @Author eternity
 * @Date 2020/4/13 22:54
 */
public class UserRegisterErrorException extends UserException {
    private static final long serialVersionUID = 42L;

    public UserRegisterErrorException() {
        super("user.register.error");
    }
}