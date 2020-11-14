package com.eternity.blog.framework.shiro.session;

import com.eternity.blog.common.constants.ShiroConstants;
import com.eternity.blog.common.enums.OnlineStatus;
import com.eternity.blog.common.utils.StringUtils;
import com.eternity.blog.framework.shiro.service.OnlineSessionService;
import com.eternity.blog.framework.threadpool.AsyncManager;
import com.eternity.blog.framework.threadpool.AsyncTaskFactory;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description 自定义SessionDAO
 * @Author eternity
 * @Date 2020/4/15 23:10
 */
public class OnlineSessionDAO extends EnterpriseCacheSessionDAO {
    private static final Logger log = LoggerFactory.getLogger(OnlineSessionDAO.class);

    @Autowired
    OnlineSessionService sessionService;

    @Value("${shiro.session.syncPeriod}")
    private int syncInterval;

    private final String lastSyncDate = OnlineSession.class.getName() + "-LAST_SYNC_DATE";

    public OnlineSessionDAO() {
        super();
    }

    /**
     * 根据会话id获取会话
     *
     * @param sessionId 会话id
     * @return 会话
     */
    @Override
    protected Session doReadSession(Serializable sessionId) {
        log.info("(doReadSession)获取数据库中的会话...");
        return sessionService.selectSessionBySessionId(sessionId);
    }

    /**
     * 更新会话
     *
     * @param session 会话
     */
    public void syncToDb(OnlineSession session) {
        Date dateTime = (Date) session.getAttribute(lastSyncDate);
        if (StringUtils.isNotNull(dateTime)) {
            long timeDifference = session.getLastAccessTime().getTime() - dateTime.getTime();
            // 小于同步周期时不需要同步
            if (timeDifference < syncInterval * ShiroConstants.MILLIS_PER_MINUTE) {
                return;
            }

            // 未登录且session未改变时不需要同步
            if (StringUtils.isNull(session.getUoUid()) && !session.isAttributeChanged()) {
                return;
            }
        }
        session.setAttribute(lastSyncDate, session.getLastAccessTime());
        if (session.isAttributeChanged()) {
            session.resetAttributeChanged();
        }
        log.info(String.format("(syncToDb)更新会话：%s", session.toString()));
        AsyncManager.me().execute(AsyncTaskFactory.asyncSaveSessionTask(session));
    }

    /**
     * 当会话过期或停止后触发
     *
     * @param session 会话
     */
    @Override
    protected void doDelete(Session session) {
        log.info("(doDelete)当会话过期或停止后触发...");
        OnlineSession onlineSession = (OnlineSession) session;
        if (StringUtils.isNull(onlineSession) || StringUtils.isNull(onlineSession.getUoUid())) {
            return;
        }
        onlineSession.setUoStatus(OnlineStatus.OFF_LINE);
        sessionService.deleteSession(onlineSession.getId());
    }
}
