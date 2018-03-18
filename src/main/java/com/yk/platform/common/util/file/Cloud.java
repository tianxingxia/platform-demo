package com.yk.platform.common.util.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.qiniu.common.QiniuException;
import com.yk.platform.common.util.ConstantsUtil;
import com.yk.platform.common.util.SpringUtils;

public class Cloud {
    /**
     * 私有文件类型，存储到私有空间
     */
    public static final String FILE_TYPE_PRIVATE = "private";
    /**
     * 公有的文件类型，存储到公有空间
     */
    public static final String FILE_TYPE_PUBLIC = "public";
    /**
     * 文件保存本地的临时路径
     */
    public static final String LOCAL_TMP_FILE_PATH = ConstantsUtil.getConstant("local.tmp.file.path");

    /**
     * 文件上传到七牛失败
     */
    public static final Integer UPLOAD_FILE_STATUS_FAIL = 3;
    /**
     * 文件正在上传到七牛
     */
    public static final Integer UPLOAD_FILE_STATUS_ING = 1;
    /**
     * 文件已上传到七牛
     */
    public static final Integer UPLOAD_FILE_STATUS_SUCCESS = 2;
    /**
     * 所有的云公有下载地址
     */
    public static List<String> publicDownUrl = new ArrayList<>();

    static {
        publicDownUrl.add(QiNiuCloud.QINIU_PUBLICT_DOWNLOAD);
    }

    private static final Logger logger = Logger.getLogger(Cloud.class);

    public static void upload(String filePath, String fileName, String type) throws Exception {
        qiniuUpload(filePath, fileName, type);
    }

    private static void qiniuUpload(String filePath, String fileName, String type) throws Exception, QiniuException {
        logger.info("上传到七牛");
        QiNiuCloud qiNiuCloud = (QiNiuCloud) SpringUtils.getBean("qiNiuCloud");
        try {
            qiNiuCloud.uploadCover(filePath, fileName, type);
        }
        catch (QiniuException e) {
            // 如果是文件未找到，由于只有七牛上传成功本地才会执行删除操作，故这里可以认为是并发执行造成的，也就是这个文件已经被一个线程成功上传到七牛并把文件删除，导致这个线程失败，故这个应该忽略，认定文件已上传成功
            if ((e.getCause() instanceof FileNotFoundException)) {
                logger.error("找不到文件，可能已成功上传，也可能人为删除，或被黑了删除了");
                return;
            }
            logger.error(e);
            throw e;
        }
        logger.info("上传到七牛成功");
    }

    /**
     * 从七牛上下载apk
     * 
     * @param qiNiuCloud
     * @param oldFilePath
     * @param fileName
     * @throws Exception
     */
    public static void download(String oldFilePath, String fileName) throws Exception {
        qiniuDownload(oldFilePath, fileName);
    }

    private static void qiniuDownload(String oldFilePath, String fileName) throws Exception {
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        OutputStream outputStream = new FileOutputStream(new File(oldFilePath));
        URL url = null;
        url = new URL(QiNiuCloud.QINIU_PUBLICT_DOWNLOAD + fileName);
        conn = (HttpURLConnection) url.openConnection();
        inputStream = conn.getInputStream();
        int i = 0;
        byte b[] = new byte[1024];
        while ((i = inputStream.read(b)) != -1)
            outputStream.write(b, 0, i);
        conn.disconnect();
        inputStream.close();
        outputStream.flush();
        outputStream.close();
    }

}
