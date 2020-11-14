package com.eternity.blog.system.service.impl;

import com.eternity.blog.common.enums.LoginType;
import com.eternity.blog.system.domain.user.LoginLogModel;
import com.eternity.blog.system.domain.user.RegisterModel;
import com.eternity.blog.system.domain.user.UserAuthsModel;
import com.eternity.blog.system.domain.user.UserModel;
import com.eternity.blog.system.mapper.IUserMapper;
import com.eternity.blog.system.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description 用户管理 业务层实现
 * @Author eternity
 * @Date 2020/4/10 17:29
 */
@Service
public class UserServiceImpl implements IUserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    IUserMapper iUserMapper;

    @Override
    public UserModel selectUserByUid(Long userUid) {
        log.info(String.format("查询用户(UID)的信息. UID：%d", userUid));
        return iUserMapper.selectUserByUid(userUid);
    }

    @Override
    public UserModel selectUserByPhone(String userPhone) {
        log.info(String.format("查询用户(Phone)的信息. Phone：%s", userPhone));
        return iUserMapper.selectUserByPhone(userPhone);
    }

    @Override
    public int checkUidUnique(Long userUid) {
        log.info(String.format("校验用户的UID是否唯一. UID：[%d]", userUid));
        return iUserMapper.checkUidUnique(userUid);
    }

    @Override
    public int insertUser(UserModel user) {
        log.info(String.format("添加用户信息. UID：[%d]", user.getuId()));
        return iUserMapper.insertUser(user);
    }

    @Override
    public int insertUserAuths(UserAuthsModel authsModel) {
        log.info(String.format("添加用户凭证信息. UID：[%d] 凭证类型：%s", authsModel.getaUid(), (LoginType.valueOf(authsModel.getaType())).strType()));
        return iUserMapper.insertUserAuths(authsModel);
    }

    @Override
    public int insertRegister(RegisterModel registerModel) {
        log.info(String.format("添加用户注册信息. UID：[%d]", registerModel.getRlUid()));
        return iUserMapper.insertRegister(registerModel);
    }

    @Override
    public int checkUserAuthsUnique(String aIdentifier, int aType) {
        log.info(String.format("校验用户凭证是否唯一. %s：[%s]", (LoginType.valueOf(aType)).strType(), aIdentifier));
        return iUserMapper.checkUserAuthsUnique(aIdentifier, aType);
    }

    @Override
    public int insertLoginLog(LoginLogModel loginLogModel) {
        log.info(String.format("添加用户登录记录. UID：[%d]", loginLogModel.getLlUid()));
        return iUserMapper.insertLoginLog(loginLogModel);
    }
}
