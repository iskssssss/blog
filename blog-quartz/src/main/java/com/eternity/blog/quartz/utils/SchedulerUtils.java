package com.eternity.blog.quartz.utils;

import com.eternity.blog.quartz.task.RedisSaveTask;
import org.quartz.*;

/**
 * @Description
 * @Author eternity
 * @Date 2020/6/20 15:43
 */
public class SchedulerUtils {

    /**
     * 创建Redis定时保存任务
     */
    public static void crateRedisSaveTask(Scheduler scheduler) throws SchedulerException {
        System.out.println("创建Redis定时保存任务...");
        JobDetail jobDetail = JobBuilder.newJob(RedisSaveTask.class)
                .withIdentity("task", "save").build();
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0 30 * * * ? *");
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("task", "save")
                .withDescription("")
                .startNow()
                .withSchedule(cronScheduleBuilder).build();
        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.start();
        System.out.println("Redis定时保存任务创建成功...");
    }
}
