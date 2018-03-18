package com.yk.platform.common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yk.platform.common.util.ConstantsUtil;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping(value = "/login")
public class LoginController {

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "跳转到前端页面 ", notes = "跳转到前端页面 ")
    public void redirectToFront(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String url = request.getScheme() + "://" + request.getServerName() + ConstantsUtil.getConstant("front.html.url");
        response.sendRedirect(url);
    }
}
