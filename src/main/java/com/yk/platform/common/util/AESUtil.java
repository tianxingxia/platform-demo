package com.yk.platform.common.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;



/**
 * AES加密工具
 * @author qiaopengfei
 * @date 2014年12月4日
 * @version 1.0.0
 * @Copyright (c) 2014, www.xtc.com All Rights Reserved.
 *
 */
public class AESUtil {
    
    private static final Logger logger = Logger.getLogger(AESUtil.class);
    
    private static final String type = "AES";
    
    /**
     * AES加密
     * @param content
     * @param password
     * @return
     */
    public static String encryptAES(String content, String password) {
        String res = null;
        try {
            // 明文加密成字节密文
            byte[] encryptResult = encrypt(content, password);
            res = Base64.encode(encryptResult);
        } catch (Exception e) {
            logger.error("encryptAES error! ", e);
        }
        return res;
    }
    
    /**
     * AES解密
     * @param encryptResultStr
     * @param password
     * @return
     */
    public static String decryptAES(String encryptResultStr, String password) {
        String res = null;
        try {
            byte[] decryptFrom = Base64.decode(encryptResultStr);
            
            // 字节密文转明文
            byte[] decryptResult = decrypt(decryptFrom, password);
            res = new String(decryptResult);
        } catch (Exception e) {
            logger.error("decryptAES error: ", e);
        }
        return res;
    }
    
    /**
     * 加密
     * @param content
     *            需要加密的内容
     * @param password
     *            加密密码
     * @return
     */
    private static byte[] encrypt(String content, String password) throws Exception {
        SecretKeySpec key = new SecretKeySpec(password.getBytes("UTF-8"), type);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// 创建密码器
        byte[] byteContent = content.getBytes("utf-8");
        cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
        byte[] result = cipher.doFinal(byteContent);
        return result; // 加密
    }
    
    /**
     * 解密
     * @param content
     *            待解密内容
     * @param password
     *            解密密钥
     * @return
     */
    private static byte[] decrypt(byte[] content, String password) throws Exception {
        SecretKeySpec key = new SecretKeySpec(password.getBytes("UTF-8"), type);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// 创建密码器
        cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
        byte[] result = cipher.doFinal(content);
        return result; // 加密
    }
    
}
