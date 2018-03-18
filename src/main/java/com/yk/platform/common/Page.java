package com.yk.platform.common;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "分页对象")
public class Page implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "当前页")
    private long nowPage = 1;

    @ApiModelProperty(value = "总记录数")
    private long totalRecord = 0;

    @ApiModelProperty(value = "总页数")
    private long totalPage;

    @ApiModelProperty(value = "每页记录数，默认10条")
    private long pageSize = 10;// mysql 使用

    @ApiModelProperty(value = "开始位置（mysql 使用）")
    private long startIndex = 0;// mysql 使用 : select * from user limit

    public Page() {
        super();
    }

    public Page(String p, long nowPage, long totalRecord, long totalPage, long pageSize, long startIndex, long beginNo, long endNo) {
        super();
        this.nowPage = nowPage;
        this.totalRecord = totalRecord;
        this.totalPage = totalPage;
        this.pageSize = pageSize;
        this.startIndex = startIndex;
    }

    public long getNowPage() {
        return nowPage;
    }

    public void setNowPage(long nowPage) {
        this.nowPage = nowPage;
    }

    public long getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(long totalRecord) {
        this.totalRecord = totalRecord;
    }

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getStartIndex() {
        if (startIndex > 0) {
            return startIndex;
        }
        else {
            if (nowPage < 1) {
                return 0;
            }
            startIndex = (nowPage - 1) * pageSize;
            return startIndex;
        }
    }

    public void setStartIndex(long startIndex) {
        this.startIndex = startIndex;
    }
}
