package com.eternity.blog.common.utils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Description IP地址 工具类
 * @Author eternity
 * @Date 2020/4/14 22:54
 */
public class IpUtils {
    private static final int IPV4_MAX_LENGTH = 15;
    private static final String UNKNOWN = "unknown";

    /**
     * 获取ip地址
     *
     * @param request http请求
     * @return ipv4
     */
    public static String getIpAddr(HttpServletRequest request) {
        if (request == null) {
            return UNKNOWN;
        }
        String ip = request.getHeader("x-forwarded-for");
        if (StringUtils.isNull(ip) || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isNull(ip) || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (StringUtils.isNull(ip) || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isNull(ip) || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (StringUtils.isNull(ip) || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            /*if ("0:0:0:0:0:0:0:1".equals(ip)) {
                InetAddress address;
                try {
                    address = InetAddress.getLocalHost();
                    ip = address.getHostAddress();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }*/
        }
        if (StringUtils.isNull(ip)) {
            return UNKNOWN;
        }
        if (ip.length() > IPV4_MAX_LENGTH) {
            if (ip.indexOf(',') == -1) {
                return ip;
            }
            ip = ip.substring(0, ip.indexOf(','));
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }
}
