package com.yk.platform.business.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @项目名称：demo-platform
 * @类名称：Demo
 * @类描述：示例实体类
 * @创建人：阳凯
 * @创建时间：2017年12月20日 下午9:07:51
 * @company:步步高教育电子有限公司
 */
@ApiModel(value = "示例实体类")
public class Demo {

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
