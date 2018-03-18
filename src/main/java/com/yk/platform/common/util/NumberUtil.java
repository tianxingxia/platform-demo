package com.yk.platform.common.util;

import java.text.DecimalFormat;

public class NumberUtil {
	public static String formatNumber(Double d) {
		DecimalFormat df = new DecimalFormat("0.00");

		return df.format(d);

	}
}
