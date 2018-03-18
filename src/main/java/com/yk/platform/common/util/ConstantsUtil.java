package com.yk.platform.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 读取constants.properties的工具类,可以通过这个类来读取系统预定义的一些常量
 * @author Administrator
 */
public class ConstantsUtil {

	private static final Logger logger = Logger.getLogger(ConstantsUtil.class);

	private static Properties constants = new Properties();

	static {
		InputStream in = ConstantsUtil.class.getClassLoader().getResourceAsStream("constants.properties");
		try {
			BufferedReader bf = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			constants.load(bf);
		} catch (IOException e) {
			logger.error(e.toString());
		}
	}

	public static String getConstant(String key) {
		return constants.getProperty(key);
	}

}