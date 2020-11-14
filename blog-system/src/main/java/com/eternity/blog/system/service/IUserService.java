package com.eternity.blog.system.service;

import com.eternity.blog.system.domain.user.LoginLogModel;
import com.eternity.blog.system.domain.user.RegisterModel;
import com.eternity.blog.system.domain.user.UserAuthsModel;
import com.eternity.blog.system.domain.user.UserModel;

/**
 * @Description 用户管理 业务层
 * @Author eternity
 * @Date 2020/4/10 17:28
 */
public interface IUserService {
    /**
     * 查询用户信息(Uid)
     *
     * @param userUid 用户名称
     * @return 用户信息
     */
   UserModel selectUserByUid(Long userUid);

    /**
     * 查询用户信息(手机号)
     *
     * @param userPhone 手机号
     * @return 用户信息
     */
   UserModel selectUserByPhone(String userPhone);

    /**
     * 校验用户Uid是否唯一
     * @param userUid 账号
     * @return 结果
     */
   int checkUidUnique(Long userUid);

    /**
     * 添加用户
     * @param user 用户信息
     * @return 结果
     */
   int insertUser(UserModel user);

    /**
     *  添加用户验证信息
     * @param authsModel 用户验证信息
     * @return 结果
     */
   int insertUserAuths(UserAuthsModel authsModel);

    /**
     *  添加用户注册信息
     * @param registerModel 用户验证信息
     * @return 结果
     */
   int insertRegister(RegisterModel registerModel);


    /**
     * 校验用户验证是否唯一
     * @param aIdentifier 身份标识
     * @param aType 验证类型
     * @return 结果
     */
   int checkUserAuthsUnique(String aIdentifier, int aType);


    /**
     * 记录用户登录
     * @param loginLogModel 登录信息
     * @return 结果
     */
   int insertLoginLog(LoginLogModel loginLogModel);
}
