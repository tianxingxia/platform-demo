package com.yk.platform.common.enums;

public enum CommentFilterType {
	/**
	 * 用户
	 */
	USER(1, "用户"),
	/**
	 * 内容
	 */
	CONTENT(2, "内容");
	private final Integer id;
	private final String name;

	private CommentFilterType(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public static String transferid(Integer id) {
		if (USER.getId().equals(id)) {
			return USER.getName();
		}
		if (CONTENT.getId().equals(id)) {
			return CONTENT.getName();
		}
		return null;
	}
}
