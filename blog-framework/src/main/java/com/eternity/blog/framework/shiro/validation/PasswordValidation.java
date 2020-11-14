package com.eternity.blog.framework.shiro.validation;

import com.eternity.blog.common.exception.user.login.UserPasswordNotMatchesException;
import com.eternity.blog.common.utils.EncryptionUtils;
import com.eternity.blog.common.utils.StringUtils;
import com.eternity.blog.system.domain.user.UserAuthsModel;
import org.springframework.stereotype.Component;

/**
 * @Description 密码验证
 * @Author eternity
 * @Date 2020/4/12 13:33
 */
@Component
public class PasswordValidation {

    public void validate(UserAuthsModel auths, String password) {
        if (!matches(auths.getaCredential(),
                StringUtils.toString(auths.getaUid()),
                password, auths.getaSalt())) {
            // 用户登录密码不匹配异常
            throw new UserPasswordNotMatchesException();
        }
    }

    private boolean matches(String credential, String uid, String password, String salt) {

        return credential.equals(EncryptionUtils.toMd5Hash(uid + password, salt));
    }
}





















