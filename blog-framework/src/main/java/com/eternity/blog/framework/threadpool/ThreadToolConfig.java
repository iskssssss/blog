package com.eternity.blog.framework.threadpool;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @Description
 * @Author eternity
 * @Date 2020/4/19 18:46
 */
@Configuration
public class ThreadToolConfig {

    @Bean(name = "scheduledExecutorService")
    protected ScheduledExecutorService scheduledExecutorService() {
        return new ScheduledThreadPoolExecutor(50,
                new BasicThreadFactory.Builder().namingPattern("task-pool-%d").daemon(true).build());
    }
}
