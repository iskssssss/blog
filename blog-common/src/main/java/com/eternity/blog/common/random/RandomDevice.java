package com.eternity.blog.common.random;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @Description 随机器
 * @Author eternity
 * @Date 2020/4/11 17:09
 */
public class RandomDevice {
    /**
     * 随机生成UID
     *
     * @return 随机生成的UID
     */
    public static long randomUid() {
        try {
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            return (secureRandom.nextInt(89999) + 10000L);
        } catch (NoSuchAlgorithmException e) {
            // 加密算法不可用时抛出异常
            return -1;
        }
    }

    /**
     * 随机生成ID
     *
     * @return 随机生成的ID
     */
    public static long randomId() {
        try {
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            return (secureRandom.nextInt(899999) + 100000L);
        } catch (NoSuchAlgorithmException e) {
            // 加密算法不可用时抛出异常
            return -1;
        }
    }
}
