package com.yk.platform.business.cache;

import com.yk.platform.business.demo.entity.Demo;

/**
 * 手表版本构建时间缓存
 * @author guojinhao
 * @date 2016年8月11日
 * @Copyright (c) 2016, www.okii.com All Rights Reserved.
 */
public interface DemoCacheService {

    /**
     * @description 示例添加对象到缓存中去
     * @author 阳凯
     * @date 2017年12月20日 下午9:23:36
     * @param demo
     */
    void addToCache(Demo demo);
}
