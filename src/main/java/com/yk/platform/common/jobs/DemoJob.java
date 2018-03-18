package com.yk.platform.common.jobs;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Repository;

import com.yk.platform.business.demo.service.DemoService;
import com.yk.platform.common.util.SpringUtils;

/**
 * @项目名称：demo-platform
 * @类名称：DemoJob
 * @类描述：示例定时任务
 * @创建人：阳凯
 * @创建时间：2017年12月20日 下午9:29:10
 * @company:步步高教育电子有限公司
 */
@Repository
public class DemoJob extends QuartzJobBean {
    private static final Logger logger = Logger.getLogger(DemoJob.class);

    @Override
    protected void executeInternal(JobExecutionContext arg0) {
        try {
            ((DemoService) SpringUtils.getBean("demoService")).timingExcuteJob();
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error("定时执行处理失败的后台任务的任务失败");
            logger.error(e);
        }
    }

}
