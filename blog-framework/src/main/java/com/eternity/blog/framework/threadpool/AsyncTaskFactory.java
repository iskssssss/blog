package com.eternity.blog.framework.threadpool;

import com.eternity.blog.common.utils.DateUtils;
import com.eternity.blog.common.utils.SpringUtils;
import com.eternity.blog.system.domain.session.OnlineSessionModel;
import com.eternity.blog.framework.shiro.session.OnlineSession;
import com.eternity.blog.system.service.IOnlineSessionService;

import java.util.TimerTask;

/**
 * @Description 异步任务 集合
 * @Author eternity
 * @Date 2020/4/19 19:21
 */
public class AsyncTaskFactory {
    /**
     * 保存会话 任务
     *
     * @param session 会话
     * @return TimerTask
     */
    public static TimerTask asyncSaveSessionTask(final OnlineSession session) {
        return new TimerTask() {
            @Override
            public void run() {
                OnlineSessionModel sessionModel = new OnlineSessionModel();
                sessionModel.setUoSessionId(session.getId().toString());
                sessionModel.setUoUid(session.getUoUid());
                sessionModel.setUoIp(session.getHost());
                sessionModel.setUoBrowser(session.getUoBrowser());
                sessionModel.setUoOs(session.getUoOs());
                sessionModel.setUoStatus(session.getUoStatus().code());
                sessionModel.setUoStartDate(DateUtils.toString(session.getStartTimestamp()));
                sessionModel.setUoLastDate(DateUtils.toString(session.getLastAccessTime()));
                sessionModel.setUoExpireTime(session.getTimeout());
                SpringUtils.getBean(IOnlineSessionService.class).insertSession(sessionModel);
            }
        };
    }
}
