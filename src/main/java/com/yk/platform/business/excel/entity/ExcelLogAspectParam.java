package com.yk.platform.business.excel.entity;

/**
 * 日志传参
 * @author: yangkai 
 * @date:2015-11-30 
 * @Copyright:Copyright (c) bbk | 广东步步高教育电子有限公司 
 */
public class ExcelLogAspectParam {
	private static final ThreadLocal<ExcelOpLog> SysLogThreadLocal = new ThreadLocal<ExcelOpLog>();

	public static ExcelOpLog getExcelOpLog(){
		ExcelOpLog params = SysLogThreadLocal.get();
		if(params==null){
			params=new ExcelOpLog();
			SysLogThreadLocal.set(params);
		}
		return params;
	} 
	
	public static void clearExcelOpLog(){		
		SysLogThreadLocal.set(null);
	}
	
	public static void setExcelOpLog(ExcelOpLog  actLogParam){
		SysLogThreadLocal.set(actLogParam);
	}
}
