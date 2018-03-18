package com.yk.platform.business.file.dao;

import java.util.List;

import com.yk.platform.business.file.entity.SysFile;
import com.yk.platform.common.IBaseDao;

public interface FileDao extends IBaseDao<SysFile> {
    public List<SysFile> getFailList(Integer status);
}
