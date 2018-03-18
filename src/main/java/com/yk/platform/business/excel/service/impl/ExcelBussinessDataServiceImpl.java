package com.yk.platform.business.excel.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.yk.platform.business.excel.service.IExcelBussinessDataService;
import com.yk.platform.common.ResultBean;

@Service
public class ExcelBussinessDataServiceImpl implements IExcelBussinessDataService {

    private static Logger logger = Logger.getLogger(ExcelBussinessDataServiceImpl.class);

    @Override
    public Map<String, Object> getComplexExcelDatas(String arg0, Map<String, String> arg1) {
        return null;
    }

    @Override
    public int getExortCountByPara(String arg0, Map<String, String> arg1) {
        return 0;
    }

    /**
     * 导出
     */
    @Override
    public List<?> getExortDataByPara(String tempId, Map<String, String> map) {
        logger.error("导出类别：" + tempId + "开始");
        try {

        }
        catch (Exception e) {
            logger.error("ExcelBussinessDataServiceImpl-getExortDataByPara:", e);
        }
        return null;
    }

    @Override
    public Map<String, Object> getPdfFreemarkerDatas(String arg0, Map<String, String> arg1) {
        return null;
    }

    @Override
    public ResultBean insertExcelData(String tempId, Map<String, String> filedmap, List<?> list) {

        ResultBean result = new ResultBean();
        logger.error("导入类别：" + tempId + "，总数量：" + (list != null && list.size() > 0 ? list.size() : 0) + "");
        return result;
    }

}
