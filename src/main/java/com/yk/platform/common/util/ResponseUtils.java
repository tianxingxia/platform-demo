package com.yk.platform.common.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.HtmlUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.yk.platform.common.Page;

/**
 * 请求响应输出工具类
 * 
 * @author liupengfei
 */
public class ResponseUtils {
    private static final Logger logger = Logger.getLogger(ResponseUtils.class);

    /**
     * 将字符串信息输出，格式为utf-8
     * 
     * @param response
     * @param string
     */
    public static void setStringResponse(HttpServletResponse response, String string) {
        try {
            response.setContentType("application/json");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.write(HtmlUtils.htmlUnescape(string));
            out.flush();
            out.close();
        }
        catch (IOException e) {
            logger.error(e);
        }
    }

    /**
     * 将对象结果输出
     * 
     * @param response
     * @param object
     */
    public static void setObjectResponse(HttpServletResponse response, Object resultBean) {
        setStringResponse(response, getJsonString(resultBean));
    }

    /**
     * 获取对象的json串
     * 
     * @param object
     * @return
     */
    public static String getJsonString(Object object) {

        String str = JSON.toJSONString(object, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteMapNullValue, // 空的字段写出去
                SerializerFeature.WriteDateUseDateFormat);
        return str;
    }

    public static Page parsePage(HttpServletRequest request) {
        return new Page();
    }

    /**
     * 获取当前登录用户的id
     * 
     * @return
     */
    public static String getUserId() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        AttributePrincipal principal = (AttributePrincipal) request.getUserPrincipal();

        if (principal == null) {
            // throw new BusinessException("获取用户信息失败");
            return "yk";
        }
        return principal.getName();
    }

    /**
     * 获取当前登录用户的信息
     * 
     * @return
     */
    public static Map<String, Object> getUserInfo() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        AttributePrincipal principal = (AttributePrincipal) request.getUserPrincipal();
        if (principal == null) {
            // throw new BusinessException("获取用户信息失败");
            Map<String, Object> temp = new HashMap<>();
            return temp;
        }
        Map<String, Object> userInfo = principal.getAttributes(); // 获取用户其它信息
        return userInfo;
    }

    public static String formatDate(Date date) {
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return time.format(date);
    }

}
