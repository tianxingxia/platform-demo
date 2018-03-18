package com.yk.platform.business.excel.service;

import java.util.List;

import com.yk.platform.business.excel.entity.ExcelOpLogDetail;
import com.yk.platform.business.excel.qo.ExcelOpLogDetailQo;
import com.yk.platform.common.IBaseService;
import com.yk.platform.common.PageResult;

/**
 * 错误详情日志业务接口
 * @author Administrator
 *
 */
public interface IExcelOpLogDetailService extends IBaseService<ExcelOpLogDetail> {

    /**
     * 批量插入错误详情日志
     * @param excelOpLogDetails
     * @return
     */
    int insertExcelOLDBatch(List<ExcelOpLogDetail> excelOpLogDetails);

    /**
     * @description 分页查询excel导入日志详情
     * @author 阳凯
     * @date 2017年12月5日 下午3:15:35
     * @param excelOpLogDetailQo
     * @return
     */
    PageResult getExcelOpLogDetailsByPage(ExcelOpLogDetailQo excelOpLogDetailQo);
}
