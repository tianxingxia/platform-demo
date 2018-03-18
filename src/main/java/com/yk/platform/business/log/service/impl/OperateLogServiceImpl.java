package com.yk.platform.business.log.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yk.platform.business.log.dao.OperateLogDao;
import com.yk.platform.business.log.entity.OperateLog;
import com.yk.platform.business.log.qo.OperateLogQo;
import com.yk.platform.business.log.service.OperateLogService;
import com.yk.platform.common.AbstractService;
import com.yk.platform.common.IBaseDao;
import com.yk.platform.common.PageResult;
import com.yk.platform.common.enums.LogTypeEnum;
import com.yk.platform.common.enums.OperateLogTypeEnum;

@Service
public class OperateLogServiceImpl extends AbstractService<OperateLog>implements OperateLogService {

    @Autowired
    private OperateLogDao operateLogDao;

    @Override
    public IBaseDao<OperateLog> getBaseDao() {
        return operateLogDao;
    }

    @Override
    public PageResult getOperateLogsByPage(OperateLogQo operateLogQo) {
        List<OperateLog> list = operateLogDao.getList(operateLogQo);
        long totalCount = operateLogDao.getTotal(operateLogQo);
        transferField(list);

        PageResult pageResult = new PageResult();
        pageResult.setRecordList(list);
        pageResult.setTotalCount(totalCount);
        return pageResult;
    }

    private void transferField(List<OperateLog> operateLogs) {
        if (CollectionUtils.isNotEmpty(operateLogs)) {
            for (OperateLog operateLog : operateLogs) {
                operateLog.setLogTypeShow(LogTypeEnum.transferLogType(operateLog.getLogType()));
                operateLog.setOperateTypeShow(OperateLogTypeEnum.transferOperateType(operateLog.getOperateType()));
            }
        }
    }
}
