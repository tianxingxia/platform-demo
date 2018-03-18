package com.yk.platform.common.util.file;

import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;

import com.yk.platform.common.util.ConstantsUtil;

public class WsCloud {
    /**
     * 网宿云文件下载地址
     */
    private static final Logger logger = Logger.getLogger(WsCloud.class);
    public static final String WS_PUBLICT_DOWNLOAD = ConstantsUtil.getConstant("ws.public.download");

    public static void upload(String fileName) throws Exception {
        URL url = null;
        HttpURLConnection connection = null;
        try {
            url = new URL(WsCloud.WS_PUBLICT_DOWNLOAD + fileName);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            connection.getResponseCode();
        }
        catch (Exception e) {
            logger.error("上传到网宿失败");
            throw e;
        }
        finally {
            if (connection != null)
                connection.disconnect();
        }

    }
}
