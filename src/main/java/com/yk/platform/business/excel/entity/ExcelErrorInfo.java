package com.yk.platform.business.excel.entity;

/**
 * Excel错误信息类
 * @author yangkai
 * @date 2015-11-30
 * @version 1.0.0
 * @company bbk
 */
public class ExcelErrorInfo {
	private Integer rowIndex;
	private Integer colIndex;
	private String errorMsg;
	public Integer getRowIndex() {
		return rowIndex;
	}
	public void setRowIndex(Integer rowIndex) {
		this.rowIndex = rowIndex;
	}
	public Integer getColIndex() {
		return colIndex;
	}
	public void setColIndex(Integer colIndex) {
		this.colIndex = colIndex;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	@Override
	public String toString() {
		return "ErrorInfo [rowIndex=" + rowIndex + ", colIndex=" + colIndex + ", errorMsg=" + errorMsg + "]";
	}
	
	
	
}
