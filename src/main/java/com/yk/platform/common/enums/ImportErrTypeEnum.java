package com.yk.platform.common.enums;

public enum ImportErrTypeEnum
{

    // 已存在
    HAVE_EXISTS(1, "已存在"),

    // 非法
    INVALID(2, "非法");

    private Integer type;

    private String name;

    private ImportErrTypeEnum(Integer type, String name)
    {
        this.type = type;
        this.name = name;
    }

    public Integer getType()
    {
        return type;
    }

    public String getName()
    {
        return name;
    }

}
