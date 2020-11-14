package com.eternity.blog.framework.shiro.validation;

import com.eternity.blog.common.enums.LoginType;
import com.eternity.blog.common.enums.UserStatus;
import com.eternity.blog.common.exception.user.UserAccountDisableException;
import com.eternity.blog.common.utils.BooleanUtils;
import com.eternity.blog.common.utils.DateUtils;
import com.eternity.blog.common.utils.StringUtils;
import com.eternity.blog.framework.utill.ShiroUtils;
import com.eternity.blog.system.domain.user.LoginLogModel;
import com.eternity.blog.system.domain.user.UserModel;
import com.eternity.blog.common.exception.user.login.UserNotExistsException;
import com.eternity.blog.system.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description 登陆认证
 * @Author eternity
 * @Date 2020/4/12 0:39
 */
@Component
public class LoginValidation {
    private static final Logger log = LoggerFactory.getLogger(LoginValidation.class);

    @Autowired
    PasswordValidation passwordValidation;

    @Autowired
    IUserService iUserService;

    /**
     * 手机号码格式限制
     */
    public static final String MOBILE_PHONE_NUMBER_FORMAT = "^0{0,1}(13[0-9]|15[0-9]|14[0-9]|18[0-9])[0-9]{8}$";
    /**
     * 纯数字格式限制
     */
    public static final String DIGITAL_FORMAT = "^-?\\d+(\\.\\d+)?$";

    /**
     * 登陆验证
     *
     * @param user     账号名
     * @param password 密码
     * @return 用户信息
     */
    public UserModel login(String user, String password) {
        UserModel userModel = getUser(user);
        if (StringUtils.isNull(userModel)) {
            throw new UserNotExistsException();
        }

        if (UserStatus.DISABLE.getCode() == userModel.getuStatus()) {
            throw new UserAccountDisableException();
        }
        passwordValidation.validate(userModel.getUserAuthsModel(), password);
        if (!BooleanUtils.toBoolean(addLoginLog(userModel.getuId()))){
            log.error("添加用户登陆记录失败。");
        }
        userModel.setUserAuthsModel(null);
        return userModel;
    }

    /**
     * 添加用户登陆记录
     * @param uid UID
     * @return 结果
     */
    private int addLoginLog(Long uid) {
        LoginLogModel loginLogModel = new LoginLogModel();
        loginLogModel.setLlUid(uid);
        loginLogModel.setLlDate(DateUtils.getDateTime());
        loginLogModel.setLlIp(ShiroUtils.getIp());
        loginLogModel.setLlType(LoginType.UID.type());
        return iUserService.insertLoginLog(loginLogModel);
    }

    /**
     * 获取用户信息
     *
     * @param user 账号名
     * @return 用户信息
     */
    private UserModel getUser(String user) {
        if (user.matches(MOBILE_PHONE_NUMBER_FORMAT)) {
            return iUserService.selectUserByPhone(user);
        } else if (user.matches(DIGITAL_FORMAT)) {
            return iUserService.selectUserByUid(StringUtils.toLong(user));
        } else {
            return null;
        }
    }
}
