package com.eternity.blog.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Description Redis 工具类(String hash set list )
 * @Author eternity
 * @Date 2020/5/24 19:58
 */
@Component
@SuppressWarnings("unchecked")
public class RedisUtils {
    private static final Logger log = LoggerFactory.getLogger(RedisUtils.class);

    private static final String ILLEGAL_ARGUMENT_MESSAGE = "参数列表不可有空值.";
    private static final String TIMEOUT_MESSAGE = "过期时间不可为零.";

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 观察键
     *
     * @param key 键
     * @return 结果
     */
    public boolean watch(String key) {
        try {
            if (!Boolean.TRUE.equals(exists(key))) {
                return false;
            }
            redisTemplate.watch(key);
            return true;
        } catch (Exception e) {
            log.error(String.format("观察键(%s)失败.", key), e);
            return false;
        }
    }

    /**
     * 开启事务
     *
     * @return 结果
     */
    public boolean multi() {
        try {
            redisTemplate.multi();
            return true;
        } catch (Exception e) {
            log.error("开启事务失败.", e);
            return false;
        }
    }

    /**
     * 结束事务
     *
     * @return 结果
     */
    public boolean exec() {
        try {
            redisTemplate.exec();
            return true;
        } catch (Exception e) {
            log.error("结束事务失败.", e);
            return false;
        }
    }

    /**
     * 回滚事务
     *
     * @return 结果
     */
    public boolean discard() {
        try {
            redisTemplate.discard();
            return true;
        } catch (Exception e) {
            log.error("回滚事务失败.", e);
            return false;
        }
    }

    /**
     * 设置过期时间(秒)
     *
     * @param key     键
     * @param timeout 过期时间
     * @return 设置结果
     */
    public Boolean expire(String key, long timeout) {
        try {
            if (timeout <= 0) {
                throw new IllegalArgumentException(TIMEOUT_MESSAGE);
            }
            return redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 查看键的有效期
     *
     * @param key 键
     * @return 有效期(0为永久有效)
     */
    public Long ttl(String key) {
        if (key == null || "".equals(key)) {
            return null;
        }
        return redisTemplate.getExpire(key);
    }

    /**
     * 取消键的过期时间
     *
     * @param key 键
     * @return 结果
     */
    public Boolean persist(String key) {
        if (key == null || "".equals(key)) {
            return false;
        }
        return redisTemplate.persist(key);
    }

    /**
     * 是否存在键
     *
     * @param key 键
     * @return 结果
     */
    public Boolean exists(String key) {
        if (key == null || "".equals(key)) {
            return false;
        }
        return redisTemplate.hasKey(key);
    }

    /**
     * 普通写入
     *
     * @param key   键
     * @param value 值
     * @return 是否写入成功
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error("写入错误.", e);
            return false;
        }
    }

    /**
     * 普通写入并设置过期时间
     *
     * @param key     键
     * @param value   值
     * @param timeout 过期时间
     * @return 是否写入成功
     */
    public boolean set(String key, Object value, long timeout) {
        try {
            if (timeout <= 0) {
                throw new IllegalArgumentException(TIMEOUT_MESSAGE);
            }
            redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            log.error("写入错误.", e);
            return false;
        }
    }

    /**
     * 删除键
     *
     * @param keys 键
     */
    public void del(String... keys) {
        if (keys == null || keys.length <= 0) {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_MESSAGE);
        }
        if (keys.length == 1) {
            redisTemplate.delete(keys[0]);
        }
        redisTemplate.delete(CollectionUtils.arrayToList(keys));
    }

    /**
     * 普通读取
     *
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        if (key == null || "".equals(key)) {
            return null;
        }
        return redisTemplate.opsForValue().get(key);
    }

    /**
     *
     * @param key
     * @return
     */
    public Long incr(String key) {
        try {
            if (key == null || "".equals(key)) {
                return 0L;
            }
            return redisTemplate.opsForValue().increment(key);
        } catch (Exception e) {
            return 0L;
        }
    }

    public Long incr(String key, long delta) {
        try {
            if (key == null || "".equals(key)) {
                return 0L;
            }
            return redisTemplate.opsForValue().increment(key,delta);
        } catch (Exception e) {
            return 0L;
        }
    }

    public Long decr(String key) {
        try {
            if (key == null || "".equals(key)) {
                return 0L;
            }
            return redisTemplate.opsForValue().decrement(key);
        } catch (Exception e) {
            return 0L;
        }
    }

    public Long decr(String key, long delta) {
        try {
            if (key == null || "".equals(key)) {
                return 0L;
            }
            return redisTemplate.opsForValue().decrement(key,delta);
        } catch (Exception e) {
            return 0L;
        }
    }



    /**
     * 设置单个hash键值
     *
     * @param key       键
     * @param hashKey   hash键
     * @param hashValue hash值
     * @return 结果
     */
    public boolean hashSet(String key, String hashKey, Object hashValue) {
        try {
            redisTemplate.opsForHash().put(key, hashKey, hashValue);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 设置单个hash键值并设置过期时间
     *
     * @param key       键
     * @param hashKey   hash键
     * @param hashValue hash值
     * @param timeout   过期时间
     * @return 结果
     */
    public boolean hashSet(String key, String hashKey, Object hashValue, long timeout) {
        try {
            redisTemplate.opsForHash().put(key, hashKey, hashValue);
            expire(key, timeout);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 设置map
     *
     * @param key 键
     * @param map hash键值
     * @return 结果
     */
    public boolean hashSet(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 设置map并设置过期时间
     *
     * @param key     键
     * @param map     hash键值
     * @param timeout 过期时间
     * @return 结果
     */
    public boolean hashSet(String key, Map<String, Object> map, long timeout) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            expire(key, timeout);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 读取
     *
     * @param key     键
     * @param hashKey hash键
     * @return hash值
     */
    public Object hashGet(String key, Object hashKey) {
        try {
            if (key == null || hashKey == null) {
                throw new IllegalArgumentException(ILLEGAL_ARGUMENT_MESSAGE);
            }
            return redisTemplate.opsForHash().get(key, hashKey);
        } catch (Exception e) {
            log.error("读取失败.", e);
            return null;
        }
    }

    /**
     * 读取(相应key的所有hash键值)
     *
     * @param key 键
     * @return 键值列表
     */
    public Map<Object, Object> hashGet(String key) {
        if (key == null) {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_MESSAGE);
        }
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 删除
     *
     * @param key      键
     * @param hashKeys hash键列表
     */
    public Long hashDel(String key, Object... hashKeys) {
        if (key == null || hashKeys == null || hashKeys.length <= 0) {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_MESSAGE);
        }
        return redisTemplate.opsForHash().delete(key, hashKeys);
    }

    /**
     * 是否存在hash键
     *
     * @param key     键
     * @param hashKey hash键
     */
    public Boolean hashExists(String key, String hashKey) {
        if (key == null || hashKey == null) {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_MESSAGE);
        }
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    /**
     * 写入
     *
     * @param key    键
     * @param values set值
     * @return 成功数量
     */
    public Long setAdd(String key, Object... values) {
        try {
            if (key == null || values == null || values.length <= 0) {
                throw new IllegalArgumentException(ILLEGAL_ARGUMENT_MESSAGE);
            }
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            log.error("写入错误.", e);
            return 0L;
        }
    }

    /**
     * 写入
     *
     * @param key     键
     * @param timeout 过期时间
     * @param values  set值
     * @return 成功数量
     */
    public Long setAdd(String key, long timeout, Object... values) {
        if (key == null || values == null || values.length <= 0) {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_MESSAGE);
        }
        Long successNumber = setAdd(key, values);
        if (successNumber <= 0) {
            return 0L;
        }
        expire(key, timeout);
        return successNumber;
    }

    public Set<Object> setGet(String key) {
        try {
            if (key == null) {
                throw new IllegalArgumentException(ILLEGAL_ARGUMENT_MESSAGE);
            }
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            log.error("获取失败.", e);
            return null;
        }
    }

    /**
     * 是否存在值
     *
     * @param key   键
     * @param value 值
     * @return 结果
     */
    public Boolean setExists(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            log.error("查找错误.", e);
            return false;
        }
    }

    /**
     * 获取set数量
     *
     * @param key 键
     * @return 数量
     */
    public Long setSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            log.error("获取错误.", e);
            return 0L;
        }
    }

    /**
     * 删除值
     *
     * @param key    键
     * @param values 值
     * @return 删除结果
     */
    public Long setRemove(String key, Object... values) {
        try {
            if (key == null || values == null || values.length <= 0) {
                throw new IllegalArgumentException(ILLEGAL_ARGUMENT_MESSAGE);
            }
            return redisTemplate.opsForSet().remove(key, values);
        } catch (Exception e) {
            log.error("删除错误.", e);
            return 0L;
        }
    }


    /**
     * 添加列表元素
     *
     * @param key    键
     * @param value  值
     * @param isLeft 是否从右边插入
     * @return 插入数量
     */
    public Long listSet(String key, Object value, boolean isLeft) {
        try {
            if (isLeft) {
                return redisTemplate.opsForList().leftPush(key, value);
            }
            return redisTemplate.opsForList().rightPush(key, value);
        } catch (Exception e) {
            log.error("设置错误.", e);
            return 0L;
        }
    }

    /**
     * 添加列表元素并设置超时时间
     *
     * @param key     键
     * @param value   值
     * @param timeout 超时时间
     * @param isLeft  是否从右边插入
     * @return 插入数量
     */
    public Long listSet(String key, Object value, Long timeout, boolean isLeft) {
        Long successNumber = listSet(key, value, isLeft);
        expire(key, timeout);
        return successNumber;
    }

    /**
     * 批量添加列表元素
     *
     * @param key    键
     * @param values 列表
     * @param isLeft 是否从右边插入
     * @return 插入数量
     */
    public Long listSet(String key, List<Object> values, boolean isLeft) {
        try {
            if (isLeft) {
                return redisTemplate.opsForList().leftPushAll(key, values);
            }
            return redisTemplate.opsForList().rightPushAll(key, values);
        } catch (Exception e) {
            log.error("设置错误.", e);
            return 0L;
        }
    }

    /**
     * 批量添加列表元素并设置超时时间
     *
     * @param key     键
     * @param values  列表
     * @param timeout 超时时间
     * @param isLeft  是否从右边插入
     * @return 插入数量
     */
    public Long listSet(String key, List<Object> values, Long timeout, boolean isLeft) {
        Long successNumber = listSet(key, values, isLeft);
        expire(key, timeout);
        return successNumber;
    }

    /**
     * 获取单个元素
     *
     * @param key   键
     * @param index 位置(0为第一元素、index < 0 从尾部获取 )
     * @return 值
     */
    public Object listGet(String key, Long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * 获取指定范围元素
     *
     * @param key   键
     * @param start 开始位置
     * @param end   结束位置
     * @return 元素列表
     */
    public List<Object> listGet(String key, Long start, Long end) {
        List<Object> items = new ArrayList<>();
        for (long index = start; index < end; index++) {
            items.add(listGet(key, index));
        }
        return items;
    }

    /**
     * 获取list全部数据
     *
     * @param key 键
     * @return 值
     */
    public List<Object> listGet(String key) {
        Long size = listSize(key);
        return listGet(key, 0L, size);
    }

    /**
     * 获取list数量
     *
     * @param key 键
     * @return 数量
     */
    public Long listSize(String key) {
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 删除list元素
     *
     * @param key   键
     * @param count 删除个数(0:全部删除 负数:从尾部删除 正数:从头部删除)
     * @param value 值
     * @return 删除数量
     */
    public Long listRemove(String key, long count, Object value) {
        try {
            return redisTemplate.opsForList().remove(key, count, value);
        } catch (Exception e) {
            log.error("删除错误.", e);
            return 0L;
        }
    }

}
