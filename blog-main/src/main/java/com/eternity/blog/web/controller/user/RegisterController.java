package com.eternity.blog.web.controller.user;

import com.eternity.blog.common.core.controller.BaseController;
import com.eternity.blog.common.core.domain.web.BaseAjaxResult;
import com.eternity.blog.common.enums.CredentialStatus;
import com.eternity.blog.common.enums.LoginType;
import com.eternity.blog.common.exception.user.register.PhoneNumberAlreadyExistsException;
import com.eternity.blog.common.exception.user.register.UserRegisterErrorException;
import com.eternity.blog.common.utils.*;
import com.eternity.blog.system.domain.user.RegisterModel;
import com.eternity.blog.system.domain.user.UserAuthsModel;
import com.eternity.blog.system.domain.user.UserModel;
import com.eternity.blog.common.exception.user.UserException;
import com.eternity.blog.common.random.RandomDevice;
import com.eternity.blog.system.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description 注册请求控制器
 * @Author eternity
 * @Date 2020/4/11 16:38
 */
@Controller
public class RegisterController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    IUserService iUserService;

    @GetMapping("/register")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        return "register";
    }

    @ResponseBody
    @PostMapping(value = "/register")
    public BaseAjaxResult ajaxRegister(@RequestParam("phone") String phone,
                                       @RequestParam("nickname") String nickname,
                                       @RequestParam("password") String password,
                                       HttpServletRequest request) {
        try {
            String msg = insert(phone, nickname, password, IpUtils.getIpAddr(request));
            return success("user.register.success", msg);
        } catch (UserException e) {
            return error(e.getMessage());
        }
    }

    String insert(String phone, String nickname, String password, String ip) throws UserException {
        long uid = getUid();
        int aIdentifier = iUserService.checkUserAuthsUnique(phone, LoginType.PHONE.type());
        if (BooleanUtils.toBoolean(aIdentifier)) {
            throw new PhoneNumberAlreadyExistsException();
        }

        addUser(uid, nickname, phone, password, ip);
        return StringUtils.toString(uid);
    }

    /**
     * 添加用户信息
     *
     * @param uid      UID
     * @param nickname 昵称
     * @param phone    手机号
     * @param password 密码
     */
    public void addUser(long uid, String nickname, String phone, String password, String ip) {
        UserModel userModel = new UserModel(uid, nickname, phone);
        boolean result = BooleanUtils.toBoolean(iUserService.insertUser(userModel));
        if (!result) {
            throw new UserRegisterErrorException();
        }
        addUserAuths(userModel.getuId(), phone, password);
        addRegister(uid, ip);
        userModel = null;
    }

    /**
     * 添加用户凭证信息
     *
     * @param uid      UID
     * @param phone    手机号
     * @param password 密码
     */
    public void addUserAuths(long uid, String phone, String password) {
        String salt = EncryptionUtils.randomSalt(uid);
        String md5pwd = EncryptionUtils.toMd5Hash(StringUtils.toString(uid) + password, salt);
        UserAuthsModel authsModel = new UserAuthsModel(uid, md5pwd, salt, CredentialStatus.VERIFIED.code());
        authsModel.setaType(LoginType.UID.type());
        authsModel.setaIdentifier(StringUtils.toString(uid));
        iUserService.insertUserAuths(authsModel);

        authsModel.setaType(LoginType.PHONE.type());
        authsModel.setaIdentifier(phone);
        iUserService.insertUserAuths(authsModel);
        authsModel = null;
    }

    /**
     * 添加用户注册信息
     *
     * @param uid UID
     */
    public void addRegister(long uid, String ip) {
        RegisterModel registerModel = new RegisterModel();
        registerModel.setRlUid(uid);
        registerModel.setRlDate(DateUtils.getDateTime());
        registerModel.setRlIp(ip);
        iUserService.insertRegister(registerModel);
        registerModel = null;
    }

    /**
     * 获取uid
     *
     * @return uid
     */
    private Long getUid() {
        long uid = RandomDevice.randomUid();
        if (BooleanUtils.toBoolean(iUserService.checkUidUnique(uid))) {
            getUid();
        }
        return uid;
    }
}
