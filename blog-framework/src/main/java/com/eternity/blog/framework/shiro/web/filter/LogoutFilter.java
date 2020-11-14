package com.eternity.blog.framework.shiro.web.filter;

import com.eternity.blog.common.utils.StringUtils;
import com.eternity.blog.framework.utill.ShiroUtils;
import com.eternity.blog.system.domain.user.UserModel;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.AdviceFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @Description
 * @Author eternity
 * @Date 2020/5/6 18:09
 */
public class LogoutFilter extends org.apache.shiro.web.filter.authc.LogoutFilter {
    private static final Logger log = LoggerFactory.getLogger(LogoutFilter.class);

    private String logoutUrl;

    public String getLogoutUrl() {
        return logoutUrl;
    }

    public void setLogoutUrl(String logoutUrl) {
        this.logoutUrl = logoutUrl;
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        try {
            Subject subject = getSubject(request, response);
            String redirectUrl = getRedirectUrl(request, response, subject);
            try {
                UserModel user = ShiroUtils.getUserModel();
                String username = "user";
                if (StringUtils.isNotNull(user)) {
                    username = user.getuNickname();
                    // TODO 登录用户退出处理...
                }
                subject.logout();
                log.info(String.format("%s logout.", username));
            } catch (SessionException ise) {
                log.error("logout fail.", ise);
            }
            issueRedirect(request, response, redirectUrl);
        } catch (SessionException ise) {
            log.debug("Encountered session exception during logout.  This can generally safely be ignored.", ise);
        }
        return false;
    }

    @Override
    protected String getRedirectUrl(ServletRequest request, ServletResponse response, Subject subject) {
        String url = getLogoutUrl();
        if (StringUtils.isNotEmpty(url)) {
            return url;
        }
        return super.getRedirectUrl(request, response, subject);
    }
}
