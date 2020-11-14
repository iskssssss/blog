package com.eternity.blog.common.utils;

/**
 * @Description 布尔值工具类
 * @Author eternity
 * @Date 2020/4/11 19:11
 */
public class BooleanUtils {
    /**
     * 将数值转为boolean
     * @param val 数值
     * @return 大于等于 1 为 true,反之false
     */
    public static boolean toBoolean(int val){
        return val >= 1;
    }
}
