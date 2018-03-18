package com.yk.platform.common.util;

/**
 * Filename: MD5.java Description: Copyright: Copyright (c)2011 Company: bbk
 * @author: guosheng.zhu
 * @version: 1.0 Create at: Apr 26, 2011 4:54:15 PM Modification History: Date
 *           Author Version Description
 *           ------------------------------------------------------------------
 *           Apr 26, 2011 guosheng.zhu 1.0 1.0 Version
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yk.platform.exception.BusinessException;

/**
 * @ClassName: MD5
 * @Description: MD5加密函数
 * @author guosheng.zhu
 * @date Apr 26, 2011 4:54:15 PM
 */
public class MD5Util {
    protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    protected static MessageDigest messagedigest = null;
    private static Logger logger = LoggerFactory.getLogger(MD5Util.class);

    public final static String MD5(String s) {
        byte[] btInput = s.getBytes(Charset.forName("utf-8"));
        return MD5(btInput);
    }

    public final static String MD5(byte[] btInput) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        try {
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        }
        catch (Exception e) {
            logger.error(e.toString());
            return null;
        }
    }

    public static String md5s(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            // System.out.println("result: " + buf.toString());// 32位的加密
            // System.out.println("result: " + buf.toString().substring(8,
            // 24));// 16位的加密
            return buf.toString();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    static {
        try {
            messagedigest = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException e) {
            logger.error("MD5FileUtil messagedigest初始化失败", e);
        }
    }

    /**
     * 另一个md5文件的方法使用的是MappedByteBuffer，但这个关闭的时候是不确定的，如果加密后仍需对文件进行操作神马的，是存在问题的
     * @param file
     * @return
     * @throws Exception
     */
    public static String getFileMD5StringByIo(File file) throws Exception {
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);

            byte[] buffer = new byte[2048];

            int length = -1;

            while ((length = in.read(buffer)) != -1) {

                messagedigest.update(buffer, 0, length);

            }

            byte[] b = messagedigest.digest();
            return bufferToHex(b);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("文件加密失败");
        }
        finally {
            try {
                in.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String getFileMD5String(File file) throws IOException {
        if (!file.exists()) {
            throw new IOException("文件" + file.getName() + " 不存在。");
        }
        FileInputStream in = new FileInputStream(file);
        FileChannel channel = in.getChannel();
        MappedByteBuffer byteBuffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
        messagedigest.update(byteBuffer);
        IOUtil.closeQuietly(channel, in);
        return bufferToHex(messagedigest.digest());
    }

    public static String getMD5String(String s) {
        return getMD5String(s.getBytes());
    }

    public static String getMD5String(byte[] bytes) {
        messagedigest.update(bytes);
        return bufferToHex(messagedigest.digest());
    }

    private static String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char c0 = hexDigits[(bt & 0xf0) >> 4];
        char c1 = hexDigits[bt & 0xf];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }
}
