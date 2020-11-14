package com.eternity.blog.common.utils;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description 客户端 工具类
 * @Author eternity
 * @Date 2020/4/18 11:33
 */
public class ServletUtils {

    public static ServletRequestAttributes getServletRequestAttributes() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) attributes;
    }

    /**
     * 获取 Request
     * @return HttpServletRequest
     */
    public static HttpServletRequest getHttpServletRequest(){
        return getServletRequestAttributes().getRequest();
    }
}
