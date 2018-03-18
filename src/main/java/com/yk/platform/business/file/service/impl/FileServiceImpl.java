package com.yk.platform.business.file.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.yk.platform.business.constant.service.ConstantService;
import com.yk.platform.business.file.bean.FrontFile;
import com.yk.platform.business.file.bean.UploadInfo;
import com.yk.platform.business.file.dao.FileDao;
import com.yk.platform.business.file.entity.SysFile;
import com.yk.platform.business.file.service.FileService;
import com.yk.platform.common.AbstractService;
import com.yk.platform.common.IBaseDao;
import com.yk.platform.common.annotation.LogType;
import com.yk.platform.common.annotation.OperateLogType;
import com.yk.platform.common.async.AsyncTask;
import com.yk.platform.common.async.FileUploadBackGroudTask;
import com.yk.platform.common.enums.LogTypeEnum;
import com.yk.platform.common.enums.OperateLogTypeEnum;
import com.yk.platform.common.enums.StatusEnum;
import com.yk.platform.common.util.MD5Util;
import com.yk.platform.common.util.ResponseUtils;
import com.yk.platform.common.util.SpringUtils;
import com.yk.platform.common.util.file.Cloud;
import com.yk.platform.exception.BusinessException;

@Service("fileService")
@LogType(logType = LogTypeEnum.SYSFILE)
public class FileServiceImpl extends AbstractService<SysFile>implements FileService {
    private static final Logger logger = Logger.getLogger(FileServiceImpl.class);

    @Autowired
    private FileDao fileDao;
    @Autowired
    private AsyncTask asyncTask;
    @Autowired
    private ConstantService constantService;

    /**
     * 保存文件操作 文件的命名是MD5文件的结果加上文件存储的空间再MD5一次，然后加上文件的后缀构成最终的文件名，当然这个是要从七牛下载需要的key，
     * 实际的文件名还是文件原先的
     */
    @Transactional
    public List<FrontFile> upload(MultipartHttpServletRequest request) throws Exception {
        // 文件先保存到临时目录
        String directorypath = Cloud.LOCAL_TMP_FILE_PATH;
        Map<String, List<MultipartFile>> map = request.getMultiFileMap();
        // 保存到本地后返回给客户端的信息
        List<FrontFile> frontFiles = new ArrayList<>();
        // 获取存储类型，是存到私有空间，还是公共空间
        String storageType = request.getParameter("storageType");
        for (Entry<String, List<MultipartFile>> entry : map.entrySet()) {
            for (MultipartFile multipartFile : entry.getValue()) {
                try {
                    frontFiles.add(saveFile(directorypath, storageType, multipartFile, request));
                }
                catch (Exception e) {
                    logger.error(e);
                    throw new BusinessException("文件上传失败");
                }
                finally {

                }
            }
        }
        return frontFiles;

    }

    /**
     * 保存文件
     * 
     * @param directorypath
     * @param storageType
     * @param multipartFile
     * @return
     * @throws IOException
     * @throws Exception
     */
    private FrontFile saveFile(String directorypath, String storageType, MultipartFile multipartFile, MultipartHttpServletRequest request)
            throws IOException, Exception {
        String suffix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".")); // 后缀
        String format = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1);// 格式
        // 给文件一个虚拟的名字，确保文件的唯一性，防止不同用户上传同一个名称的文件产生的覆盖
        String virtualName = MD5Util.getMD5String(multipartFile.getOriginalFilename() + Math.random()) + "tmp" + suffix;
        File file = new File(directorypath + File.separator + virtualName);
        multipartFile.transferTo(file);
        String md5 = MD5Util.getFileMD5StringByIo(file);
        FrontFile frontfile = new FrontFile(constantService.getConstantValue(ConstantService.PUBLIC_DOWNLOAD_URL) + md5 + suffix,
                multipartFile.getOriginalFilename(), format, file.length() / 1.0);
        SysFile oldSysFile = fileDao.get(md5 + suffix);
        if (oldSysFile != null) {// 文件已存在
            oldFile(directorypath, storageType, suffix, file, md5, oldSysFile);
        }
        else {// 文件不存在
            newFile(directorypath, storageType, request, suffix, virtualName, md5);
        }
        return frontfile;
    }

    /**
     * 重新上传的文件
     * 
     * @param directorypath
     * @param storageType
     * @param suffix
     * @param file
     * @param md5
     * @param oldSysFile
     */
    private void oldFile(String directorypath, String storageType, String suffix, File file, String md5, SysFile oldSysFile) {
        // 重新加入上传队列
        if (!StatusEnum.UPLOAD.getStatus().equals(oldSysFile.getStatus())) {
            UploadInfo uploadInfo = new UploadInfo(directorypath + File.separator + md5 + suffix, md5 + suffix, storageType);
            asyncTask.add(new FileUploadBackGroudTask(uploadInfo));
        }
        file.delete();
    }

    /**
     * 新上传文件
     * 
     * @param directorypath
     * @param storageType
     * @param request
     * @param suffix
     * @param virtualName
     * @param md5
     */
    private void newFile(String directorypath, String storageType, MultipartHttpServletRequest request, String suffix, String virtualName,
            String md5) {
        SysFile sysFile = new SysFile(md5 + suffix, storageType, Cloud.UPLOAD_FILE_STATUS_ING);
        sysFile.setFailTimes(0);
        sysFile.setCreateTime(new Date());
        sysFile.setCreateBy(ResponseUtils.getUserId());
        // 如果直接调用add方法，调用的是未经过代理的方法，所以这里先取service然后再进行调用，这样才能够进行日志记录
        ((FileService) SpringUtils.getBean("fileService")).add(sysFile);
        renameFile(directorypath, virtualName, md5 + suffix);
        // 如果文件不存在，那么将文件加入到上传到七牛的队列
        UploadInfo uploadInfo = new UploadInfo(directorypath + File.separator + md5 + suffix, md5 + suffix, storageType);
        asyncTask.add(new FileUploadBackGroudTask(uploadInfo));
    }

    public void renameFile(String path, String oldname, String newname) {
        if (!oldname.equals(newname)) {// 新的文件名和以前文件名不同时,才有必要进行重命名
            File oldfile = new File(path + File.separator + oldname);
            File newfile = new File(path + File.separator + newname);
            if (!oldfile.exists()) {
                return;// 重命名文件不存在
            }
            boolean flag = oldfile.renameTo(newfile);
            if (!flag) {
                throw new BusinessException("文件重命名失败");
            }
        }
    }

    public List<SysFile> getFailList() {
        return fileDao.getFailList(Cloud.UPLOAD_FILE_STATUS_FAIL);
    }

    @OperateLogType(operateLoLogType = OperateLogTypeEnum.ADD, customDescription = "上传文件")
    @Override
    public void add(SysFile t) {
        super.add(t);
    }

    @Override
    public IBaseDao<SysFile> getBaseDao() {
        return fileDao;
    }
}
