/**
 * 
 */
package com.yk.platform.common.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * ***********************************
 * @author sandy
 * @project aider
 * @create_date 2013-10-11 下午9:55:55 ***********************************
 */
public class ObjectUtils
{
    /**
     * 集合是否为空 ***********************************
     * @author sandy
     * @create_date 2013-10-11 下午10:03:09
     * @param t
     * @return ***********************************
     */
    public static boolean isEmpty(Collection<?> s)
    {
        if (null == s)
        {
            return true;
        }
        return s.isEmpty();
    }

    /**
     * map是否为空 ***********************************
     * @author sandy
     * @create_date 2013-10-11 下午10:03:09
     * @param t
     * @return ***********************************
     */
    public static boolean isEmpty(Map<?, ?> s)
    {
        if (null == s)
        {
            return true;
        }
        return s.isEmpty();
    }

    /**
     * 字符串是否为空 ***********************************
     * @author sandy
     * @create_date 2013-10-11 下午10:03:09
     * @param t
     * @return ***********************************
     */
    public static boolean isEmpty(String s)
    {
        if (null == s)
        {
            return true;
        }
        return s.toString().trim().length() <= 0;
    }

    /**
     * 对象是否为空 ***********************************
     * @author sandy
     * @create_date 2013-10-11 下午10:03:09
     * @param t
     * @return ***********************************
     */
    public static <T> boolean isEmpty(T s)
    {
        if (null == s)
        {
            return true;
        }
        return false;

    }

    /**
     * 对象是否为空 ***********************************
     * @author sandy
     * @create_date 2013-10-11 下午10:03:09
     * @param t
     * @return ***********************************
     */
    public static <T> boolean isEmpty(T[] s)
    {
        if (null == s)
        {
            return true;
        }
        return Array.getLength(s) < 1;
    }

    /**
     * 集合不为空 ***********************************
     * @author sandy
     * @create_date 2013-10-11 下午10:03:09
     * @param t
     * @return ***********************************
     */
    public static boolean isNotEmpty(Collection<?> s)
    {
        if (null == s)
        {
            return false;
        }
        return !s.isEmpty();
    }

    /**
     * map不为空 ***********************************
     * @author sandy
     * @create_date 2013-10-11 下午10:03:09
     * @param t
     * @return ***********************************
     */
    public static boolean isNotEmpty(Map<?, ?> s)
    {
        if (null == s)
        {
            return false;
        }
        return !s.isEmpty();
    }

    /**
     * 字符串不为空 ***********************************
     * @author sandy
     * @create_date 2013-10-11 下午10:03:09
     * @param t
     * @return ***********************************
     */
    public static boolean isNotEmpty(String s)
    {
        if (null == s)
        {
            return false;
        }
        return s.toString().trim().length() > 0;
    }

    /**
     * 对象是否为空 ***********************************
     * @author sandy
     * @create_date 2013-10-11 下午10:03:09
     * @param t
     * @return ***********************************
     */
    public static <T> boolean isNotEmpty(T s)
    {
        if (null == s)
        {
            return false;
        }
        return !isEmpty(s);
    }

    public static String toFirstLetterUpperCase(String str)
    {
        if (str == null || str.length() < 2)
        {
            return str;
        }
        String firstLetter = str.substring(0, 1).toUpperCase();
        return firstLetter + str.substring(1, str.length());
    }

    /**
     * 整型转换为4位字节数组
     * @param intValue
     * @return
     */
    public static byte[] int2Byte(int intValue)
    {
        byte[] b = new byte[4];
        for (int i = 0; i < 4; i++)
        {
            b[i] = (byte) (intValue >> 8 * (3 - i) & 0xFF);
            // System.out.print(Integer.toBinaryString(b[i])+" ");
            // System.out.print((b[i] & 0xFF) + " ");
        }
        return b;
    }

    /**
     * 4位字节数组转换为整型
     * @param b
     * @return
     */
    public static int byte2Int(byte[] b)
    {
        int intValue = 0;
        // int tempValue = 0xFF;
        for (int i = 0; i < b.length; i++)
        {
            intValue += (b[i] & 0xFF) << (8 * (3 - i));
            // System.out.print(Integer.toBinaryString(intValue)+" ");
        }
        return intValue;
    }

    /**
     * 转换list为 id列表 ***********************************
     * @author sandy
     * @create_date 2013-10-11 下午10:03:18
     * @param t
     * @return ***********************************
     */
    public static <T> String listToString(Collection<T> t, String keyName)
    {
        String methodName = "";
        StringBuilder keys = new StringBuilder();
        try
        {
            for (T t2 : t)
            {
                methodName = "get" + keyName.substring(0, 1).toUpperCase() + keyName.substring(1);
                Method method = t2.getClass().getDeclaredMethod(methodName);
                Object res = method.invoke(t2);
                if (null != res)
                {
                    keys.append(res);
                    keys.append(",");
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        if (keys.length() > 0)
        {
            return keys.substring(0, keys.length() - 1);
        }
        else
        {
            return "";
        }
    }

    /**
     * @author Jon Chiang
     * @create_date 2014-8-7 上午10:16:59
     * @param score
     * @return
     */
    public static Float parseFloat(String score)
    {
        if (isNotEmpty(score))
        {
            if (isDouble(score))
            {
                return Float.valueOf(score);
            }
        }
        return 0f;
    }

    /**
     * @author Jon Chiang
     * @create_date 2014-8-7 上午10:16:59
     * @param score
     * @return
     */
    public static Integer parseInt(String score)
    {
        if (isNotEmpty(score))
        {
            if (isDouble(score))
            {
                return Integer.valueOf(score);
            }
        }
        return 0;
    }

    public static final Pattern integerPattern = Pattern.compile("^[-\\+]?[\\d]*$");

    /**
     * @author Jon Chiang
     * @create_date 2014-8-7 上午10:23:15
     * @param str
     * @return
     */
    public static boolean isInteger(String str)
    {
        return integerPattern.matcher(str).matches();
    }

    /*
     * @param str
     * 
     * @return
     */
    public static final Pattern floatPattern = Pattern.compile("^[-\\+]?[.\\d]*$");

    /**
     * 判断是否为浮点数，包括double和float
     * @author Jon Chiang
     * @create_date 2014-8-7 上午10:22:54
     * @param str传入的字符串
     * @return是浮点数返回true,否则返回false
     */
    public static boolean isDouble(String str)
    {
        return floatPattern.matcher(str).matches();
    }

    /**
     * @author Jon Chiang
     * @create_date 2014-8-8 上午11:26:33
     * @param difficulty
     * @return
     */
    public static byte stringToByte(String difficulty)
    {

        if (ObjectUtils.isNotEmpty(difficulty))
        {
            if (difficulty.length() == 1)
            {
                return Byte.valueOf(difficulty);
            }
        }
        return (byte) 0;
    }

    /**
     * @author Jon Chiang
     * @create_date 2014-9-1 下午5:26:29
     * @param paperIdSb
     * @return
     */
    public static String setToString(Set<Integer> set)
    {
        if (isEmpty(set))
        {
            return "";
        }
        String ids = set.toString();
        return ids.substring(1, ids.length() - 1);
    }

    /**
     * 设置属性值到对象中
     * @param obj
     * @param attributeName
     * @param value
     */
    public static void setAttributeToObj(Object obj, String attributeName, String value)
    {
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();// 获得属性
        for (Field field : fields)
        {
            if (field.getName().equals(attributeName))
            {
                String firstLetter = attributeName.substring(0, 1).toUpperCase();
                String setMethodName = "set" + firstLetter + attributeName.substring(1);
                Method setMethod;
                try
                {
                    setMethod = clazz.getMethod(setMethodName, new Class[] { field.getType() });
                    setMethod.invoke(obj, new Object[] { value });
                }
                catch (Exception e)
                {
                    System.out.println("通过反射来设置" + attributeName + "的值时出错！\n" + e.getMessage());
                }
            }
        }
    }

    public static String getAttributeFromObj(Object obj, String className, String attributeName)
    {
        Class<?> clazz;
        String value = null;
        try
        {
            clazz = Class.forName(className);
            if (!obj.getClass().equals(clazz))
            {
                return null;
            }
            PropertyDescriptor pd = new PropertyDescriptor(attributeName, clazz);
            Method getMethod = pd.getReadMethod();// 获得get方法
            value = (String) getMethod.invoke(obj);
        }
        catch (Exception e)
        {
            System.out.println("通过反射来获取" + attributeName + "的值时出错！\n" + e.getMessage());
        }
        return value;
    }
}
