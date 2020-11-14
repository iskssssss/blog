package com.eternity.blog.framework.shiro.web.session;

import com.eternity.blog.common.enums.OnlineStatus;
import com.eternity.blog.common.utils.BooleanUtils;
import com.eternity.blog.common.utils.DateUtils;
import com.eternity.blog.common.utils.StringUtils;
import com.eternity.blog.framework.shiro.session.OnlineSession;
import com.eternity.blog.system.domain.session.OnlineSessionModel;
import com.eternity.blog.framework.shiro.service.OnlineSessionService;
import org.apache.shiro.session.ExpiredSessionException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @Description 自定义会话管理器
 * @Author eternity
 * @Date 2020/4/18 15:43
 */
public class OnlineWebSessionManager extends DefaultWebSessionManager {
    private static final Logger log = LoggerFactory.getLogger(OnlineWebSessionManager.class);

    @Override
    public void setAttribute(SessionKey sessionKey, Object attributeKey, Object value) throws InvalidSessionException {
        super.setAttribute(sessionKey, attributeKey, value);
    }

    @Override
    public Object removeAttribute(SessionKey sessionKey, Object attributeKey) throws InvalidSessionException {
        return super.removeAttribute(sessionKey, attributeKey);

    }

    @Autowired
    OnlineSessionService sessionService;

    @Override
    public void validateSessions() {
        if (log.isInfoEnabled()) {
            log.info("Validating all active sessions...");
        }

        int invalidCount = 0;
        // 获取过期会话
        List<OnlineSessionModel> activeSessions = sessionService.selectExpiredSessionByDate(
                DateUtils.toString(DateUtils.minus(getGlobalSessionTimeout(), ChronoUnit.MILLIS)));

        if (activeSessions != null && !activeSessions.isEmpty()) {
            Iterator<OnlineSessionModel> iterator = activeSessions.iterator();
            while (iterator.hasNext()) {
                OnlineSessionModel s = iterator.next();
                try {
                    SessionKey key = new DefaultSessionKey(s.getUoSessionId());
                    Session session = retrieveSession(key);
                    if (StringUtils.isNotNull(session)) {
                        throw new InvalidSessionException();
                    }
                    iterator.remove();
                } catch (InvalidSessionException e) {
                    if (log.isDebugEnabled()) {
                        boolean expired = (e instanceof ExpiredSessionException);
                        String msg = "Invalidated session with id [" + s.getUoSessionId() + "]" +
                                (expired ? " (expired)" : " (stopped)");
                        log.debug(msg);
                    }
                    invalidCount++;
                }
            }
            // 删除过期会话
            if (BooleanUtils.toBoolean(activeSessions.size())) {
                try {
                    sessionService.deleteSession(activeSessions);
                } catch (Exception e) {
                    log.error("batch delete session error.", e);
                }
            }
            // 清空过期会话列表
            activeSessions.clear();
        }

        if (log.isInfoEnabled()) {
            String msg = "Finished session validation.";
            if (invalidCount > 0) {
                msg += "  [" + invalidCount + "] sessions were stopped.";
            } else {
                msg += "  No sessions were stopped.";
            }
            log.info(msg);
        }
    }

    @Override
    protected Collection<Session> getActiveSessions() {
        throw new UnsupportedOperationException("getActiveSessions method not supported");
    }
}
