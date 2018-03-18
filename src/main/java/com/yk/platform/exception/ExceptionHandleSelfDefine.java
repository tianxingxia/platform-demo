package com.yk.platform.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.yk.platform.common.ResultBeanFactory;
import com.yk.platform.common.errorcode.ErrorCode;
import com.yk.platform.common.util.ResponseUtils;

@ControllerAdvice
public class ExceptionHandleSelfDefine {
    /**
     * 统一异常处理
     * @author liupengfei
     * @return
     */
    private static final Logger logger = Logger.getLogger(ExceptionHandleSelfDefine.class);

    @ExceptionHandler({ Exception.class })
    public void exception(Exception exception, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (exception instanceof BusinessException) {
                logger.error(exception.getMessage());
                ResponseUtils.setObjectResponse(response, ResultBeanFactory.getResultBeanFail(ErrorCode.CODE_OTHER_ERROR, exception.getMessage()));
                return;
            }
            logger.error("系统异常", exception);
            ResponseUtils.setObjectResponse(response,
                    ResultBeanFactory.getResultBeanFail(ErrorCode.CODE_SYSTEM_ABNORMAL, ErrorCode.DESC_SYSTEM_ABNORMAL));
        }
        catch (Exception e) {
            logger.error(e);
            ResponseUtils.setObjectResponse(response,
                    ResultBeanFactory.getResultBeanFail(ErrorCode.CODE_SYSTEM_ABNORMAL, ErrorCode.DESC_SYSTEM_ABNORMAL));
        }

    }
}
