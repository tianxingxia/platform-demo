package com.yk.platform.common.async;

import java.io.File;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import com.yk.platform.business.file.bean.UploadInfo;
import com.yk.platform.business.file.entity.SysFile;
import com.yk.platform.business.file.service.FileService;
import com.yk.platform.common.util.SpringUtils;
import com.yk.platform.common.util.file.Cloud;

/**
 * @author liupengfei 文件上传到七牛的异步任务线程
 */
public class FileUploadBackGroudTask extends BackGroudTask implements Callable<String> {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(FileUploadBackGroudTask.class);

    private UploadInfo uploadInfo;

    public UploadInfo getUploadInfo() {
        return uploadInfo;
    }

    public void setUploadInfo(UploadInfo uploadInfo) {
        this.uploadInfo = uploadInfo;
    }

    @Override
    public void doinworkThread() throws Exception {
        Cloud.upload(uploadInfo.getFilePath(), uploadInfo.getFileName(), uploadInfo.getType());
        updateFileStatus(Cloud.UPLOAD_FILE_STATUS_SUCCESS);
        deleteFile(new File(uploadInfo.getFilePath()));
    }

    /**
     * 失败或成功之后更新文件的状态
     * 
     * @param status
     */
    private void updateFileStatus(Integer status) {
        logger.info("更新文件状态" + status);
        FileService fileService = (FileService) SpringUtils.getBean("fileService");
        SysFile sysFile = fileService.get(uploadInfo.getFileName());
        sysFile.setStatus(status);
        if (Cloud.UPLOAD_FILE_STATUS_FAIL.equals(status))
            sysFile.setFailTimes(sysFile.getFailTimes() + 1);
        fileService.update(sysFile);
    }

    public FileUploadBackGroudTask() {
    }

    public FileUploadBackGroudTask(UploadInfo uploadInfo) {
        super();
        this.uploadInfo = uploadInfo;
    }

    @Override
    public void doFail() {
        super.doFail();
        logger.error("上传到七牛失败");
        updateFileStatus(Cloud.UPLOAD_FILE_STATUS_FAIL);
    }

    /**
     * 删除文件，所有文件的删除对不存在的异常均忽略
     * 
     * @param file
     */
    private void deleteFile(File file) {
        try {
            file.delete();
        }
        catch (Exception e) {
            logger.error(e);
        }
    }

}
