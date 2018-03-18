package com.yk.platform.common;

import com.yk.platform.common.errorcode.ErrorCode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 请求结果的返回封装bean
 * @author liupengfei
 */
@ApiModel(value = "ResultBean: 返回结果封装体 ")
public class ResultBean {

    @ApiModelProperty(value = "状态码", required = true)
    private Integer stateCode; // 状态码

    @ApiModelProperty(value = "描述", required = true)
    private String stateInfo; // 消息

    @ApiModelProperty(value = "返回数据", required = true)
    private Object data; // 返回数据

    public ResultBean() {
        super();
        this.stateCode = ErrorCode.CODE_SUCCESS;
        this.stateInfo = ErrorCode.DESC_SUCCESS;
    }

    public Integer getStateCode() {
        return stateCode;
    }

    public void setStateCode(Integer stateCode) {
        this.stateCode = stateCode;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
