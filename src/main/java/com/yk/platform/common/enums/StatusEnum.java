package com.yk.platform.common.enums;

public enum StatusEnum {
	/**
	 * 正在上传到云存储 1
	 */
	UPLOAD_ING(1, "正在上传到云存储"),
	/**
	 * 已上传到云存储 2
	 */
	UPLOAD(2, "已上传到云存储"),
	/**
	 * 上传到云存储失败 3
	 */
	UPLOAD_FAIL(3, "上传到云存储失败"),
	/**
	 * 灰度发布 4
	 */
	GRAY_RELEASE(4, "灰度发布"),
	/**
	 * 灰度发布通过 5
	 */
	GRAY_VERIFY_PASS(5, "灰度发布通过"),
	/**
	 * 全量发布 6
	 */
	ALL_RELEASE(6, "全量发布"),
	/**
	 * 下架 7
	 */
	OFF_THE_SHELF(7, "下架"),
	/**
	 * 灰度发布失败 8
	 */
	GRAY_VERIFY_FAIL(8, "灰度发布失败");
	private final Integer status;
	private final String name;

	private StatusEnum(Integer status, String name) {
		this.status = status;
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public Integer getStatus() {
		return this.status;
	}

	public static String transferStatus(Integer status) {
		if (UPLOAD_ING.getStatus().equals(status)) {
			return UPLOAD_ING.getName();
		}
		if (UPLOAD.getStatus().equals(status)) {
			return UPLOAD.getName();
		}
		if (UPLOAD_FAIL.getStatus().equals(status)) {
			return UPLOAD_FAIL.getName();
		}
		if (GRAY_RELEASE.getStatus().equals(status)) {
			return GRAY_RELEASE.getName();
		}
		if (GRAY_VERIFY_PASS.getStatus().equals(status)) {
			return GRAY_VERIFY_PASS.getName();
		}
		if (ALL_RELEASE.getStatus().equals(status)) {
			return ALL_RELEASE.getName();
		}
		if (OFF_THE_SHELF.getStatus().equals(status)) {
			return OFF_THE_SHELF.getName();
		}
		if (GRAY_VERIFY_FAIL.getStatus().equals(status)) {
			return GRAY_VERIFY_FAIL.getName();
		}
		return null;
	}
}
