package com.yk.platform.business.excel.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yk.platform.common.CustomDateSerializer;

/**
 * <p>Description: 导入excel日志记录</p>
 * @date 2014年5月21日
 * @author 李志勇 
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public class ExcelOpLog {
    /* 搜索属性 start */
    /* 操作日期开始 */
    private String opDateStart;
    /* 操作日期结束 */
    private String opDateEnd;
    /* 搜索属性 end */

    /* id */
    private String opId;
    /* 操作人 */
    private String opUser;
    /* 操作时间 */
    private Date opDate = new Date();
    /* 操作结果: 0:失败，1:成功 */
    private int opResult;
    /* 操作类型: 0:导入，1:导出 */
    private int opType;
    /* 总记录数 */
    private int totalRows;
    /* 错误个数 */
    private int errorCount;
    /* 日志说明 */
    private String logDesc;

    /* 详细信息 */
    private List<ExcelOpLogDetail> details = new ArrayList<ExcelOpLogDetail>();

    public String getOpId() {
        return opId;
    }

    public void setOpId(String opId) {
        this.opId = opId;
    }

    public String getOpUser() {
        return opUser;
    }

    public void setOpUser(String opUser) {
        this.opUser = opUser;
    }

    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getOpDate() {
        return opDate;
    }

    public void setOpDate(Date opDate) {
        this.opDate = opDate;
    }

    public int getOpResult() {
        return opResult;
    }

    public void setOpResult(int opResult) {
        this.opResult = opResult;
    }

    public int getOpType() {
        return opType;
    }

    public void setOpType(int opType) {
        this.opType = opType;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }

    public List<ExcelOpLogDetail> getDetails() {
        return details;
    }

    public void setDetails(List<ExcelOpLogDetail> details) {
        this.details = details;
    }

    public String getLogDesc() {
        return logDesc;
    }

    public void setLogDesc(String logDesc) {
        this.logDesc = logDesc;
    }

    public String getOpDateStart() {
        return opDateStart;
    }

    public void setOpDateStart(String opDateStart) {
        this.opDateStart = opDateStart;
    }

    public String getOpDateEnd() {
        return opDateEnd;
    }

    public void setOpDateEnd(String opDateEnd) {
        this.opDateEnd = opDateEnd;
    }

    @Override
    public String toString() {
        return "ExcelOpLog [opDateStart=" + opDateStart + ", opDateEnd=" + opDateEnd + ", opId=" + opId + ", opUser=" + opUser + ", opDate=" + opDate
                + ", opResult=" + opResult + ", opType=" + opType + ", totalRows=" + totalRows + ", errorCount=" + errorCount + ", logDesc=" + logDesc
                + ", details=" + details + "]";
    }

}
