package com.eternity.blog.framework.shiro.session;

import com.eternity.blog.common.utils.IpUtils;
import com.eternity.blog.common.utils.ServletUtils;
import com.eternity.blog.common.utils.StringUtils;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.web.session.mgt.WebSessionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description 自定义 Session 会话
 * @Author eternity
 * @Date 2020/4/18 11:20
 */
public class OnlineSessionFactory implements SessionFactory {
    private static final Logger log = LoggerFactory.getLogger(OnlineSessionFactory.class);

    @Override
    public Session createSession(SessionContext initData) {
        OnlineSession session = new OnlineSession();
        if (StringUtils.isNotNull(initData) && initData instanceof WebSessionContext) {
            WebSessionContext sessionContext = (WebSessionContext) initData;
            HttpServletRequest servletSession = (HttpServletRequest) sessionContext.getServletRequest();
            if (StringUtils.isNotNull(servletSession)) {
                UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getHttpServletRequest().getHeader("User-Agent"));
                String os = userAgent.getOperatingSystem().getName();
                String browser = userAgent.getBrowser().getName();
                session.setHost(IpUtils.getIpAddr(servletSession));
                session.setUoOs(os);
                session.setUoBrowser(browser);
                log.info("(createSession)创建会话.");
            }
        }
        return session;
    }
}
