package com.eternity.blog.framework.utill;

import com.eternity.blog.common.utils.BeanUtils;
import com.eternity.blog.common.utils.StringUtils;
import com.eternity.blog.system.domain.user.UserModel;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * @Description Shiro 工具类
 * @Author eternity
 * @Date 2020/4/14 16:37
 */
public class ShiroUtils {

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static Session getSession() {
        return getSubject().getSession();
    }

    /**
     * 获取IP地址
     *
     * @return ip
     */
    public static String getIp() {
        String ip = getSubject().getSession().getHost();
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    public static UserModel getUserModel() {
        Object object = getSubject().getPrincipal();
        if (StringUtils.isNull(object)) {
            return null;
        }
        UserModel user = new UserModel();
        BeanUtils.copyBeanProperties(object, user);
        return user;
    }
}
