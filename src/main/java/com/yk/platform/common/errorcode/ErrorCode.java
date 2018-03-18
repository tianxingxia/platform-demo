package com.yk.platform.common.errorcode;

public interface ErrorCode {

    /**
     * 无权限访问
     */
    public static final Integer CODE_ACCESS_DENY = -2;
    public static final String DESC_ACCESS_DENY = "Access Deny.";

    /**
     * 未授权
     */
    public static final Integer CODE_AUTH_FAIL = -1;
    public static final String DESC_AUTH_FAIL = "Auth Fail.";

    /**
     * 成功
     */
    public static final Integer CODE_SUCCESS = 0;
    public static final String DESC_SUCCESS = "Success.";

    /**
     * 其他错误
     */
    public static final Integer CODE_OTHER_ERROR = 2;
    public static final String DESC_OTHER_ERROR = "Other error happened.";

    /**
     * 系统异常
     */
    public static final Integer CODE_SYSTEM_ABNORMAL = 4;
    public static final String DESC_SYSTEM_ABNORMAL = "System abnormal.";

    /**
     * 文件拷贝出错
     */
    public static final Integer CODE_FILE_COPY_FAIL = 20;
    public static final String DESC_FILE_COPY_FAIL = "文件拷贝失败";

    /**
     * Excel文件导入失败
     */
    public static final Integer CODE_EXCEL_IMPORT_FAIL = 21;
    public static final String DESC_EXCEL_IMPORT_FAIL = "Excel文件导入失败";

    /**
     * Excel配置不正确
     */
    public static final Integer CODE_EXCEL_TEMPLATE_INVALID = 22;
    public static final String DESC_EXCEL_TEMPLATE_INVALID = "EXCEL模板配置不正确";

    /**
     * Excel有空行
     */
    public static final Integer CODE_EXCEL_BLANK_LINE = 23;
    public static final String DESC_EXCEL_BLANK_LINE = "Excel中有空行";
}
