package com.yk.platform.business.excel.qo;

import com.yk.platform.common.Qo;

public class ExcelOpLogDetailQo extends Qo {

    /**
     * 日志Id
     */
    private String logId;

    /**
     * 出错行号
     */
    private Integer rowNum;

    /**
     * 出错列号
     */
    private Integer colNum;

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public Integer getRowNum() {
        return rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    public Integer getColNum() {
        return colNum;
    }

    public void setColNum(Integer colNum) {
        this.colNum = colNum;
    }

}
