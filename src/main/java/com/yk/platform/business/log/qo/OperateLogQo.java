package com.yk.platform.business.log.qo;

import com.yk.platform.common.Qo;

public class OperateLogQo extends Qo {

    /**
     * 操作人
     */
    private String operator;

    /**
     * 操作类型
     */
    private Integer operateType;

    /**
     * 日志业务类型
     */
    private Integer logType;

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Integer getOperateType() {
        return operateType;
    }

    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }

    public Integer getLogType() {
        return logType;
    }

    public void setLogType(Integer logType) {
        this.logType = logType;
    }

}
