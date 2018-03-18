package com.yk.platform.common.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * 字符串常用方法，建议里面的方法写成静态的
 */
public class StrUtil {
	private static final Logger LOGGER = Logger.getLogger(StrUtil.class);
	private static final String DEFALUT_DATETIME_FORMATSTRING = "yyyy-MM-dd HH:mm:ss";
	private static final String DEFALUT_DATE_FORMATSTRING = "yyyy-MM-dd";

	public static String getGuidId() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	public static void main(String[] args) {
		// test
		System.out.println(getGuidId());
	}

	public static Date parse(String source) {
		try {
			SimpleDateFormat format = new SimpleDateFormat();
			String formatString = source.trim();
			if (formatString.indexOf(":") > 0) {
				format.applyPattern(DEFALUT_DATETIME_FORMATSTRING);
			} else {
				format.applyPattern(DEFALUT_DATE_FORMATSTRING);
			}
			return format.parse(formatString);
		} catch (ParseException e) {
			LOGGER.error("ParseException", e);
		}
		return null;
	}

	public static double stringToDouble(Object str) {
		if (str == null) {
			return 0;
		}
		try {
			return Double.parseDouble(str.toString());
		} catch (Exception e) {
			LOGGER.error("parse error", e);
			return 0;
		}
	}

	public static Double toDouble(Object str) {
		if (str == null) {
			return null;
		}
		try {
			return Double.parseDouble(str.toString());
		} catch (Exception e) {
			LOGGER.error("parse error", e);
			return 0D;
		}
	}

	public static Float stringToFloat(Object str) {
		if (str == null || "".equals(str.toString().trim())) {
			return null;
		}
		try {
			return Float.parseFloat(str.toString());
		} catch (Exception e) {
			LOGGER.error("parse error", e);
			return 0f;
		}
	}

	public static int stringToInt(Object str) {
		if (str == null) {
			return 0;
		}
		try {
			return Integer.parseInt(str.toString());
		} catch (Exception e) {
			LOGGER.error("parse error", e);
			return 0;
		}
	}

	public static long stringToLong(Object str) {
		if (str == null) {
			return 0;
		}
		try {
			return Long.parseLong(str.toString());
		} catch (Exception e) {
			LOGGER.error("parse error", e);
			return 0;
		}
	}

	public static int stringToInt(String str, int def) {
		if (str == null) {
			return def;
		}
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
			LOGGER.error("parse error", e);
			return def;
		}
	}

	public static String format(Date date) {
		SimpleDateFormat format = new SimpleDateFormat(DEFALUT_DATETIME_FORMATSTRING);
		return format.format(date);
	}

	public static String format(Date date, String formatString) {
		SimpleDateFormat format = new SimpleDateFormat(formatString);
		return format.format(date);
	}

	/**
	 * 判断�?个字符串是否
	 * 
	 * @param numberString
	 * @return
	 */
	public static Boolean isInt(String numberString) {
		if (StringUtils.isBlank(numberString)) {
			return false;
		}
		Pattern p = Pattern.compile("-*" + "\\d*");
		Matcher m = p.matcher(numberString);
		boolean b = m.matches();
		return b;
	}

	public static Boolean isDouble(String numberString) {
		try {
			Double.parseDouble(numberString);
		} catch (Exception e) {
			LOGGER.error("parse error", e);
			return false;
		}
		return true;
	}

	public static Boolean isLong(String numberString) {
		try {
			Long.parseLong(numberString);
		} catch (Exception e) {
			LOGGER.error("parse error", e);
			return false;
		}
		return true;
	}

	/**
	 * 在客户端浏览器中等侍t秒后，跳转至url
	 * 
	 * @param msg
	 *            String 等待时的提示信息
	 * @param t
	 *            int 等待秒数
	 * @param url
	 *            String
	 * @return String
	 */
	public static String waitJump(String where, String msg, int t, String url) {
		String str = "";
		String spanid = "id" + System.currentTimeMillis();
		str = "\n<ol><b><span id=" + spanid + "> 3 </span>";
		str += "秒钟后系统将自动跳转�?" + where + "... </b></ol>";
		str += "<ol>" + msg + "</ol>";
		str += "<script language=javascript>\n";
		str += "<!--\n";
		str += "function tickout(secs) {\n";
		str += spanid + ".innerText = secs;\n";
		str += "if (--secs > 0) {\n";
		str += "  setTimeout('tickout(' +secs + ')', 1000);\n";
		str += "}\n";
		str += "}\n";
		str += "tickout(" + t + ");\n";
		str += "-->\n";
		str += "</script>\n";
		str += "<meta http-equiv=refresh content=" + t + ";url=" + url + ">\n";
		return str;
	}

	/**
	 * 对字符串进行URLEncoder.encode编码
	 * 
	 * @param str
	 *            String
	 * @param charset
	 *            String
	 * @return String
	 */
	public static String urlEncode(String str, String charset) {
		if (str == null) {
			return "";
		}

		String s = str;

		try {
			s = URLEncoder.encode(str, charset);
		} catch (Exception e) {
			LOGGER.error("encode error", e);
		}
		return s;
	}

	/**
	 * 对字符串进行utf-8编码的URLEncoder.encode
	 * 
	 * @param str
	 *            String
	 * @return String
	 */
	public static String urlEncode(String str) {
		return urlEncode(str, "utf-8");
	}

	/**
	 * 对字符串进行URLDecoder.decode解码
	 * 
	 * @param str
	 *            String
	 * @param charset
	 *            String
	 * @return String
	 */
	public static String urlDecode(String str, String charset) {
		if (str == null) {
			return "";
		}

		String s = str;

		try {
			s = URLDecoder.decode(str, charset);
		} catch (Exception e) {
			LOGGER.error("encode error", e);
		}
		return s;
	}

	/**
	 * 对字符串进行utf-8解码的URLDecoder.decode
	 * 
	 * @param str
	 *            String
	 * @return String
	 */
	public static String urlDecode(String str) {
		return urlDecode(str, "utf-8");
	}

	/**
	 * 将ISO8859_1转换为utf-8
	 * 
	 * @param strIn
	 *            String
	 * @return String
	 */
	public static String unicodeToUTF8(String strIn) {
		String strOut = "";
		if (strIn == null) {
			return "";
		}
		try {
			byte[] b = strIn.getBytes("ISO8859_1");
			// strOut = new String(b, "GB2312");//转为GB2312
			strOut = new String(b, "utf-8"); // 转为UTF-8
		} catch (Exception e) {
			LOGGER.error("encode error", e);
		}
		return strOut;
	}

	/**
	 * 判断给定id字符串是否为大于1的数字，不是则返�?0
	 * 
	 * @param str
	 * @return
	 */
	public static int getIdIfIsId(Object obj) {
		int id = 0;
		if (obj == null || StringUtils.isEmpty(obj.toString())) {
			return id;
		}
		String str = obj.toString();
		if (StrUtil.isInt(str)) {
			id = Integer.parseInt(str);
		}
		if (id < 0) {
			id = 0;
		}
		return id;
	}

	/**
	 * @Description: 返回long数值，默认返回0
	 * @param obj
	 * @return
	 */
	public static long getLongVal(Object obj) {
		long val = 0;
		if (obj == null) {
			return val;
		}
		try {
			val = Long.parseLong(String.valueOf(obj));
		} catch (Exception e) {
			LOGGER.error("getLongVal", e);
		}
		return val;
	}

	/**
	 * 解析AJAX传�?�的中文字符
	 * 
	 * @param obj
	 * @return
	 */
	public static String ajaxDecode(String obj) {
		String str = null;
		try {
			str = URLDecoder.decode(obj, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("UnsupportedEncodingException", e);
		}
		return str;
	}

	/**
	 * 判断两个日期是否是同�?�?
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isOneDay(Date date1, Date date2) {
		boolean flag = false;

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String day1 = dateFormat.format(date1);
		String day2 = dateFormat.format(date2);
		if (day1.equals(day2)) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 判断是否今天
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isToday(Date date) {

		return isOneDay(date, new Date());

	}

	/**
	 * 在客户端浏览器中先alert，然后重定向至toUrl
	 * 
	 * @param msg
	 *            String
	 * @param toUrl
	 *            String
	 * @return String
	 */
	public static String alertRedirect(String msg, String toUrl) {
		String str = "";
		str = "<script language=javascript>\n";
		str += "<!--\n";
		str += "alert(\"" + msg + "\")\n";
		if (StringUtils.isNotEmpty(toUrl)) {
			str += "location.href=\"" + toUrl + "\"\n";
		}
		str += "-->\n";
		str += "</script>\n";
		return str;
	}

	/**
	 * 在客户端浏览器重定向至toUrl
	 * 
	 * @param msg
	 *            String
	 * @param toUrl
	 *            String
	 * @return String
	 */
	public static String redirect(String toUrl) {
		String str = "";
		str = "<script language=javascript>\n";
		str += "<!--\n";
		if (StringUtils.isNotBlank(toUrl)){
			str += "location.href=\"" + toUrl + "\"\n";}
		str += "-->\n";
		str += "</script>\n";
		return str;
	}

	/**
	 * 过滤掉字符串中的html标签
	 * 
	 * @param inputString
	 * @return
	 */
	public static String htmlToText(String inputString) {
		String htmlStr = inputString; // 含html标签的字符串
		String textStr = "";
		java.util.regex.Pattern pScript;
		java.util.regex.Matcher mScript;
		java.util.regex.Pattern pStyle;
		java.util.regex.Matcher mStyle;
		java.util.regex.Pattern pHtml;
		java.util.regex.Matcher mHtml;
		try {
			String regExScript = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{�?<script[^>]*?>[\\s\\S]*?<\\/script>
			// }
			String regExStyle = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{�?<style[^>]*?>[\\s\\S]*?<\\/style>
			// }
			String regExHtml = "<[^>]+>"; // 定义HTML标签的正则表达式

			pScript = Pattern.compile(regExScript, Pattern.CASE_INSENSITIVE);
			mScript = pScript.matcher(htmlStr);
			htmlStr = mScript.replaceAll(""); // 过滤script标签

			pStyle = Pattern.compile(regExStyle, Pattern.CASE_INSENSITIVE);
			mStyle = pStyle.matcher(htmlStr);
			htmlStr = mStyle.replaceAll(""); // 过滤style标签

			pHtml = Pattern.compile(regExHtml, Pattern.CASE_INSENSITIVE);
			mHtml = pHtml.matcher(htmlStr);
			htmlStr = mHtml.replaceAll(""); // 过滤html标签
			htmlStr = htmlStr.replaceAll("&nbsp;", "");
			htmlStr = htmlStr.replaceAll("&quot;", "");
			htmlStr = htmlStr.replaceAll("&#039;", "");
			htmlStr = htmlStr.replaceAll("&amp;", "");
			htmlStr = htmlStr.replaceAll("&ldquo;", "");
			htmlStr = htmlStr.replaceAll("&mdash;", "");
			htmlStr = htmlStr.replaceAll("&rdquo;", "");
			textStr = htmlStr;

		} catch (Exception e) {
			LOGGER.error("htmlToText error", e);
		}

		return textStr;// 返回文本字符�?
	}

	/**
	 * 截取字符串长�?
	 * 
	 * @param inputString
	 * @param len
	 * @return
	 */
	public static String subString(String inputString, int len) {
		String str = "";
		try {
			if (inputString.length() > len) {
				str = inputString.substring(0, len) + "...";
			} else {
				str = inputString;
			}
		} catch (Exception e) {
			LOGGER.error("subString error", e);
		}
		return str;
	}

	/**
	 * 字符串不足多少位左侧�?0
	 * 
	 * @param inputString
	 * @param d
	 *            位数
	 * @return
	 */
	public static String padLeft(String inputString, int d) {
		String str = inputString;
		while (str.length() < d) {
			str = "0" + str;
		}
		return str;
	}

	/**
	 * 将GB2312转换为utf-8
	 * 
	 * @param strIn
	 *            String
	 * @return String
	 */
	public static String gb2312ToUTF8(String strIn) {
		String strOut = "";
		if (strIn == null) {
			return "";
		}
		try {
			byte[] b = strIn.getBytes("GB2312");
			// strOut = new String(b, "GB2312");//转为GB2312
			strOut = new String(b, "utf-8"); // 转为UTF-8
		} catch (Exception e) {
			LOGGER.error("gb2312ToUTF8 error", e);
		}
		return strOut;
	}

	/**
	 * 将utf-8转换为GB2312
	 * 
	 * @param strIn
	 *            String
	 * @return String
	 */
	public static String utf8ToGB2312(String strIn) {
		String strOut = "";
		if (strIn == null) {
			return "";
		}
		try {
			byte[] b = strIn.getBytes("utf-8");
			strOut = new String(b, "GB2312"); // 转为GB2312
		} catch (Exception e) {
			LOGGER.error("utf8ToGB2312 error", e);
		}
		return strOut;
	}

	/**
	 * 得打6位无重复随机�?
	 * 
	 * @return
	 */
	public static String getRandNumber() {
		StringBuilder strbufguess = new StringBuilder();
		int[] nums = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		Random rannum = new Random();
		int count;
		int i = 0, tempI = 0;
		for (int j = 10; j > 4; j--) {
			i = 0;
			tempI = 0;
			count = rannum.nextInt(j);
			while (i <= count) {
				if (nums[tempI] == -1){
					tempI++;}
				else {
					i++;
					tempI++;
				}
			}
			strbufguess.append(Integer.toString(nums[tempI - 1]));
			nums[tempI - 1] = -1;
		}
		String strguess = strbufguess.toString();
		rannum = null;
		strbufguess = null;
		nums = null;
		return strguess;
	}

	public static boolean isContain(String all, String in, String reg) {
		boolean rs = false;
		if (all == null || in == null || reg == null) {
			return rs;
		}
		String[] strs = all.split(reg);
		for (int i = 0; i < strs.length; i++) {
			if (strs[i].equals(in)) {
				return true;
			}
		}
		return rs;
	}

	public static String getFileName(String str) {
		if (StringUtils.isBlank(str)) {
			return "";
		}
		if (str.indexOf("/") > -1) {
			return str.substring(str.lastIndexOf("/") + 1, str.length());
		}
		return "";
	}

	/**
	 * 
	 * 方法描述: 计算百分�?
	 * 
	 * 创建时间: 2012-5-17 下午04:46:35
	 */
	public static String getPercent(int y, int z) {
		String baifenbi = "";// 接受百分比的�?
		double baiy = y * 1.0;
		double baiz = z * 1.0;
		double fen = baiy / baiz;
		DecimalFormat df1 = new DecimalFormat("##%");// ##.00%
		baifenbi = df1.format(fen);
		return baifenbi;
	}

	/**
	 * 
	 * 方法描述: 如果不含小数，则去掉小数
	 * 
	 * 创建时间: 2012-12-11 下午03:32:36
	 */
	public static String doubleToStr(double f) {
		String str = new DecimalFormat("#0.00").format(f).toString();
		String[] array = str.split("\\.");
		if ("00".equals(array[1])) {
			str = array[0];
		} else if (array[1].endsWith("0")) {
			str = array[0] + "." + array[1].substring(0, 1);
		}
		return str;
	}

	// 全角转半角的 转换函数

	public static final String fullHalfChange(String qJstr) {
		StringBuilder outStrBuf = new StringBuilder("");
		String tStr = "";
		byte[] b = null;
		for (int i = 0; i < qJstr.length(); i++) {
			try {
				tStr = qJstr.substring(i, i + 1);
				// 全角空格转换成半角空�?
				if ("�?".equals(tStr)) {
					outStrBuf.append(" ");
					continue;
				}
				b = tStr.getBytes("unicode");
				// 得到 unicode 字节数据
				if (b[2] == -1) {
					// 表示全角�?
					b[3] = (byte) (b[3] + 32);
					b[2] = 0;
					outStrBuf.append(new String(b, "unicode"));
				} else {
					outStrBuf.append(tStr);
				}
			} catch (UnsupportedEncodingException e) {
				LOGGER.error("UnsupportedEncodingException", e);
			}
		} // end for.
		return outStrBuf.toString();
	}

	// 半角转全�?
	public static final String halfFullchange(String qJstr) {
		StringBuilder outStrBuf = new StringBuilder("");
		String sTtr = "";
		byte[] b = null;
		for (int i = 0; i < qJstr.length(); i++) {
			try {
				sTtr = qJstr.substring(i, i + 1);
				if (" ".equals(sTtr)) {
					// 半角空格
					outStrBuf.append(sTtr);
					continue;
				}
				b = sTtr.getBytes("unicode");
				if (b[2] == 0) {
					// 半角?
					b[3] = (byte) (b[3] - 32);
					b[2] = -1;
					outStrBuf.append(new String(b, "unicode"));
				} else {
					outStrBuf.append(sTtr);
				}
			} catch (UnsupportedEncodingException e) {
				LOGGER.error("UnsupportedEncodingException", e);
			}
		}
		return outStrBuf.toString();
	}

	public static String percent(double p1, double p2) {
		String str;
		double p3 = p1 / p2;
		NumberFormat nf = NumberFormat.getPercentInstance();
		nf.setMinimumFractionDigits(2);
		str = nf.format(p3);
		return str;
	}

	public static String msgRedirectUrl(int type, String returnUrl, String msg) {
		return "redirect:/admin/msg?type=" + type + "&url=" + StrUtil.urlEncode(returnUrl) + "&msg="
				+ StrUtil.urlEncode(msg);
	}

	public static String getFormatSize(String sizeStr) {
		long size = StrUtil.stringToLong(sizeStr);
		double kiloByte = size / (double) 1024;
		if (kiloByte < 1) {
			return size + "Byte(s)";
		}

		double megaByte = kiloByte / 1024;
		if (megaByte < 1) {
			BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
			return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
		}

		double gigaByte = megaByte / 1024;
		if (gigaByte < 1) {
			BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
			return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
		}

		double teraBytes = gigaByte / 1024;
		if (teraBytes < 1) {
			BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
			return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
		}
		BigDecimal result4 = new BigDecimal(teraBytes);
		return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
	}

	/**
	 * @Description: 得到异常详细信息
	 * @author yxy
	 * @date Oct 18, 2014 10:43:08 AM
	 */
	public static String getStackTrace(Exception e) {
		StringBuilder sOut = new StringBuilder();
		StackTraceElement[] trace = e.getStackTrace();
		for (StackTraceElement s : trace) {
			sOut.append("\tat ").append(s).append("\r\n");
		}
		return sOut.toString();
	}

	public static String dateToTodayFormat(String dateStr) {
		// 时间规范应该是yyyy-MM-dd hh:mm:ss.格式不符合异常情况直接返回
		if (StringUtils.isBlank(dateStr) || dateStr.trim().length() <= 10 || dateStr.indexOf("-") != 4) {
			return dateStr;
		}
		SimpleDateFormat format = new SimpleDateFormat(DEFALUT_DATE_FORMATSTRING);
		String todayStr = format.format(new Date());
		String paraDate = dateStr.substring(0, 10);
		// 日期小于今天不用替换
		if (parse(paraDate).before(parse(todayStr))) {
			return dateStr;
		} else {
			return todayStr + dateStr.substring(10);
		}
	}

	/**
	 * @Description: 是否为空字符串
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (StringUtils.isEmpty(str)) {
			return true;
		}
		return false;
	}

	/**
	 * @Description: 是否为空字符串，忽略空格
	 * @param str
	 * @return
	 */
	public static boolean isEmptyIgnoreSpaces(String str) {
		if (StringUtils.isBlank(str)) {
			return true;
		}
		return false;
	}

	/**
	 * @Description: 回车换行转为空格
	 * @param str
	 * @return
	 */
	public static String replaceReturn2Space(String str) {
		if (StringUtils.isBlank(str)) {
			return "";
		}	
		return str.replace((char) 10, (char) 32).replace((char) 13, (char) 32);
	}

}
