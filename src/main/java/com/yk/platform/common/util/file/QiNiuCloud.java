package com.yk.platform.common.util.file;

import java.io.IOException;

import org.springframework.stereotype.Repository;

import com.qiniu.common.QiniuException;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.yk.platform.common.util.AESUtil;
import com.yk.platform.common.util.ConstantsUtil;

@Repository
public class QiNiuCloud {

    /**
     * 七牛访问密钥
     */
    private static final String QINIU_ACCESS_KEY = AESUtil.decryptAES(ConstantsUtil.getConstant("qiniu.ACCESS_KEY"), ConstantsUtil.getConstant("PRIVATE_KEY"));

    /**
     * 七牛安全密钥
     */
    private static final String QINIU_SECRET_KEY = AESUtil.decryptAES(ConstantsUtil.getConstant("qiniu.SECRET_KEY"),ConstantsUtil.getConstant("PRIVATE_KEY"));

    /**
     * 公有空间
     */
    public static final String PUBLICSMARTWATCH = ConstantsUtil.getConstant("your public space");
    /**
     * 私有空间
     */
    public static final String PRIVATESMARTWATCH = ConstantsUtil.getConstant("your private space");

    /**
     * 七牛云文件私有空间下载地址
     */
    private static final String QINIU_DOWNLOAD = ConstantsUtil.getConstant("qiniu.download");

    /**
     * 七牛云文件公有空间下载地址
     */
    public static final String QINIU_PUBLICT_DOWNLOAD = ConstantsUtil.getConstant("qiniu.public.download");
    // 密钥配置
    Auth auth = Auth.create(QINIU_ACCESS_KEY, QINIU_SECRET_KEY);
    // 创建上传对象
    UploadManager uploadManager = new UploadManager();

    // 简单上传，使用默认策略，只需要设置上传的空间名就可以了
    public String getUpToken(String bucketname) {
        return auth.uploadToken(bucketname);
    }

    /**
     * @param filePath文件路径
     * @param fileName文件名
     * @param type
     *            上传到的空间类型，是私有还是公共
     * @throws QiniuException
     * @throws IOException
     */
    public void uploadCover(String filePath, String fileName, String type) throws QiniuException {
        // 调用put方法上传，这里指定的key和上传策略中的key要一致
        uploadManager.put(filePath, fileName, getUpToken(getType(type)));
    }

    /**
     * 根据文件类型获取空间类型
     * 
     * @param type
     * @return
     */
    private String getType(String type) {
        if (type.equals(Cloud.FILE_TYPE_PRIVATE)) {
            return PRIVATESMARTWATCH;
        }
        else {
            return PUBLICSMARTWATCH;
        }
    }

    public String download(String fileName) {
        // 调用privateDownloadUrl方法生成下载链接,第二个参数可以设置Token的过期时间
        String downloadRUL = auth.privateDownloadUrl(QINIU_DOWNLOAD + fileName);
        return downloadRUL;
    }

}
