package com.yk.platform.business.demo.service;

import com.yk.platform.business.demo.entity.Demo;
import com.yk.platform.common.IBaseService;

public interface DemoService extends IBaseService<Demo> {

    /**
     * @description 校验登录信息
     * @author 阳凯
     * @date 2017年12月20日 下午9:16:46
     * @param username
     * @param password
     * @return
     */
    boolean checkLogin(String username, String password);

    /**
     * @description 定时执行任务
     * @author 阳凯
     * @date 2017年12月20日 下午9:20:07
     */
    void timingExcuteJob();
}
