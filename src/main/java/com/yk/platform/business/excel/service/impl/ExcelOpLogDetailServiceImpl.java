package com.yk.platform.business.excel.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yk.platform.business.excel.dao.ExcelOpLogDetailDao;
import com.yk.platform.business.excel.entity.ExcelOpLogDetail;
import com.yk.platform.business.excel.qo.ExcelOpLogDetailQo;
import com.yk.platform.business.excel.service.IExcelOpLogDetailService;
import com.yk.platform.common.AbstractService;
import com.yk.platform.common.IBaseDao;
import com.yk.platform.common.PageResult;

@Service
public class ExcelOpLogDetailServiceImpl extends AbstractService<ExcelOpLogDetail>implements IExcelOpLogDetailService {

    @Autowired
    @Qualifier("excelOpLogDetailDao")
    private ExcelOpLogDetailDao excelOpLogDetailDao;

    @Override
    public int insertExcelOLDBatch(List<ExcelOpLogDetail> excelOpLogDetails) {
        return excelOpLogDetailDao.insertExcelOLDBatch(excelOpLogDetails);
    }

    @Override
    public IBaseDao<ExcelOpLogDetail> getBaseDao() {
        return excelOpLogDetailDao;
    }

    @Override
    public PageResult getExcelOpLogDetailsByPage(ExcelOpLogDetailQo excelOpLogDetailQo) {
        List<ExcelOpLogDetail> list = excelOpLogDetailDao.getList(excelOpLogDetailQo);
        long totalCount = excelOpLogDetailDao.getTotal(excelOpLogDetailQo);
        PageResult pageResult = new PageResult();
        pageResult.setRecordList(list);
        pageResult.setTotalCount(totalCount);
        return pageResult;
    }

}
