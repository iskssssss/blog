package com.eternity.blog.system.service.impl;

import com.eternity.blog.system.domain.session.OnlineSessionModel;
import com.eternity.blog.system.mapper.IOnlineSessionMapper;
import com.eternity.blog.system.service.IOnlineSessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description 会话管理 业务层实现
 * @Author eternity
 * @Date 2020/4/19 11:44
 */
@Service
public class OnlineSessionServiceImpl implements IOnlineSessionService {
    private static final Logger log = LoggerFactory.getLogger(OnlineSessionServiceImpl.class);

    @Autowired
    private IOnlineSessionMapper sessionMapper;

    @Override
    public int insertSession(OnlineSessionModel sessionModel) {
        log.info(String.format("添加Session的信息. SessionId：%s", sessionModel.getUoSessionId()));
        return sessionMapper.insertSession(sessionModel);
    }

    @Override
    public int deleteSession(String sessionId) {
        log.info(String.format("删除Session的信息. SessionId：%s", sessionId));
        return sessionMapper.deleteSession(sessionId);
    }

    @Override
    public int updateSession(OnlineSessionModel sessionModel) {
        log.info(String.format("修改Session的信息. SessionId：%s", sessionModel.getUoSessionId()));
        return sessionMapper.updateSession(sessionModel);
    }

    @Override
    public OnlineSessionModel selectSessionBySessionId(String sessionId) {
        log.info(String.format("查询Session的信息. SessionId：%s", sessionId));
        return sessionMapper.selectSessionBySessionId(sessionId);
    }

    @Override
    public List<OnlineSessionModel> selectExpiredSessionByDate(String expiredDate) {
        log.info(String.format("查询过期Session的信息. expiredDate：%s", expiredDate));
        return sessionMapper.selectExpiredSessionByDate(expiredDate);
    }
}
