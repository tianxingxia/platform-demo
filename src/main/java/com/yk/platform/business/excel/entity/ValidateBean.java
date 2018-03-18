package com.yk.platform.business.excel.entity;

/**
 * Excel验证类
 * @author yangkai
 * @date 2015-11-30
 * @version 1.0.0
 * @company bbk
 */
public class ValidateBean {
	/*最小长度*/
	public Integer minLength;
	/*最大长度*/
	public Integer maxLength;
	/*最小值*/
	public Double minSize;
	/*最大值*/
	public Double maxSize;
	/*是否允许为空,默认为不允许*/
	public boolean nullAble=false;
	/*子定义验证方法*/
	public String customValidate;
	
	public Integer getMinLength() {
		return minLength;
	}

	public void setMinLength(Integer minLength) {
		this.minLength = minLength;
	}

	public Integer getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}


	public boolean isNullAble() {
		return nullAble;
	}

	public void setNullAble(boolean nullAble) {
		this.nullAble = nullAble;
	}

	
	
	public Double getMinSize() {
		return minSize;
	}

	public void setMinSize(Double minSize) {
		this.minSize = minSize;
	}

	public Double getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(Double maxSize) {
		this.maxSize = maxSize;
	}

	public String getCustomValidate() {
		return customValidate;
	}

	public void setCustomValidate(String customValidate) {
		this.customValidate = customValidate;
	}

	@Override
	public String toString() {
		return "ValidateBean [minLength=" + minLength + ", maxLength=" + maxLength + ", minSize=" + minSize
				+ ", maxSize=" + maxSize + ", nullAble=" + nullAble + ", customValidate=" + customValidate + "]";
	}

 

}
