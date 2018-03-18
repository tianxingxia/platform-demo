package com.yk.platform.common.util;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yk.platform.exception.BusinessException;

/**
 * http请求工具类 适用于约定好的数据返回格式
 * @author liupengfei
 */
public class HttpUtil {
    private static final Logger logger = Logger.getLogger(HttpUtil.class);
    private static final String CODESTRING = "code";
    public static final String SUCCESS_CODE_200 = "200";
    public static final String SUCCESS_CODE_100001 = "000001";
    private static final Integer READTIMEOUT = 10000;
    private static final Integer CONNECTTIMETOUT = 10000;
    /**
     * 商城的秘钥，用于认证请求是否来自商城，泄漏了就改掉
     */
    private static final String encryptionkey = "watchstorekey#@$%@$";

    /**
     * post请求
     * @param url
     * @param param
     * @return
     * @throws Exception
     */
    public static JSONObject post(String url, String param, String successCode) throws Exception {
        URL postUrl = null;
        HttpURLConnection connection = null;
        DataOutputStream out = null;
        InputStream inputStream = null;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            postUrl = new URL(url);
            connection = (HttpURLConnection) postUrl.openConnection();
            // 设置是否向connection输出，因为这个是post请求，参数要放在 http正文内，因此需要设为true
            connection.setConnectTimeout(CONNECTTIMETOUT);
            connection.setReadTimeout(READTIMEOUT);
            connection.setDoOutput(true);
            // Read from the connection. Default is true.
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            // Post 请求不能使用缓存
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type", "application/json");
            String watchstorekey = MD5Util.MD5(param.getBytes("utf-8"));
            connection.setRequestProperty("watchstorevalue", MD5Util.MD5(encryptionkey + watchstorekey));
            connection.connect();
            out = new DataOutputStream(connection.getOutputStream());
            out.write(param.getBytes("utf-8"));
            inputStream = connection.getInputStream();

            byte b[] = new byte[1024];
            int len;
            while ((len = inputStream.read(b)) != -1) {
                stringBuffer.append(new String(b, 0, len, "utf-8"));
            }
            JSONObject jsonObject = JSON.parseObject(stringBuffer.toString());
            if (!successCode.equals(jsonObject.getString(CODESTRING))) {
                throw new BusinessException("http post请求失败，返回状态吗：" + jsonObject.getString(CODESTRING));
            }
            return jsonObject;
        }
        catch (Exception e) {
            logger.error(e);
            logger.error(stringBuffer.toString());
            throw e;
        }
        finally {
            if (out != null) {
                out.flush();
                out.close();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                }
                catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (connection != null) {
                connection.disconnect();
            }

        }
    }

    /**
     * 带重试次数的post
     * @param url
     * @param param
     * @param successCode
     * @param count
     * @return
     */
    public static JSONObject post(String url, String param, String successCode, int count) throws Exception {
        try {

            return post(url, param, successCode);

        }
        catch (Exception e) {
            if (count > 0) {
                count--;
                return post(url, param, successCode, count);
            }
            else {
                throw e;
            }
        }

    }

    /**
     * get请求
     * @param url
     * @return
     * @throws Exception
     */
    public static JSONObject get(String url, String successCode) throws Exception {
        URL getUrl = null;
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        try {
            getUrl = new URL(url);
            connection = (HttpURLConnection) getUrl.openConnection();
            connection.setConnectTimeout(CONNECTTIMETOUT);
            connection.setReadTimeout(READTIMEOUT);
            inputStream = connection.getInputStream();
            StringBuffer stringBuffer = new StringBuffer();
            byte b[] = new byte[1024];
            int len;
            while ((len = inputStream.read(b)) != -1) {
                stringBuffer.append(new String(b, 0, len, "utf-8"));
            }
            JSONObject jsonObject = JSON.parseObject(stringBuffer.toString());
            if (!successCode.equals(jsonObject.getString("code"))) {
                throw new BusinessException("http get请求失败，返回状态吗：" + jsonObject.getString("code"));
            }
            return jsonObject;
        }
        catch (Exception e) {
            logger.error(e);
            throw e;
        }
        finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                }
                catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

}
