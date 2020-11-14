package com.eternity.blog;

import com.eternity.blog.quartz.utils.SchedulerUtils;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @Description 启动类
 * @Author eternity
 * @Date 2020/4/10 15:52
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class BlogMainApplication {

	public static void main(String[] args)  {
		SpringApplication.run(BlogMainApplication.class, args);
		System.out.println(
				"/////////////////\n" +
						"//   启动成功   //\n" +
						"/////////////////"
		);
	}
}
