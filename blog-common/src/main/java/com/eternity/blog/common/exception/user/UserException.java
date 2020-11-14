package com.eternity.blog.common.exception.user;

import com.eternity.blog.common.utils.I18nUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description 用户信息异常
 * @Author eternity
 * @Date 2020/4/13 14:00
 */
public class UserException extends RuntimeException {
    private static final long serialVersionUID = 42L;
    private static final Logger log = LoggerFactory.getLogger(UserException.class);

    public UserException(String error) {
        super(error);
        log.error(I18nUtils.message(error));
    }
}