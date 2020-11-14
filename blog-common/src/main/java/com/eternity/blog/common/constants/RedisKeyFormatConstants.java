package com.eternity.blog.common.constants;

import com.sun.org.apache.bcel.internal.generic.LineNumberGen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.IllegalFormatConversionException;

/**
 * @Description
 * @Author eternity
 * @Date 2020/5/28 18:17
 */
public enum RedisKeyFormatConstants {
    /**
     * 博文 存放样式
     */
    BLOG("BLOG-INFO-%s");

    private static final Logger log = LoggerFactory.getLogger(RedisKeyFormatConstants.class);
    private final String format;

    RedisKeyFormatConstants(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }

    public String format(Object info) {
        try {
            return String.format(format, info);
        } catch (IllegalFormatConversionException e) {
            log.error("类型错误.", e);
            return null;
        }
    }
}
