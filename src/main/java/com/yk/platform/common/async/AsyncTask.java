package com.yk.platform.common.async;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Repository;

import com.yk.platform.common.redis.BackGroudTaskRedis;
import com.yk.platform.common.util.SerializeUtils;

/**
 * @author liupengfei 文件上传到七牛的后台任务
 */
@Repository
public class AsyncTask implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger logger = Logger.getLogger(AsyncTask.class);
    // 所有任务量比较小的线程池
    private ExecutorService pool = Executors.newFixedThreadPool(2);
    // 存放上传任务，更新之后添加推送任务的队列
    private BlockingQueue<BackGroudTask> queue = new LinkedBlockingQueue<>();

    public void dotask() {
        beforeDoTask();
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    try {
                        pool.submit(queue.take());
                    }
                    catch (InterruptedException e) {
                        logger.error(e);
                        logger.error("常规异步任务执行失败");
                    }
                }

            }
        }).start();

    }

    public void add(BackGroudTask f) {

        queue.add(f);
        BackGroudTaskRedis.sadd(BackGroudTaskRedis.BACKTASK.getBytes(), SerializeUtils.serialize(f));
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            // root application context 没有parent，他就是老大.
            // 需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。
            dotask();
        }
    }

    public void beforeDoTask() {
        Set<byte[]> set = BackGroudTaskRedis.smembers(BackGroudTaskRedis.BACKTASK.getBytes());
        if (set == null) {
            return;
        }
        Iterator<byte[]> iterator = set.iterator();
        while (iterator.hasNext()) {
            BackGroudTask backGroudTask = (BackGroudTask) SerializeUtils.deserialize(iterator.next());
            queue.add(backGroudTask);
        }

    }
}
