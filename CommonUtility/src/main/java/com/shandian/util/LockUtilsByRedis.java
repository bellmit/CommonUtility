package com.shandian.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

import com.shandian.CommonUtility.redis.JedisUtil;

/**
 * Created by linqs on 2017/1/11.
 */
public class LockUtilsByRedis {
    private static Logger logger = LoggerFactory.getLogger(LockUtilsByRedis.class);

    /**
     * 方法描述：获取redis锁
     * 
     * @author chenlin
     * @date： 日期：2016年8月27日 时间：下午3:13:44
     * @param lockname
     * @param seconds
     * @return
     * @version 1.4.0
     */
    public static boolean getLock(String lockname, String lockvalue, int seconds) {
        Jedis jedis = null;
        boolean lock = true;
        try {
            jedis = JedisUtil.getInstance().getJedis();
            if (jedis.setnx(lockname, lockvalue) == 1 && seconds != 0) {
                jedis.expire(lockname, seconds);
            } else {
                lock = false;
            }
        } catch (Exception e) {
            logger.error("获取锁" + lockname + "失败", e);
        } finally {
            JedisUtil.returnBrokenResource(jedis);
        }
        return lock;
    }

    /**
     * 方法描述：释放redis锁
     * 
     * @author chenlin
     * @date： 日期：2016年8月27日 时间：下午3:17:09
     * @param lockname
     * @param lockvalue
     * @version 1.4.0
     */
    public static void unlock(String lockname, String lockvalue) {
        Jedis jedis = null;
        try {
            jedis = JedisUtil.getInstance().getJedis();
            String lockValue = jedis.get(lockname);
            if (org.apache.commons.lang.StringUtils.isNotBlank(lockValue) && lockvalue.equals(lockValue)) {
                jedis.del(lockname);
            }
        } catch (Exception e) {
            logger.error("释放锁" + lockname + "失败", e);
        } finally {
            JedisUtil.returnBrokenResource(jedis);
        }
    }

    public static String redisGet(String key) {
        String value = null;
        Jedis jedis = null;
        try {
            jedis = JedisUtil.getInstance().getJedis();
            value = jedis.get(key);
        } catch (Exception e) {
            logger.error("获取{}失败", key, e);
        } finally {
            JedisUtil.returnBrokenResource(jedis);
        }
        return value;
    }

    public static void redisSet(String key, String value, int seconds) {
        Jedis jedis = null;
        try {
            jedis = JedisUtil.getInstance().getJedis();
            value = jedis.set(key, value);
            if (seconds > 0) {
                // 设置失效时间
                jedis.expire(key, seconds);
            }
        } catch (Exception e) {
            logger.error("保存{}-{}失败", key, value, e);
        } finally {
            JedisUtil.returnBrokenResource(jedis);
        }
    }
}
