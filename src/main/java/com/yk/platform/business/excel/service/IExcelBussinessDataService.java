package com.yk.platform.business.excel.service;

import java.util.List;
import java.util.Map;

import com.yk.platform.common.ResultBean;

/**
 * Excel业务逻辑接口
 * @author yangkai
 * @date 2014年5月27日
 * @version 1.0.0
 */
public interface IExcelBussinessDataService {

    /**
     * 根据模板id获得要导出的数据 方法用途: <br>
     * 实现步骤: <br>
     * @param tempId
     * @param map
     * @return
     */
    public List<?> getExortDataByPara(String tempId, Map<String, String> map);

    public int getExortCountByPara(String tempId, Map<String, String> map);

    /**
     * 根据模板id选择方法插入数据 方法用途: <br>
     * 实现步骤: <br>
     * @param tempId
     * @param list
     * @return
     */
    public ResultBean insertExcelData(String tempId, Map<String, String> filedmap, List<?> list);

    /**
     * 获得生成pdf的freemarker填充参数 @param tempId @param filedmap @return @throws
     */
    public Map<String, Object> getPdfFreemarkerDatas(String tempId, Map<String, String> filedmap);

    /**
     * 获得生成复杂excel文件的填充参数 @param tempName @param filedmap @return @throws
     */
    public Map<String, Object> getComplexExcelDatas(String tempName, Map<String, String> filedmap);
}
