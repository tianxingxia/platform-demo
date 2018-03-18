package com.yk.platform.common.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class UserUtil {

	private static Logger logger=Logger.getLogger(UserUtil.class.getName());
	
	public static String getUserName(){
		String userName=null;
		try {
			RequestAttributes ra = RequestContextHolder.getRequestAttributes();  
			HttpServletRequest request = ((ServletRequestAttributes)ra).getRequest();  
			userName = (String) request.getSession().getAttribute("reqUserName");
		} catch (Exception e) {
			logger.error("获取用户异常",e);
		} 
		return userName;
	}
}
