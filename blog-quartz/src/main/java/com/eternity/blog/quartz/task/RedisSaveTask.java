package com.eternity.blog.quartz.task;

import com.eternity.blog.common.constants.RedisKeyFormatConstants;
import com.eternity.blog.common.utils.RedisUtils;
import com.eternity.blog.common.utils.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import java.util.Map;

/**
 * @Description Redis 定时缓存至数据库中
 * @Author eternity
 * @Date 2020/6/20 15:09
 */
public class RedisSaveTask implements Job {
    private static Logger log = LoggerFactory.getLogger(RedisSaveTask.class);

    @Autowired
    private RedisUtils redisUtil;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Map<Object, Object> blogMap = redisUtil.hashGet("BLOG-LIST");
        if (StringUtils.isNotNull(blogMap)) {
            blogMap.forEach((k, y) -> {
                log.info(k + "已保存。");
            });
        }
        log.info("保存任务执行完毕。");
    }
}
