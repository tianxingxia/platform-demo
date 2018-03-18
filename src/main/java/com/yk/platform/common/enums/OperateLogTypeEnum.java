package com.yk.platform.common.enums;

/**
 * @author liuepngfei 日志的操作类型
 */
public enum OperateLogTypeEnum {

	/**
	 * 更新
	 */
	UPDATE(1, "更新"),
	/**
	 * 删除
	 */
	DELETE(2, "删除"),
	/**
	 * 新增
	 */
	ADD(3, "新增");
	private final String value;
	private final Integer id;

	private OperateLogTypeEnum(Integer id, String value) {
		this.id = id;
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public Integer getId() {
		return id;
	}

	public static String transferOperateType(Integer id) {
		if (UPDATE.getId().equals(id)) {
			return UPDATE.getValue();
		}
		if (DELETE.getId().equals(id)) {
			return DELETE.getValue();
		}
		if (ADD.getId().equals(id)) {
			return ADD.getValue();
		}

		return null;
	}
}
