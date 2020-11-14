package com.eternity.blog.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * @Description Spring工具类
 * @Author eternity
 * @Date 2020/4/13 19:38
 */
@Component
public class SpringUtils implements BeanFactoryPostProcessor {
    /**
     * spring应用上下文
     */
    private static ConfigurableListableBeanFactory beanFactory;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        SpringUtils.beanFactory = configurableListableBeanFactory;
    }

    /**
     * 获取类型对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeansException{
        return (T) SpringUtils.beanFactory.getBean(name);
    }

    /**
     * 获取类型为requiredType的对象
     */
    public static <T> T getBean(Class<T> requiredType) throws BeansException {
        return SpringUtils.beanFactory.getBean(requiredType);
    }
}
