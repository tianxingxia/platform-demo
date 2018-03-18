package com.yk.platform.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.yk.platform.common.enums.LogTypeEnum;

/**
 * 、
 * @author liupengfei 日志的类型
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogType {
    public LogTypeEnum logType();
}
