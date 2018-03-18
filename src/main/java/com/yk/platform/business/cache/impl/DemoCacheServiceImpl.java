package com.yk.platform.business.cache.impl;

import org.springframework.stereotype.Service;

import com.eebbk.edu.redis.JedisWrapper;
import com.yk.platform.business.cache.DemoCacheService;
import com.yk.platform.business.demo.entity.Demo;
import com.yk.platform.common.util.JSONUtil;

@Service
public class DemoCacheServiceImpl implements DemoCacheService {

    private static final String CACHE_PRE_KEY = "demo";

    private JedisWrapper getInstance() {
        return JedisWrapper.getInstance("demoRedis");
    }

    @Override
    public void addToCache(Demo demo) {
        this.getInstance().sadd(CACHE_PRE_KEY, JSONUtil.toJSONString(demo));
    }
}
