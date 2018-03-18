package com.yk.platform.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.GZIPOutputStream;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.yk.platform.common.BaseRequestParam;

import net.sf.json.JSONObject;

public class HttpRequestUtil {

    public static final Logger logger = Logger.getLogger(HttpRequestUtil.class);

    public static final String PRIVATE_KEY_MOBILE = "EebbkKf1Watch";
    public static final String PRIVATE_KEY_MOBILE_EXPERIENCE = "EebbkKf1MobileExperience";
    // public static final String URL = "http://y1.eebbk.net/smartwatch";
    // public static final String URL = "http://y2.eebbk.net/smartwatch";
    // public static final String URL = "http://127.0.0.1:8081/smartwatch";
    public static final String URL = "http://watch.module.okii.com/smartwatch";
    // public static final String URL = "http://watch.okii.com/smartwatch";
    // public static final String URL = "http://y1test.eebbk.net/smartwatch";

    public static final String COMPRESS_TYPE_GZIP = "gzip"; // body压缩方式

    public static String ticket = "ED70D7D09BCE644EEEFDA82012AFC702";
    public static String mac = "CE084690-2156-4DE2-B02F-D3895E9CAB0C";
    public static String machineId = "CE08469021564DE2B02FD3895E9CAB0C";

    public static String ENCRPTY_KEY = "#!@#$ASDFADFAS##123411309#!@#$!!@#$!@3"; // 用来加密的key

    /**
     * 获取正式账户基本请求参数
     * 
     * @return
     */
    private static String getBaseRequestParam() {

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = sdf.format(date);

        // System.out.println(timestamp);

        BaseRequestParam baseRequestParam = new BaseRequestParam();
        baseRequestParam.setAppId("2");
        baseRequestParam.setMac(mac);
        baseRequestParam.setMachineId(machineId);
        baseRequestParam.setToken(ticket);
        baseRequestParam.setTimestamp(timestamp);

        // 设置一个标记
        baseRequestParam.setDeviceId(getEncrptyCode());

        Gson gson = new Gson();
        String baseStr = gson.toJson(baseRequestParam);

        return baseStr;
    }

    /**
     * @description 获取秘钥，算法：将mac、machineId、ticket结合加密key，通过AES对称加密算法进行加密，再转换成MD5值
     * @author 阳凯
     * @date 2017年2月9日 上午9:17:15
     * @return
     */
    private static String getEncrptyCode() {
        String src = mac + machineId + ticket + ENCRPTY_KEY;
        String middle = AesUtils.encrypt(src);
        String result = MD5Util.MD5(middle);
        return result;
    }

    /**
     * 打印http头信息
     * 
     * @param headers
     */
    private static void printHttpHeaders(Header[] headers) {
        System.out.println("");
        System.out.println("<------------header----------->");
        for (int i = 0; i < headers.length; i++) {
            System.out.println(headers[i].getName() + ":" + headers[i].getValue());
        }
    }

    /**
     * 获取非对称加密公钥
     * 
     * @return
     */
    private static String getPublicKey() {
        String result = doGetPublicKey(URL + "/rsa", PRIVATE_KEY_MOBILE);
        JSONObject jsonObject = JSONObject.fromObject(result);
        jsonObject = jsonObject.getJSONObject("data");
        String publicKey = (String) jsonObject.get("publicKey");

        return publicKey;
    }

    /**
     * 获取请求
     * 
     * @param url
     * @param key
     */
    public static String doGetPublicKey(String url, String key) {
        String baseStr = getBaseRequestParam();

        HttpClient hc = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(url);
        get.addHeader("Base-Request-Param", baseStr);

        HttpResponse response = null;
        try {
            response = hc.execute(get);
            response.getStatusLine().getStatusCode();
            String responseBody = EntityUtils.toString(response.getEntity());

            // System.out.println("code = " +
            // response.getStatusLine().getStatusCode());
            // System.out.println(responseBody);
            // printHttpHeaders(response.getAllHeaders());

            return responseBody;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 将字符串进行gzip压缩
     * 
     * @param str
     * @param encoding
     * @return
     */
    public static byte[] compressToByte(String str, String encoding) {
        if (str == null || str.length() == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(str.getBytes(encoding));
            gzip.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    /**
     * 对请求数据进行签名
     * 
     * @param url
     * @param baseStr
     * @param body
     * @param signKey
     * @return
     */
    private static String sign(String url, String baseStr, byte[] body, String signKey) {
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();

        byte[] urlBytes = url.getBytes(Charset.forName("utf-8"));
        outSteam.write(urlBytes, 0, urlBytes.length);

        byte[] baseBytes = baseStr.getBytes(Charset.forName("utf-8"));
        outSteam.write(baseBytes, 0, baseBytes.length);

        if (body != null) {
            outSteam.write(body, 0, body.length);
        }

        byte[] signKeyBytes = signKey.getBytes(Charset.forName("utf-8"));
        outSteam.write(signKeyBytes, 0, signKeyBytes.length);

        try {
            outSteam.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        byte[] signData = outSteam.toByteArray();

        return MD5Util.MD5(signData);
    }

    /**
     * 创建请求
     * 
     * @param url
     * @param body
     */
    public static String doPost(String url, String body, String compressType) {
        String baseStr = getBaseRequestParam();

        String publicKey = getPublicKey();
        String signKey = UUIDUtil.getUUID();
        String eebbkKey = null;
        try {
            eebbkKey = RSAUtil.pubEncrypt(signKey, publicKey);
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }

        HttpClient hc = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);

        byte[] bodyBytes = body.getBytes(Charset.forName("utf-8"));
        // System.out.println("uncompressBodyLen = " + bodyBytes.length);
        if ("gzip".equals(compressType)) {
            post.addHeader("Content-Encoding", "gzip");

            bodyBytes = compressToByte(body, "utf-8");
            // System.out.println("compressBodyLen = " + bodyBytes.length);
        }

        String sign = sign(url, baseStr, bodyBytes, signKey);

        post.addHeader("Base-Request-Param", baseStr);
        post.addHeader("Eebbk-Sign", sign);
        post.addHeader("Content-Type", "application/json");
        post.addHeader("Eebbk-Key", eebbkKey);
        post.addHeader("serverGrey", "619386");
        // post.addHeader("Version", "A_2.0.0");

        // System.out.println("Base-Request-Param: "+baseStr);
        // System.out.println("Eebbk-Sign: "+sign);

        // System.out.println("signKey = " + signKey + ", eebbkKey = " +
        // eebbkKey + ", sign = " + sign);

        ByteArrayEntity arrayEntity = new ByteArrayEntity(bodyBytes);
        post.setEntity(arrayEntity);

        // printHttpHeaders(post.getAllHeaders());

        HttpResponse response = null;
        try {
            response = hc.execute(post);
            response.getStatusLine().getStatusCode();
            String responseBody = EntityUtils.toString(response.getEntity());

            // System.out.println(responseBody);
            // printHttpHeaders(response.getAllHeaders());

            return responseBody;

        }
        catch (Exception e) {
            ExceptionUtil.log(e, logger);
            return null;
        }
    }

    /**
     * 修改请求
     * 
     * @param url
     * @param body
     * @param key
     */
    public static String doPut(String url, String body, String compressType) {
        String baseStr = getBaseRequestParam();

        String publicKey = getPublicKey();
        String signKey = UUIDUtil.getUUID();
        String eebbkKey = null;
        try {
            eebbkKey = RSAUtil.pubEncrypt(signKey, publicKey);
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }

        HttpClient hc = HttpClientBuilder.create().build();
        HttpPut put = new HttpPut(url);

        byte[] bodyBytes = body.getBytes(Charset.forName("utf-8"));
        // System.out.println("uncompressBodyLen = " + bodyBytes.length);
        if ("gzip".equals(compressType)) {
            put.addHeader("Content-Encoding", "gzip");

            bodyBytes = compressToByte(body, "utf-8");
            // System.out.println("compressBodyLen = " + bodyBytes.length);
        }

        String sign = sign(url, baseStr, bodyBytes, signKey);

        put.addHeader("Base-Request-Param", baseStr);
        put.addHeader("Eebbk-Sign", sign);
        put.addHeader("Content-Type", "application/json");
        put.addHeader("Eebbk-Key", eebbkKey);

        // System.out.println("Base-Request-Param: "+baseStr);
        // System.out.println("Eebbk-Sign: "+sign);

        // System.out.println("signKey = " + signKey + ", eebbkKey = " +
        // eebbkKey + ", sign = " + sign);

        ByteArrayEntity arrayEntity = new ByteArrayEntity(bodyBytes);
        put.setEntity(arrayEntity);

        HttpResponse response = null;
        try {
            response = hc.execute(put);
            response.getStatusLine().getStatusCode();
            String responseBody = EntityUtils.toString(response.getEntity());

            // System.out.println(responseBody);
            // printHttpHeaders(response.getAllHeaders());

            return responseBody;
        }
        catch (Exception e) {
            ExceptionUtil.log(e, logger);
            return null;
        }
    }

    /**
     * 获取请求
     * 
     * @param url
     * @param key
     */
    public static String doGet(String url, String key) {
        String baseStr = getBaseRequestParam();

        String publicKey = getPublicKey();
        String signKey = UUIDUtil.getUUID();
        String eebbkKey = null;
        try {
            eebbkKey = RSAUtil.pubEncrypt(signKey, publicKey);
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }

        String sign = MD5Util.MD5(url + baseStr + signKey);

        HttpClient hc = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(url);
        get.addHeader("Base-Request-Param", baseStr);
        get.addHeader("Eebbk-Sign", sign);
        get.addHeader("Eebbk-Key", eebbkKey);
        // get.addHeader("Version", "W_1.04");
        get.addHeader("Grey", "619385");

        // System.out.println("Base-Request-Param: "+baseStr);
        // System.out.println("Eebbk-Sign: "+sign);
        // System.out.println("Eebbk-Key: "+eebbkKey);
        //
        // System.out.println(baseStr);
        // System.out.println(sign);

        HttpResponse response = null;
        try {
            // System.out.println(get.getURI().toString());
            // printHttpHeaders(get.getAllHeaders());

            response = hc.execute(get);

            response.getStatusLine().getStatusCode();
            // System.out.println("code = " +
            // response.getStatusLine().getStatusCode());
            String responseBody = EntityUtils.toString(response.getEntity());

            // System.out.println(responseBody);

            return responseBody;
        }
        catch (Exception e) {
            ExceptionUtil.log(e, logger);
            return null;
        }

    }

    /**
     * 删除请求
     * 
     * @param url
     * @param key
     */
    public static String doDelete(String url, String key) {
        String baseStr = getBaseRequestParam();

        String sign = MD5Util.MD5(url + baseStr + key);

        HttpClient hc = HttpClientBuilder.create().build();
        HttpDelete get = new HttpDelete(url);
        get.addHeader("Base-Request-Param", baseStr);
        get.addHeader("Eebbk-Sign", sign);

        HttpResponse response = null;
        try {
            response = hc.execute(get);
            response.getStatusLine().getStatusCode();
            String responseBody = EntityUtils.toString(response.getEntity());

            // System.out.println(responseBody);
            // printHttpHeaders(response.getAllHeaders());

            return responseBody;
        }
        catch (Exception e) {
            ExceptionUtil.log(e, logger);
            return null;
        }

    }
}
