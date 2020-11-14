package com.eternity.blog.framework.shiro.service;

import com.eternity.blog.common.enums.OnlineStatus;
import com.eternity.blog.common.utils.DateUtils;
import com.eternity.blog.common.utils.StringUtils;
import com.eternity.blog.framework.shiro.session.OnlineSession;
import com.eternity.blog.system.domain.session.OnlineSessionModel;
import com.eternity.blog.system.service.IOnlineSessionService;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

/**
 * @Description
 * @Author eternity
 * @Date 2020/4/19 16:05
 */
@Component
public class OnlineSessionService {

    @Autowired
    private IOnlineSessionService sessionService;

    /**
     * 删除会话
     *
     * @param sessionId 会话ID
     * @return 结果
     */
    public int deleteSession(Serializable sessionId) {
        return sessionService.deleteSession(sessionId.toString());
    }

    /**
     * 批量删除会话
     *
     * @param sessionModelList 会话列表
     */
    public void deleteSession(List<OnlineSessionModel> sessionModelList) {
        for (OnlineSessionModel sessionModel : sessionModelList) {
            sessionService.deleteSession(sessionModel.getUoSessionId());
        }
    }

    /**
     * 获取会话
     *
     * @param sessionId 会话ID
     * @return 结果
     */
    public Session selectSessionBySessionId(Serializable sessionId) {
        OnlineSessionModel sessionModel = sessionService.selectSessionBySessionId(sessionId.toString());
        return StringUtils.isNull(sessionModel) ? null : toOnlineSession(sessionModel);
    }

    /**
     * 获取过期会话列表
     *
     * @param expiredDate 过期时间
     * @return 结果
     */
    public List<OnlineSessionModel> selectExpiredSessionByDate(String expiredDate) {
        return sessionService.selectExpiredSessionByDate(expiredDate);
    }


    /**
     * OnlineSessionModel -> OnlineSession
     *
     * @param sessionModel 会话信息（数据库）
     * @return 会话信息（内存）
     */
    private Session toOnlineSession(OnlineSessionModel sessionModel) {
        OnlineSession session = new OnlineSession();
        if (StringUtils.isNotNull(sessionModel)) {
            session.setId(sessionModel.getUoSessionId());
            session.setUoUid(sessionModel.getUoUid());
            session.setHost(sessionModel.getUoIp());
            session.setUoBrowser(sessionModel.getUoBrowser());
            session.setUoOs(sessionModel.getUoOs());
            session.setUoStatus(OnlineStatus.valueOf(sessionModel.getUoStatus()));
            session.setStartTimestamp(DateUtils.toDate(sessionModel.getUoStartDate()));
            session.setLastAccessTime(DateUtils.toDate(sessionModel.getUoLastDate()));
            session.setTimeout(sessionModel.getUoExpireTime());
        }
        return session;
    }
}
