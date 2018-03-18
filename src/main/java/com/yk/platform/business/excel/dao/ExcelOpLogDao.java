package com.yk.platform.business.excel.dao;

import com.yk.platform.business.excel.entity.ExcelOpLog;

/**
 * Excel操作日志数据库接口
 * @author yangkai
 * @date 2015-11-30
 * @version 1.0.0
 */
public interface ExcelOpLogDao {

    /**
     * 插入Excel操作日志
     * @param oplog
     * @return
     */
    public int insert(ExcelOpLog oplog);
}
