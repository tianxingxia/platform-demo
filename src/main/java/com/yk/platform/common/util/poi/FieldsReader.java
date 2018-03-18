/**********************************************************************
 *                                                                  *
 * Copyright(C) 2014 Shenzhen Parameter Navigator Technology        *
 * Limited. All rights reserved.               						*
 *                                                                  *
 * The software is developed by PNT. Using, reproducing,      		*
 * modification and distribution are prohibited without             *
 * PNT's permission.                                          		*
 *                                                                  *
 *********************************************************************/
package com.yk.platform.common.util.poi;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.yk.platform.business.excel.entity.ValidateBean;

/**
 * 
 * <p>Description: </p>
 * @date 2015年11月30日
 * @author yangkai
 * @version 1.0
 * <p>Company:bbk</p>
 * <p>Copyright:Copyright(c)2015</p>
 */
public class FieldsReader {
    private static DocumentBuilderFactory factory;
    private static DocumentBuilder builder;
    private static Document doc;
    private static Logger logger = Logger.getLogger(FieldsReader.class.getName());

    /**
     * 获得列名-属性映射map
     * @param templetePath
     * @param templ_id
     * @return  
     * @throws
     */
    public static Map<String, String> getTempleteMap(String templetePath, String templ_id) {
        Map<String, String> map = new HashMap<String, String>();
        Element root;
        NodeList level_1_List;
        NodeList level_2_List;
        Element level_1_Element;
        Element level_2_Element;
        try {
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            doc = builder.parse(templetePath);
            root = doc.getDocumentElement();
            level_1_List = root.getElementsByTagName("sheet");
            for (int i = 0; i < level_1_List.getLength(); i++) {
                level_1_Element = (Element) level_1_List.item(i);
                if (templ_id.equals(level_1_Element.getAttribute("id"))) {
                    level_2_List = level_1_Element.getElementsByTagName("field");
                    for (int j = 0; j < level_2_List.getLength(); j++) {
                        level_2_Element = (Element) level_2_List.item(j);
                        String key = level_2_Element.getAttribute("label");
                        String value = level_2_Element.getAttribute("name");
                        map.put(key, value);
                    }
                }
            }
        }
        catch (ParserConfigurationException e) {
            logger.error(e.getMessage(), e);
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        catch (SAXException e) {
            logger.error(e.getMessage(), e);
        }
        return map;
    }

    /**
     * 获得列号-宽度映射map
     * @param templetePath
     * @param templ_id
     * @return  
     * @throws
     */
    public static Map<Integer, Integer> getWidthMap(String templetePath, String templ_id) {
        Map<Integer, Integer> map = new TreeMap<Integer, Integer>();
        Element root;
        NodeList level_1_List;
        NodeList level_2_List;
        Element level_1_Element;
        Element level_2_Element;
        Integer width = 6000;// 默认宽度为6000
        String value = null;
        try {
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            doc = builder.parse(templetePath);
            root = doc.getDocumentElement();
            level_1_List = root.getElementsByTagName("sheet");
            for (int i = 0; i < level_1_List.getLength(); i++) {
                level_1_Element = (Element) level_1_List.item(i);
                if (templ_id.equals(level_1_Element.getAttribute("id"))) {
                    level_2_List = level_1_Element.getElementsByTagName("field");
                    for (int j = 0; j < level_2_List.getLength(); j++) {// 循环每一个元素，设置对应列的宽度
                        level_2_Element = (Element) level_2_List.item(j);
                        value = level_2_Element.getAttribute("width");
                        if (value != null && !value.trim().equals("")) {
                            try {
                                map.put(j, Integer.parseInt(value));
                            }
                            catch (NumberFormatException e) {
                                logger.error(e.getMessage(), e);
                                map.put(j, width);
                            }
                        }
                        else {
                            map.put(j, width);
                        }
                    }
                }
            }
        }
        catch (ParserConfigurationException e) {
            logger.error(e.getMessage(), e);
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        catch (SAXException e) {
            logger.error(e.getMessage(), e);
        }
        return map;
    }

    /**
     * 获得标题
     * @Description getTitles
     * @param templetePath
     * @param templ_name
     * @return
     */
    public static String getTitles(String templetePath, String templ_id) {
        Element root;
        NodeList level_1_List;
        NodeList level_2_List;
        Element level_1_Element;
        Element level_2_Element;
        String titles = null;
        StringBuffer titleBuffer = new StringBuffer();
        try {
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            doc = builder.parse(templetePath);
            root = doc.getDocumentElement();
            level_1_List = root.getElementsByTagName("sheet");
            for (int i = 0; i < level_1_List.getLength(); i++) {
                level_1_Element = (Element) level_1_List.item(i);
                if (level_1_Element.getAttribute("id").trim().equals(templ_id)) {
                    level_2_List = level_1_Element.getElementsByTagName("field");
                    for (int j = 0; j < level_2_List.getLength(); j++) {
                        level_2_Element = (Element) level_2_List.item(j);
                        titleBuffer.append(level_2_Element.getAttribute("label"));
                        titleBuffer.append(",");
                    }
                }
            }
        }
        catch (ParserConfigurationException e) {
            logger.error(e.getMessage(), e);
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        catch (SAXException e) {
            logger.error(e.getMessage(), e);
        }

        if (titleBuffer != null && titleBuffer.length() > 0) {
            titles = titleBuffer.substring(0, titleBuffer.lastIndexOf(","));
        }
        return titles;
    }

    public static String getLevel1AttributeByName(String templetePath, String templ_id, String attName) {
        Element root;
        NodeList level_1_List;
        Element level_1_Element;
        try {
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            doc = builder.parse(templetePath);
            root = doc.getDocumentElement();
            level_1_List = root.getElementsByTagName("sheet");
            for (int i = 0; i < level_1_List.getLength(); i++) {
                level_1_Element = (Element) level_1_List.item(i);
                if (level_1_Element.getAttribute("id").trim().equals(templ_id)) {
                    return level_1_Element.getAttribute(attName);
                }
            }
        }
        catch (ParserConfigurationException e) {
            logger.error(e.getMessage(), e);
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        catch (SAXException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static String getLevel2AttributeByName(String templetePath, String templ_id, String name, String attName) {
        Element root;
        NodeList level_1_List;
        NodeList level_2_List;
        Element level_1_Element;
        Element level_2_Element;
        try {
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            doc = builder.parse(templetePath);
            root = doc.getDocumentElement();
            level_1_List = root.getElementsByTagName("sheet");
            for (int i = 0; i < level_1_List.getLength(); i++) {
                level_1_Element = (Element) level_1_List.item(i);
                if (level_1_Element.getAttribute("id").trim().equals(templ_id)) {
                    level_2_List = level_1_Element.getElementsByTagName("field");
                    for (int j = 0; j < level_2_List.getLength(); j++) {
                        level_2_Element = (Element) level_2_List.item(j);
                        if (level_2_Element.getAttribute("label").trim().equals(name.trim())) {
                            return level_2_Element.getAttribute(attName);
                        }
                    }
                }
            }
        }
        catch (ParserConfigurationException e) {
            logger.error(e.getMessage(), e);
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        catch (SAXException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static Object getFiledValue(Object object, String fieldName) {
        Object object2 = null;
        Class<?> classType = object.getClass();
        String firstLetter = fieldName.substring(0, 1).toUpperCase();
        String getMethodName = "get" + firstLetter + fieldName.substring(1);
        try {
            Method getMethod = classType.getMethod(getMethodName, new Class[] {});
            object2 = getMethod.invoke(object, new Object[] {});
        }
        catch (SecurityException e) {
            logger.error(e.getMessage(), e);
        }
        catch (NoSuchMethodException e) {
            logger.error(e.getMessage(), e);
        }
        catch (IllegalArgumentException e) {
            logger.error(e.getMessage(), e);
        }
        catch (IllegalAccessException e) {
            logger.error(e.getMessage(), e);
        }
        catch (InvocationTargetException e) {
            logger.error(e.getMessage(), e);
        }
        return object2;
    }

    public static Map<String, ValidateBean> getValidateBean(String templetePath, String templ_id) {
        Map<String, ValidateBean> map = new HashMap<String, ValidateBean>();
        Element root;
        NodeList level_1_List;
        NodeList level_2_List;
        Element level_1_Element;
        Element level_2_Element;
        String key = null;
        ValidateBean vBean;
        int minLength;
        int maxLength;
        double minSize;
        double maxSize;
        boolean nullAble;
        String minLengthstr = null;
        String maxLengthstr = null;
        String minSizestr = null;
        String maxSizestr = null;
        String nullAblestr = null;
        String customValidate = null;
        try {
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            doc = builder.parse(templetePath);
            root = doc.getDocumentElement();
            level_1_List = root.getElementsByTagName("sheet");
            for (int i = 0; i < level_1_List.getLength(); i++) {
                level_1_Element = (Element) level_1_List.item(i);
                if (templ_id.equals(level_1_Element.getAttribute("id"))) {
                    level_2_List = level_1_Element.getElementsByTagName("field");
                    for (int j = 0; j < level_2_List.getLength(); j++) {
                        vBean = new ValidateBean();
                        level_2_Element = (Element) level_2_List.item(j);
                        key = level_2_Element.getAttribute("label");
                        minLengthstr = getLevel2AttributeByName(templetePath, templ_id, key, "minLength");
                        maxLengthstr = getLevel2AttributeByName(templetePath, templ_id, key, "maxLength");
                        minSizestr = getLevel2AttributeByName(templetePath, templ_id, key, "minSize");
                        maxSizestr = getLevel2AttributeByName(templetePath, templ_id, key, "maxSize");
                        nullAblestr = getLevel2AttributeByName(templetePath, templ_id, key, "nullAble");

                        customValidate = getLevel2AttributeByName(templetePath, templ_id, key, "customValidate");
                        if (minLengthstr != null && minLengthstr.trim().length() > 0) {
                            minLength = Integer.parseInt(minLengthstr);
                            vBean.setMinLength(minLength);
                        }
                        if (maxLengthstr != null && maxLengthstr.trim().length() > 0) {
                            maxLength = Integer.parseInt(maxLengthstr);
                            vBean.setMaxLength(maxLength);
                        }
                        if (minSizestr != null && minSizestr.trim().length() > 0) {
                            minSize = Double.parseDouble(minSizestr);
                            vBean.setMinSize(minSize);
                        }
                        if (maxSizestr != null && maxSizestr.trim().length() > 0) {
                            maxSize = Double.parseDouble(maxSizestr);
                            vBean.setMaxSize(maxSize);
                        }
                        if (customValidate != null && customValidate.trim().length() > 0) {
                            vBean.setCustomValidate(customValidate);
                        }
                        if (nullAblestr != null) {
                            if (nullAblestr.trim().equalsIgnoreCase("false") || nullAblestr.trim().equalsIgnoreCase("true")) {
                                nullAble = Boolean.parseBoolean(nullAblestr);
                                vBean.setNullAble(nullAble);
                            }
                        }
                        map.put(key, vBean);
                    }
                }
            }
        }
        catch (ParserConfigurationException e) {
            logger.error(e.getMessage(), e);
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        catch (SAXException e) {
            logger.error(e.getMessage(), e);
        }
        return map;
    }

    /**
     * 调用自定义验证方法
     * 方法用途: <br>
     * 实现步骤: <br>
     * @param methodPath
     * @param value
     * @return
     */
    public static String customValidate(String methodPath, Object value) {
        String className = methodPath.substring(0, methodPath.lastIndexOf("."));
        String methodName = methodPath.substring(methodPath.lastIndexOf(".") + 1, methodPath.length());
        String res = null;
        try {
            Class<?> c = Class.forName(className);
            Method m = c.getMethod(methodName, Object.class);
            res = (String) m.invoke(c.newInstance(), new Object[] { value });
        }
        catch (ClassNotFoundException e) {
            res = "找不到验证类:" + className;
            logger.error(e.getMessage(), e);
        }
        catch (SecurityException e) {
            logger.error(e.getMessage(), e);
            res = "SecurityException:";
        }
        catch (NoSuchMethodException e) {
            logger.error(e.getMessage(), e);
            res = "找不到验证方法:" + methodName;
        }
        catch (IllegalArgumentException e) {
            logger.error(e.getMessage(), e);
            res = "传入参数错误:" + value.toString();
        }
        catch (IllegalAccessException e) {
            logger.error(e.getMessage(), e);
            res = "权限错误，请将验证方法" + methodName + "改为public";
        }
        catch (InvocationTargetException e) {
            logger.error(e.getMessage(), e);
            res = "InvocationTargetException";
        }
        catch (InstantiationException e) {
            logger.error(e.getMessage(), e);
            res = "InstantiationException";
        }
        return res;
    }

    public static String validateData(ValidateBean vBean, Object obj) {
        Integer minLength = vBean.getMinLength();
        Integer maxLength = vBean.getMaxLength();
        Double minSize = vBean.getMinSize();
        Double maxSize = vBean.getMaxSize();
        Boolean nullAble = vBean.isNullAble();
        String customValidate = vBean.getCustomValidate();
        if (!nullAble) { // 先判断是否允许值为空
            String objStr = null;
            if (obj != null) {
                objStr = obj.toString();
            }

            if (objStr != null && !objStr.trim().equals("")) {
                if (minLength != null && objStr.length() < minLength) {
                    logger.error(obj.toString().length() + "<最小长度:" + minLength);
                    return obj.toString().length() + "<最小长度:" + minLength;
                }

                if (maxLength != null && objStr.length() > maxLength) {
                    logger.error(obj.toString().length() + ">最大长度:" + maxLength);
                    return obj.toString().length() + ">最大长度:" + maxLength;
                }

                if (minSize != null && Double.parseDouble(objStr) < minSize) {
                    logger.error(Double.parseDouble(objStr) + "<最小值:" + minSize);
                    return Double.parseDouble(objStr) + "<最小值:" + minSize;
                }

                if (maxSize != null && Double.parseDouble(objStr) > maxSize) {
                    logger.error(Double.parseDouble(objStr) + ">最大值:" + maxSize);
                    return Double.parseDouble(objStr) + ">最大值:" + maxSize;
                }

                if (customValidate != null && customValidate.trim().length() > 0) {
                    String customValidateMsg = customValidate(customValidate, obj);
                    if (!customValidateMsg.trim().equalsIgnoreCase("true")) {
                        logger.error(obj + "不符合用户自定义法方法:" + customValidate + "," + customValidateMsg);
                        return obj + "不符合用户自定义法方法:" + customValidate + "," + customValidateMsg;
                    }
                }
                return "true";
            }
            else {
                logger.error("不能为空");
                return "不能为空";
            }
        }
        else {
            if (customValidate != null && customValidate.trim().length() > 0) {
                String customValidateMsg = customValidate(customValidate, obj);
                if (!customValidateMsg.trim().equalsIgnoreCase("true")) {
                    logger.error(obj + "不符合用户自定义法方法:" + customValidate + "," + customValidateMsg);
                    return obj + "不符合用户自定义法方法:" + customValidate + "," + customValidateMsg;
                }
            }
            return "true";
        }
    }
}
