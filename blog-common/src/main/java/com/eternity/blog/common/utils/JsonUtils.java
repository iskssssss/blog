package com.eternity.blog.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.eternity.blog.common.utils.StringUtils;

/**
 * @Description json 工具类
 * @Author eternity
 * @Date 2020/4/21 22:50
 */
public class JsonUtils extends JSONObject  {
    private static final String ERROR_INFO = "传入参数不可为空.";

    public static String toJsonString(Object object) {
        if (StringUtils.isNull(object)) {
            throw new IllegalArgumentException(ERROR_INFO);
        }
        return toJSONString(object);
    }

    public static JSONObject toJsonObject(Object object) {
        if (StringUtils.isNull(object)) {
            throw new IllegalArgumentException(ERROR_INFO);
        }
        return (JSONObject) toJSON(object);
    }

    public static JSONObject toJsonObject(String text) {
        if (StringUtils.isNull(text) || StringUtils.isEmpty(text)) {
            throw new IllegalArgumentException(ERROR_INFO);
        }
        return parseObject(text);
    }
}
