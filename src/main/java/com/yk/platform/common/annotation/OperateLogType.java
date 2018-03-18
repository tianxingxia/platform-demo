package com.yk.platform.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.yk.platform.common.enums.OperateLogTypeEnum;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OperateLogType {

    public OperateLogTypeEnum operateLoLogType();// 操作类型

    public String customDescription() default ("");// 自定义说明

}
