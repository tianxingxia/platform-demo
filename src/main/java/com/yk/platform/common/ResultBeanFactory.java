package com.yk.platform.common;

import com.yk.platform.common.errorcode.ErrorCode;

/**
 * @author liupengfei 获取resultBean的工厂
 */
public class ResultBeanFactory {

    public static ResultBean getResultBeanSuccess() {
        ResultBean resultBean = new ResultBean();
        resultBean.setStateCode(ErrorCode.CODE_SUCCESS);
        resultBean.setStateInfo(ErrorCode.DESC_SUCCESS);
        return resultBean;
    }

    /**
     * 获取失败带消息的resultBean
     * 
     * @return
     */
    public static ResultBean getResultBeanFail(Integer code, String message) {
        ResultBean resultBean = new ResultBean();
        resultBean.setStateCode(code);
        resultBean.setStateInfo(message);
        return resultBean;
    }
}
