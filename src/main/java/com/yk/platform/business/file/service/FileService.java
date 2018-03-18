package com.yk.platform.business.file.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.yk.platform.business.file.bean.FrontFile;
import com.yk.platform.business.file.entity.SysFile;
import com.yk.platform.common.IBaseService;

public interface FileService extends IBaseService<SysFile> {
    public List<FrontFile> upload(MultipartHttpServletRequest request) throws Exception;

    public List<SysFile> getFailList();
}
