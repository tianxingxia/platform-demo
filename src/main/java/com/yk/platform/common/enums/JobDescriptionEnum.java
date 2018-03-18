package com.yk.platform.common.enums;

/**
 * @枚举名 JobDescriptionEnum
 * @枚举描述 定时任务描述枚举
 * @作者 阳凯
 * @日期 2016-11-24
 *
 */
public enum JobDescriptionEnum {

	TIME_NOT_FIT("当前时间不合适执行定时任务"),

	JOB_FINISH("定时任务执行完成"),

	JOB_NOT_FINISH("定时任务由于异常原因没有执行完成");

	private String value;

	private JobDescriptionEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
