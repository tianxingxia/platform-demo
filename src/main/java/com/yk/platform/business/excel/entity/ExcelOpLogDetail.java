package com.yk.platform.business.excel.entity;

import java.util.Date;

/**
 * <p>Description: </p>
 * @date 2015年12月13日
 * @author yangkai 
 * @version 1.0
 * <p>Company:BBK</p>
 * <p>Copyright:Copyright(c)2015</p>
 */
public class ExcelOpLogDetail {
	/* ID */
	private int id;
	/* 出错行号 */
	private Integer rowNum;
	/* 出错列号 */
	private Integer colNum;
	/* 出错信息 */
	private String errMsg;
	/* 日志ID */
	private String logId;
	/* 出错时间 */
	private Date errDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getRowNum() {
		return rowNum;
	}

	public void setRowNum(Integer rowNum) {
		this.rowNum = rowNum;
	}

	public Integer getColNum() {
		return colNum;
	}

	public void setColNum(Integer colNum) {
		this.colNum = colNum;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public Date getErrDate() {
		return errDate;
	}

	public void setErrDate(Date errDate) {
		this.errDate = errDate;
	}

	@Override
	public String toString() {
		return "ExcelOpLogDetail [id=" + id + ", rowNum=" + rowNum + ", colNum=" + colNum + ", errMsg="
				+ errMsg + ", logId=" + logId + ", errDate=" + errDate + "]";
	}
}
