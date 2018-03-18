package com.yk.platform.business.log.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yk.platform.business.log.qo.OperateLogQo;
import com.yk.platform.business.log.service.OperateLogService;
import com.yk.platform.common.AbstractController;
import com.yk.platform.common.PageResult;
import com.yk.platform.common.ResultBean;
import com.yk.platform.common.util.ResponseUtils;

@Controller
@RequestMapping(value = "/operateLog")
public class OperateLogController extends AbstractController {

    @Autowired
    private OperateLogService operateLogService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public void getGreyGroupList(HttpServletRequest request, HttpServletResponse response, OperateLogQo qo) throws Exception {
        PageResult pageResult = operateLogService.getOperateLogsByPage(qo);
        ResultBean resultBean = getSuccessResultBean();
        resultBean.setData(pageResult);
        ResponseUtils.setObjectResponse(response, resultBean);
    }
}
