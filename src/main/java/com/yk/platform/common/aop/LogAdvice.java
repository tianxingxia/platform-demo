package com.yk.platform.common.aop;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.HtmlUtils;

import com.alibaba.fastjson.JSON;
import com.yk.platform.business.log.entity.OperateLog;
import com.yk.platform.business.log.service.OperateLogService;
import com.yk.platform.common.BaseEntity;
import com.yk.platform.common.annotation.LogType;
import com.yk.platform.common.annotation.OperateLogType;
import com.yk.platform.common.enums.LogTypeEnum;
import com.yk.platform.common.util.ResponseUtils;

/**
 * @项目名称：watchback
 * @类名称：LogAdvice
 * @类描述： 日志切面处理类
 * @创建人：阳凯
 * @创建时间：2017年2月22日 下午4:08:58
 * @company:步步高教育电子有限公司
 */
public class LogAdvice {

    @Autowired
    private OperateLogService operateLogService;

    private static final Logger logger = Logger.getLogger(LogAdvice.class);

    // 此方法将作为后置通知
    public void afterReturnAdvice(JoinPoint joinpoint, OperateLogType operateLogType) {
        // try
        // catch是为了防止日志添加异常影响主事务的提交，经验证在operateLogService里新开事务行不通，在日志记录失败的时候一样不能提交
        OperateLog operateLog = null;
        try {
            LogTypeEnum logType = joinpoint.getTarget().getClass().getAnnotation(LogType.class).logType();
            Object[] args = joinpoint.getArgs();
            operateLog = new OperateLog();
            operateLog.setOperator(ResponseUtils.getUserId());
            operateLog.setOperateDate(new Date());
            operateLog.setLogType(logType.getId());
            operateLog.setOperateType(operateLogType.operateLoLogType().getId());
            operateLog.setCustomDescription(operateLogType.customDescription());

            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof BaseEntity) {
                    operateLog.setOperate(HtmlUtils.htmlUnescape(JSON.toJSONString(args[i])));
                    operateLogService.add(operateLog);
                }
                if (args[i] instanceof ArrayList<?>) {
                    List<?> list = (List<?>) args[i];
                    for (int j = 0, len = list.size(); j < len; j++) {
                        if (list.get(j) instanceof BaseEntity) {
                            operateLog.setOperate(HtmlUtils.htmlUnescape(JSON.toJSONString(list.get(j))));
                            operateLogService.add(operateLog);
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            try {
                logger.error(e);
                logger.error("日志记录失败：" + JSON.toJSONString(operateLog));
            }
            catch (Exception e1) {
                logger.error(e1);
            }
        }
    }
}
