package com.yk.platform.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.eebbk.webadmin.authority.client.BaseAuthorityFilter;
import com.yk.platform.exception.BusinessException;

public class MyBaseAuthorityFilter extends BaseAuthorityFilter implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // BaseAuthorityFilter类中方法，功能：发送请求到远程服务器校验权限
        boolean isAuthority = this.checkAuthority(request);
        if (isAuthority) {
            return true;
        }
        response.setStatus(401);
        throw new BusinessException("accessdeny");
    }

    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {
    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3) throws Exception {
    }
}
