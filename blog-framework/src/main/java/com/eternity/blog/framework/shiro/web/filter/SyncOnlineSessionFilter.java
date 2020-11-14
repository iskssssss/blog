package com.eternity.blog.framework.shiro.web.filter;

import com.eternity.blog.common.constants.ShiroConstants;
import com.eternity.blog.common.enums.OnlineStatus;
import com.eternity.blog.common.utils.SpringUtils;
import com.eternity.blog.common.utils.StringUtils;
import com.eternity.blog.framework.shiro.service.OnlineSessionService;
import com.eternity.blog.framework.shiro.session.OnlineSession;
import com.eternity.blog.framework.shiro.session.OnlineSessionDAO;
import com.eternity.blog.framework.utill.ShiroUtils;
import com.eternity.blog.system.domain.user.UserModel;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @Description 自定义同步过滤器
 * @Author eternity
 * @Date 2020/4/18 17:03
 */
public class SyncOnlineSessionFilter extends PathMatchingFilter {

    @Autowired
    private OnlineSessionDAO sessionDAO;

    /**
     * 同步会话至数据库
     */
    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        OnlineSession session = (OnlineSession) request.getAttribute(ShiroConstants.ONLINE_SESSION);
        UserModel user = ShiroUtils.getUserModel();
        if (StringUtils.isNull(user) && StringUtils.isNotNull(session.getUoUid())){
            SpringUtils.getBean(OnlineSessionService.class).deleteSession(session.getId());
            session.setUoStatus(null);
            session.setUoUid(null);
            return true;
        }
        if (StringUtils.isNotNull(session) &&
                StringUtils.isNotNull(session.getUoUid()) &&
                StringUtils.isNull(session.getStopTimestamp())) {
            sessionDAO.syncToDb(session);
        }
        return true;
    }
}
