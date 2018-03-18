package com.yk.platform.business.excel.entity;

import java.util.List;


/**
 * <p>Description: </p>
 * @date 2014年5月19日
 * @author 李志勇 
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 * @param <T>
 */
public class ImportInfoBean<T> {
	/*数据集合*/
	private List<T> beanList;
	/*错误信息集合*/
	private List<ExcelOpLogDetail> errInfoList;
	/*是否有错误*/
	private boolean hasError;
	public List<T> getBeanList() {
		return beanList;
	}
	public void setBeanList(List<T> beanList) {
		this.beanList = beanList;
	}
	public List<ExcelOpLogDetail> getErrInfoList() {
		return errInfoList;
	}
	public void setErrInfoList(List<ExcelOpLogDetail> errInfoList) {
		this.errInfoList = errInfoList;
	}
	public boolean isHasError() {
		return hasError;
	}
	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}
}
