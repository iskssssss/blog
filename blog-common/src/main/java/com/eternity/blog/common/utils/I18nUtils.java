package com.eternity.blog.common.utils;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * @Description 国际化工具类
 * @Author eternity
 * @Date 2020/4/13 20:20
 */
public class I18nUtils {
    /**
     * 将字符串链接转为实际文本
     * @param val 字符串链接
     * @return 实际文本
     */
    public static String message(String val) {
        MessageSource source = SpringUtils.getBean(MessageSource.class);
        return StringUtils.delHeadAndTailChar(source.getMessage(val, null, LocaleContextHolder.getLocale()));
    }
}
