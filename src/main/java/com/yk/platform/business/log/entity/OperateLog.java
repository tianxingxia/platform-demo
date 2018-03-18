package com.yk.platform.business.log.entity;

import java.io.Serializable;
import java.util.Date;

public class OperateLog implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long logId;// 主键
    private String operator;// 操作者
    private String operate;// 具体的操作
    private Date operateDate;// 操作时间
    private Integer logType;// 操作业务类型，比如应用，品牌
    private Integer operateType;// 操作的类型，更新，删除，新增
    private String customDescription;// 自定义操作描述
    private String logTypeShow;
    private String operateTypeShow;

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }

    public Date getOperateDate() {
        return operateDate;
    }

    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }

    public Integer getLogType() {
        return logType;
    }

    public void setLogType(Integer logType) {
        this.logType = logType;
    }

    public Integer getOperateType() {
        return operateType;
    }

    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }

    public String getCustomDescription() {
        return customDescription;
    }

    public void setCustomDescription(String customDescription) {
        this.customDescription = customDescription;
    }

    public String getLogTypeShow() {
        return logTypeShow;
    }

    public void setLogTypeShow(String logTypeShow) {
        this.logTypeShow = logTypeShow;
    }

    public String getOperateTypeShow() {
        return operateTypeShow;
    }

    public void setOperateTypeShow(String operateTypeShow) {
        this.operateTypeShow = operateTypeShow;
    }

}
