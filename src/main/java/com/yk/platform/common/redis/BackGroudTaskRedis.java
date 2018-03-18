package com.yk.platform.common.redis;

import java.util.Set;

import org.apache.log4j.Logger;

import com.yk.platform.common.util.RedisUtil;

/**
 * 将后台任务存储到redis，系统重启或崩溃的时候可以恢复执行
 * 
 * @author liupengfei
 */
public class BackGroudTaskRedis {

    public static final String BACKTASK = "w_bt_";

    private static final Logger logger = Logger.getLogger(BackGroudTaskRedis.class);

    public static void sadd(byte[] key, byte[]... members) {

        try {
            RedisUtil.getJedisWrapper().sadd(key, members);
        }
        catch (Exception e) {
            logger.error("redis添加异步任务失败");
        }
    }

    public static void srem(byte[] key, byte[]... members) {
        try {
            RedisUtil.getJedisWrapper().srem(key, members);
        }
        catch (Exception e) {
            logger.error("redis删除异步任务失败");
        }
    }

    public static Set<byte[]> smembers(byte[] key) {
        return RedisUtil.getJedisWrapper().smembers(key);
    }
}
