package com.yk.platform.common.util;

import com.eebbk.edu.redis.JedisWrapper;

public class RedisUtil {

    public static JedisWrapper getJedisWrapper() {
        return (JedisWrapper) JedisWrapper.getInstance("platformRedis");
    }

}
