package com.yk.platform.business.excel.service;

import com.yk.platform.business.excel.entity.ExcelOpLog;

/**
 * Excel日志操作接口
 * @author yangkai
 * @date 2015-11-30
 * @version 1.0.0
 */
public interface IExcelOpLogService {
    /**
     * 插入Excel操作日志
     * @param oplog
     * @return
     */
    public int insert(ExcelOpLog oplog);
}
