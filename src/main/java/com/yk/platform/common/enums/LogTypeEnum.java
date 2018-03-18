package com.yk.platform.common.enums;

/**
 * @项目名称：demo-platform
 * @类名称：LogTypeEnum
 * @类描述：枚举按照功能模块分
 * @创建人：阳凯
 * @创建时间：2017年2月22日 下午4:40:31
 * @company:步步高教育电子有限公司
 */
public enum LogTypeEnum {

    /**
     * 消息日志
     */
    MSG(1, "消息日志"),

    /**
     * 弹窗日志
     */
    BANNER(2, "弹窗日志"),

    /**
     * 系统文件
     */
    SYSFILE(3, "系统文件"),

    /**
     * 标签日志
     */
    TAG(4, "标签日志");

    private final String value;
    private final Integer id;

    private LogTypeEnum(Integer id, String value) {
        this.id = id;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public Integer getId() {
        return id;
    }

    public static String transferLogType(Integer id) {
        if (MSG.getId().equals(id)) {
            return MSG.getValue();
        }
        if (BANNER.getId().equals(id)) {
            return BANNER.getValue();
        }
        if (SYSFILE.getId().equals(id)) {
            return SYSFILE.getValue();
        }

        return null;
    }
}
