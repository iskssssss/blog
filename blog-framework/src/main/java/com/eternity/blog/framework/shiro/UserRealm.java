package com.eternity.blog.framework.shiro;

import com.eternity.blog.common.exception.user.UserAccountDisableException;
import com.eternity.blog.common.exception.user.login.UserPasswordNotMatchesException;
import com.eternity.blog.common.utils.StringUtils;
import com.eternity.blog.framework.utill.ShiroUtils;
import com.eternity.blog.system.domain.user.UserModel;
import com.eternity.blog.common.exception.user.login.UserNotExistsException;
import com.eternity.blog.framework.shiro.validation.LoginValidation;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description
 * @Author eternity
 * @Date 2020/4/10 19:10
 */
public class UserRealm extends AuthorizingRealm {
    private static final Logger log = LoggerFactory.getLogger(UserRealm.class);
    @Autowired
    LoginValidation loginValidation;

    /**
     * 权限授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        UserModel userModel = ShiroUtils.getUserModel();
        if (StringUtils.isNull(userModel)) {
            return null;
        }
        log.info(String.format("查询用户{%s}的授权信息 -> doGetAuthorizationInfo", userModel.getuNickname()));
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermission("");
        return info;
    }

    /**
     * 登录认证
     *
     * @param authenticationToken 身份凭证
     * @return 身份信息
     * @throws AuthenticationException 认证异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        UserModel userModel;
        try {
            userModel = loginValidation.login(token.getUsername(), StringUtils.toString(token.getPassword()));
        } catch (UserNotExistsException e) {
            throw new UnknownAccountException(e.getMessage(), e);
        } catch (UserPasswordNotMatchesException e) {
            throw new IncorrectCredentialsException(e.getMessage(), e);
        } catch (UserAccountDisableException e) {
            throw new AccountException(e.getMessage(), e);
        }
        return new SimpleAuthenticationInfo(userModel, StringUtils.toString(token.getPassword()), getName());
    }
}
