package com.yk.platform.common;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "分页结果封装体")
public class PageResult {

    @ApiModelProperty(value = "当前页, 默认为第1页")
    private long nowPage = 1;

    @ApiModelProperty(value = "每页记录数，默认10条")
    private long pageSize = 10;

    @ApiModelProperty(value = "总记录数", required = true)
    private long totalCount;

    @ApiModelProperty(value = "总分页数")
    private long totalPage;

    @ApiModelProperty(value = "数据集合")
    private List<?> recordList = new ArrayList<>();

    public long getNowPage() {
        return nowPage;
    }

    public void setNowPage(long nowPage) {
        this.nowPage = nowPage;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public List<?> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<?> recordList) {
        this.recordList = recordList;
    }

}
