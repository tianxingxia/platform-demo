package com.yk.platform.business.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yk.platform.business.demo.service.DemoService;
import com.yk.platform.common.ResultBean;

@Controller
@RequestMapping(value = "/demo")
public class DemoController {

    @Autowired
    private DemoService demoService;

    @ResponseBody
    @RequestMapping(value = "/login")
    public ResultBean updPage(@RequestParam("username") String username, @RequestParam(value = "password") String password) {
        ResultBean resultBean = new ResultBean();

        boolean result = demoService.checkLogin(username, password);
        resultBean.setData(result);

        return resultBean;
    }
}
