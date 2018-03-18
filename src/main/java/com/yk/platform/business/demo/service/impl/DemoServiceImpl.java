package com.yk.platform.business.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yk.platform.business.demo.dao.DemoDao;
import com.yk.platform.business.demo.entity.Demo;
import com.yk.platform.business.demo.service.DemoService;
import com.yk.platform.common.AbstractService;
import com.yk.platform.common.IBaseDao;

@Service("demoService")
public class DemoServiceImpl extends AbstractService<Demo>implements DemoService {

    @Autowired
    private DemoDao demoDao;

    @Override
    public IBaseDao<Demo> getBaseDao() {
        return demoDao;
    }

    @Override
    public boolean checkLogin(String username, String password) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void timingExcuteJob() {
        // TODO Auto-generated method stub

    }

}
