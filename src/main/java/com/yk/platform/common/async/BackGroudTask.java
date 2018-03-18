package com.yk.platform.common.async;

import java.io.Serializable;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.yk.platform.common.redis.BackGroudTaskRedis;
import com.yk.platform.common.util.SerializeUtils;
import com.yk.platform.exception.BackgroundTaskFailException;

public abstract class BackGroudTask implements Callable<String>, Serializable {

    private static final Logger logger = Logger.getLogger(BackGroudTask.class);
    private AtomicInteger trytimes = new AtomicInteger(5);// 重试次数
    private static final long serialVersionUID = 1L;

    @Override
    public String call() throws Exception {
        work();
        return null;
    }

    private void work() throws Exception {
        try {
            doinworkThread();
            trytimes.set(5);
            BackGroudTaskRedis.srem(BackGroudTaskRedis.BACKTASK.getBytes(), SerializeUtils.serialize(this));
        }
        catch (Exception e) {
            try {
                logger.error(e);
                retry(trytimes.getAndDecrement());
            }
            catch (BackgroundTaskFailException e1) {
                doFail();
                logger.error(e1);
            }
        }
    }

    /**
     * 业务处理方法
     * 
     * @throws Exception
     */
    public abstract void doinworkThread() throws Exception;

    public void retry(Integer count) throws Exception {
        if (count > 0) {
            work();
        }
        else {
            logger.error("异步任务执行失败:" + JSON.toJSONString(this));
            throw new BackgroundTaskFailException();
        }
    }

    /**
     * 失败处理方法,子类重写时请先执行此方法
     */
    public void doFail() {
        try {
            trytimes.set(5);
            BackGroudTaskRedis.srem(BackGroudTaskRedis.BACKTASK.getBytes(), SerializeUtils.serialize(this));
        }
        catch (Exception e) {
            logger.error(e);
        }
    }
}
