package com.eternity.blog.framework.threadpool;

import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.eternity.blog.common.utils.SpringUtils;

/**
 * @Description 异步任务
 * @Author eternity
 * @Date 2020/4/19 18:42
 */
public class AsyncManager {
    private static AsyncManager asyncManager = new AsyncManager();

    private ScheduledExecutorService executorService = SpringUtils.getBean("scheduledExecutorService");

    private AsyncManager() {
    }

    public static AsyncManager me() {
        return asyncManager;
    }

    public void execute(TimerTask task) {
        executorService.schedule(task, 10, TimeUnit.MILLISECONDS);
    }

    public void shutdown() {
        Threads.shutdown(executorService);
    }
}
