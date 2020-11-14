package com.eternity.blog.common.exception.user.register;

import com.eternity.blog.common.exception.user.UserException;

/**
 * @Description 手机号已存在异常
 * @Author eternity
 * @Date 2020/4/13 22:27
 */
public class PhoneNumberAlreadyExistsException extends UserException {
    private static final long serialVersionUID = 42L;

    public PhoneNumberAlreadyExistsException() {
        super("user.register.phone.number.exists");
    }
}