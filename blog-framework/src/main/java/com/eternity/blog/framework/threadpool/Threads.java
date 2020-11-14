package com.eternity.blog.framework.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Description 线程管理
 * @Author eternity
 * @Date 2020/4/20 0:10
 */
public class Threads {
    private static final Logger logger = LoggerFactory.getLogger(Threads.class);

    public static void shutdown(ScheduledExecutorService executorService) {
        if (executorService.isShutdown()) {
            return;
        }
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
                if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                    logger.info("数据连接池关闭失败.");
                    return;
                }
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
