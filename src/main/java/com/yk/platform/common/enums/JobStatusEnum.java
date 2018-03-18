package com.yk.platform.common.enums;

/**
 * @枚举名 JobStatusEnum
 * @枚举描述 定时任务状态枚举
 * @作者 阳凯
 * @日期 2016-11-24
 *
 */
public enum JobStatusEnum {

	SUCCESS("SUCCESS"),

	FAIL("FAIL");

	private String value;

	JobStatusEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
