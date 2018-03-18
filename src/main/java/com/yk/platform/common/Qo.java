package com.yk.platform.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author liupengfei 查询qo
 */
@ApiModel(value = "基础查询对象")
public class Qo {

    @ApiModelProperty(value = "分页对象")
    private Page page;

    @ApiModelProperty(value = "排序对象")
    private Sort sort;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

}
