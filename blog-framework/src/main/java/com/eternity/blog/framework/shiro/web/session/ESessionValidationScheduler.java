package com.eternity.blog.framework.shiro.web.session;

import com.eternity.blog.common.constants.ShiroConstants;
import com.eternity.blog.common.utils.SpringUtils;
import com.eternity.blog.framework.threadpool.Threads;
import org.apache.shiro.session.mgt.SessionValidationScheduler;
import org.apache.shiro.session.mgt.ValidatingSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author eternity
 * @Date 2020/4/20 0:02
 */
@Component
public class ESessionValidationScheduler implements SessionValidationScheduler {
    private static final Logger log = LoggerFactory.getLogger(ESessionValidationScheduler.class);

    @Autowired
    @Qualifier("sessionManager")
    @Lazy
    private ValidatingSessionManager sessionManager;

    @Value("${shiro.session.validateSessionsPeriod}")
    private int validateSessionsPeriod;

    private volatile boolean enabled = false;

    private ScheduledExecutorService executorService = SpringUtils.getBean("scheduledExecutorService");

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public void enableSessionValidation() {
        enabled = true;
        if (enabled) {
            try {
                executorService.scheduleAtFixedRate(() -> {
                    if (enabled) {
                        sessionManager.validateSessions();
                    }
                }, 1000, validateSessionsPeriod * ShiroConstants.MILLIS_PER_MINUTE, TimeUnit.MILLISECONDS);
                log.info("会话验证启动成功.");
            } catch (Exception e) {
                log.error("会话验证启动失败.");
            }
        }
    }

    @Override
    public void disableSessionValidation() {
        if (enabled) {
            Threads.shutdown(executorService);
        }
        enabled = false;
    }
}
