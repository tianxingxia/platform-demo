package com.yk.platform.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "排序对象")
public class Sort {

    @ApiModelProperty(value = "排序字段")
    private String fieldName;

    @ApiModelProperty(value = "排序方式（ASC 升序；DESC 降序）")
    private String sort;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public Sort() {
    }

    public Sort(String fieldName, String sort) {
        super();
        this.fieldName = fieldName;
        this.sort = sort;
    }

    public enum SortEnum {
        DESC("desc");
        private final String value;

        private SortEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
