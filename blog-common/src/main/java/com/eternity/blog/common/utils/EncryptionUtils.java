package com.eternity.blog.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.dc.pr.PRError;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * @Description 加密工具类
 * @Author eternity
 * @Date 2020/4/12 16:33
 */
public class EncryptionUtils {
    private static final Logger log = LoggerFactory.getLogger(EncryptionUtils.class);
    private static final String MD5_CREATE_DATE = "2020/4/12 16:33";
    private static final long ENCRYPTION_TIMES = 16;

    /**
     * MD5加密
     *
     * @param pwd        明文
     * @param slat       盐
     * @param iterations 迭代次数
     * @return 密文
     */
    private static byte[] md5(String pwd, String slat, long iterations) {
        try {
            MessageDigest mMd5 = MessageDigest.getInstance("md5");
            if (StringUtils.isNotNull(slat) && !"".equals(slat)) {
                mMd5.reset();
                mMd5.update(slat.getBytes(StandardCharsets.UTF_8));
            }

            byte[] hashed = mMd5.digest(pwd.getBytes(StandardCharsets.UTF_8));
            for (long i = 0; i < iterations - 1; i++) {
                mMd5.reset();
                hashed = mMd5.digest(hashed);
            }
            return hashed;
        } catch (NoSuchAlgorithmException e) {
            String error = String.format("MD5加密错误：%s", e.getMessage());
            log.error(error);
        }
        return null;
    }

    /**
     * 转为十六进制
     *
     * @param bytes 数组
     * @return 十六进制字符串
     */
    public static String toHex(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder(bytes.length * 2);
        for (byte aByte : bytes) {
            int num = (aByte & 0xff);
            if (num < 0x10) {
                stringBuilder.append("0");
            }
            stringBuilder.append(Long.toString(num, 16));
        }
        return stringBuilder.toString();
    }


    /**
     * 根据uid获取盐值
     *
     * @param uid UID
     * @return 盐
     */
    public static String randomSalt(Long uid) {
        byte[] val = new byte[8];
        long seed = System.currentTimeMillis() / uid;
        Random random = new Random(seed);
        random.nextBytes(val);
        return EncryptionUtils.toHex(val);
    }

    /**
     * 将密码转换成HashMd5
     *
     * @param pwd  明文
     * @param salt 盐
     * @return 密文
     */
    public static String toMd5Hash(String pwd, String salt) {
        return toMd5Hash(pwd, salt, ENCRYPTION_TIMES);
    }

    /**
     * 将密码转换成HashMd5
     *
     * @param pwd  明文
     * @param salt 盐
     * @return 密文
     */
    public static String toMd5Hash(String pwd, String salt, long iterations) {
        return toHex(md5(pwd, salt, iterations));
    }
}