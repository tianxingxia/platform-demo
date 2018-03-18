package com.yk.platform.business.excel.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yk.platform.business.excel.dao.ExcelOpLogDao;
import com.yk.platform.business.excel.entity.ExcelOpLog;
import com.yk.platform.business.excel.service.IExcelOpLogService;

@Service
public class ExcelOpLogServiceImpl implements IExcelOpLogService {

    @Autowired
    @Qualifier("excelOpLogDao")
    private ExcelOpLogDao excelOpLogDao;

    @Override
    public int insert(ExcelOpLog oplog) {
        return excelOpLogDao.insert(oplog);
    }

}
