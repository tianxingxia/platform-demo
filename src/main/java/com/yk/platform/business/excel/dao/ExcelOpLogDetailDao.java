package com.yk.platform.business.excel.dao;

import java.util.List;

import com.yk.platform.business.excel.entity.ExcelOpLogDetail;
import com.yk.platform.common.IBaseDao;

/**
 * Excel错误详情数据库操作接口
 * @author yangkai
 *
 */
public interface ExcelOpLogDetailDao extends IBaseDao<ExcelOpLogDetail> {

    /**
     * 批量插入错误详情日志
     * @param excelOpLogDetails
     * @return
     */
    public int insertExcelOLDBatch(List<ExcelOpLogDetail> excelOpLogDetails);
}
