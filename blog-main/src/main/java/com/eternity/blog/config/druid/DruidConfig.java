package com.eternity.blog.config.druid;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @Description
 * @Author eternity
 * @Date 2020/4/19 13:50
 */
@Configuration
public class DruidConfig {
    private static final Logger log = LoggerFactory.getLogger(DruidConfig.class);

    @Bean
    @ConfigurationProperties("spring.datasource.druid.master")
    public DataSource masterDataSource(DruidDbConfig druidDbConfig) {
        log.info("配置主数据库");
        return druidDbConfig.dataSource(DruidDataSourceBuilder.create().build());
    }

    /*@Bean
    @ConfigurationProperties("spring.datasource.druid.slave")
    @ConditionalOnProperty(prefix = "spring.datasource.druid.slave", name = "enabled", havingValue = "true")
    public DataSource slaveDataSource(DruidDbConfig druidDbConfig) {
        log.info("配置从数据库");
        return druidDbConfig.dataSource(DruidDataSourceBuilder.create().build());
    }*/
}
