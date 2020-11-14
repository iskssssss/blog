package com.eternity.blog.config;

import com.eternity.blog.quartz.utils.SchedulerUtils;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author eternity
 * @Date 2020/6/20 20:58
 */
@Configuration
public class SchedulerConfig {

    @Autowired
    Scheduler scheduler;

    @Bean
    void startRedisSaveTask() throws SchedulerException {
        SchedulerUtils.crateRedisSaveTask(scheduler);
    }
}
