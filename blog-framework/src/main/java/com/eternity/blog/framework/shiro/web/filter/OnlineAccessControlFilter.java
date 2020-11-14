package com.eternity.blog.framework.shiro.web.filter;

import com.eternity.blog.common.constants.ShiroConstants;
import com.eternity.blog.common.enums.OnlineStatus;
import com.eternity.blog.common.utils.StringUtils;
import com.eternity.blog.framework.shiro.session.OnlineSession;
import com.eternity.blog.framework.shiro.session.OnlineSessionDAO;
import com.eternity.blog.framework.shiro.session.OnlineSessionFactory;
import com.eternity.blog.framework.utill.ShiroUtils;
import com.eternity.blog.system.domain.user.UserModel;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * @Description 自定义处理过滤器
 * @Author eternity
 * @Date 2020/4/18 17:19
 */
public class OnlineAccessControlFilter extends AccessControlFilter {
    private static final Logger log = LoggerFactory.getLogger(OnlineAccessControlFilter.class);

    @Value("${shiro.user.loginUrl}")
    private String loginUrl;

    @Autowired
    OnlineSessionDAO sessionDAO;

    /**
     * 是否允许访问 true允许访问 false拒绝访问
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        Subject subject = getSubject(servletRequest, servletResponse);
        if (StringUtils.isNull(subject) || StringUtils.isNull(subject.getSession())) {
            return true;
        }
        Session session = sessionDAO.readSession(subject.getSession().getId());
        if (StringUtils.isNull(session) || !(session instanceof OnlineSession)) {
            return true;
        }
        OnlineSession onlineSession = ((OnlineSession) session);
        servletRequest.setAttribute(ShiroConstants.ONLINE_SESSION, onlineSession);
        if (StringUtils.isNull(onlineSession.getUoUid())) {
            UserModel user = ShiroUtils.getUserModel();
            if (StringUtils.isNotNull(user)) {
                onlineSession.setUoUid(user.getuId());
                onlineSession.setUoStatus(OnlineStatus.ON_LINE);
                onlineSession.markAttributeChanged();
            }
        }
        return onlineSession.getUoStatus() != OnlineStatus.OFF_LINE;
    }

    /**
     * 当拒绝访问时是否继续已经处理了 返回false表示处理完成
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        Subject subject = getSubject(servletRequest, servletResponse);
        if (StringUtils.isNotNull(subject)) {
            subject.logout();
        }
        saveRequestAndRedirectToLogin(servletRequest, servletResponse);
        return false;
    }

    /**
     * 重定向至登录界面
     */
    @Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        WebUtils.issueRedirect(request, response, loginUrl);
    }
}
