package com.eternity.blog.common.utils;

import org.springframework.beans.BeansException;

/**
 * @Description Bean 工具类
 * @Author eternity
 * @Date 2020/4/14 16:54
;
 */
public class BeanUtils extends org.springframework.beans.BeanUtils {
    /**
     * 将source复制到target
     *
     * @param source 源
     * @param target 目标
     */
    public static void copyBeanProperties(Object source, Object target) {
        try {
            copyProperties(source, target);
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }

}
