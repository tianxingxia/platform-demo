package com.yk.platform.common.util;

import java.util.UUID;

public class UUIDUtil {
	/**
	 * 获得一个UUID
	 * @return
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		return uuid.substring(0,8)+uuid.substring(9,13)+uuid.substring(14,18)+uuid.substring(19,23)+uuid.substring(24); 
	}
	
}
