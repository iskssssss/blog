package com.eternity.blog;

import com.eternity.blog.common.constants.RedisKeyFormatConstants;
import com.eternity.blog.common.utils.RedisUtils;
import com.eternity.blog.quartz.utils.SchedulerUtils;
import com.eternity.blog.web.controller.blog.BlogController;
import com.eternity.blog.web.dto.CommentUser;
import org.junit.jupiter.api.Test;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.ui.ModelMap;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class RedisApplicationTest {
	private static final Logger log = LoggerFactory.getLogger(RedisApplicationTest.class);

	@Autowired
	@Qualifier("redisTemplate")
	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	private RedisUtils redisUtil;

	@Test
	void set() {
		int k = 18880;
		while (k > 0) {
			System.out.println(String.format("%d / 2 = %d ... %d", k, k / 2, k % 2));
			k /= 2;
		}
	}

	@Autowired
	Scheduler scheduler;

	@Test
	void quartz() throws SchedulerException {
		Map<Object, Object> blogMap = redisUtil.hashGet(BlogController.blogListKey);
		System.out.println(blogMap.size());
//		SchedulerUtils.crateRedisSaveTask(scheduler);
//		while (true) {
//
//		}
	}

}
