package com.yk.platform.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    /**
     * 判断str是否为null或者是否为""
     * @param str
     * @return
     */
    public static boolean isNull(String str) {
        if (str == null || str.equals("")) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 判断str是否非空
     * @param str
     * @return
     */
    public static boolean isNotNull(String str) {
        if (isNull(str)) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 从字符串中提取出数字，拼接成字符串返回
     * @param str
     * @return
     */
    public static String getNumberStr(String str) {
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    public static String getCurTimeStr() {
        long time = System.currentTimeMillis();
        return String.valueOf(time);
    }

    public static boolean isLikeBindNumber(String bindNumber) {
        if (StringUtil.isNull(bindNumber)) {
            return false;
        }
        else if (bindNumber.length() != 16) {
            return false;
        }
        /* 小写字母区间：97-122 */
        for (char c : bindNumber.toCharArray()) {
            if (c < 97 || c > 122) {
                return false;
            }
        }
        return true;
    }

    public static boolean isLikeNumber(String bindNumber) {
        if (StringUtil.isNull(bindNumber)) {
            return false;
        }
        else if (bindNumber.length() >= 16) {
            return false;
        }
        // 首字母不是+号且不是数字,返回false
        char[] arr = bindNumber.toCharArray();
        if (arr[0] != 43 && (arr[0] < 48 || arr[0] > 57)) {
            return false;
        }

        for (int i = 1; i < arr.length; i++) {
            if (arr[0] < 48 || arr[0] > 57) {
                return false;
            }
        }

        return true;
    }

    public static String getPhonePrex(String number) {
        if (!isLikeNumber(number)) {
            return null;
        }
        else {
            return number.substring(0, 7);
        }
    }

    public static void main(String[] args) {
        String number = "a12341241234";
        System.out.println(getPhonePrex(number));
    }
}
