package com.yk.platform.common;

public class AbstractController {

    /**
     * 获取成功的resultBEan
     * 
     * @return
     */
    public ResultBean getSuccessResultBean() {
        return ResultBeanFactory.getResultBeanSuccess();
    }

    /**
     * 获取失败的resultBEan
     * 
     * @return
     */
    public ResultBean getFailureResultBean(Integer code, String message) {
        return ResultBeanFactory.getResultBeanFail(code, message);
    }
}
