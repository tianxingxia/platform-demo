package com.yk.platform.business.log.service;

import com.yk.platform.business.log.entity.OperateLog;
import com.yk.platform.business.log.qo.OperateLogQo;
import com.yk.platform.common.IBaseService;
import com.yk.platform.common.PageResult;

public interface OperateLogService extends IBaseService<OperateLog> {

    /**
     * @description 分页查询excel导入日志详情
     * @author 阳凯
     * @date 2017年12月5日 下午3:15:35
     * @param excelOpLogDetailQo
     * @return
     */
    PageResult getOperateLogsByPage(OperateLogQo operateLogQo);
}
