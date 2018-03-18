/**
 * 
 */
package com.yk.platform.common.util;

import java.io.Closeable;
import java.io.IOException;

public class IOUtil {

	public static void closeQuietly(Closeable... closeables) {
		for (Closeable closeable : closeables) {
			if (null != closeable) {
				try {
					closeable.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
