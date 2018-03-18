package com.yk.platform.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * 异常日志输出的工具类
 * 
 * @author jtz
 */
public class ExceptionUtil {
	
	/**
	 * 记录异常日志
	 * @param e
	 * @param logger
	 */
	public static void log(Exception e, Logger logger) {
		Map<String, String> logMap = new HashMap<String, String>();
		Writer w = new StringWriter();
		e.printStackTrace(new PrintWriter(w));
		logMap.put("exception", w.toString());// 增加exception属性
		logMap.put("exceptionName",  e.getCause()!=null?e.getCause().getClass().getName():e.getClass().getName());// 增加exceptionName属性
		logger.error("[xtcwatchlog] " + "[" + DateUtil.getDateFormatter()+ "] " + JSONUtil.toJSONString(logMap));
	}
}
