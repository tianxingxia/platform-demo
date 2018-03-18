package com.yk.platform.business.file.entity;

import java.io.Serializable;
import java.util.Date;

import com.yk.platform.common.BaseEntity;

public class SysFile implements Serializable, BaseEntity {
    /**
     * 序列化Id
     */
    private static final long serialVersionUID = 1L;
    private String fileCode;// 文件md5编码
    private String storageType;// 文件类型是共有还是私有
    private Integer status;// 是否上传到七牛的状态
    private Date createTime;// 创建时间
    private String createBy;// 创建人
    private Integer failTimes;// 上传失败的次数

    public SysFile() {
    }

    public SysFile(String fileCode, String storageType, Integer status) {
        super();
        this.fileCode = fileCode;
        this.storageType = storageType;
        this.status = status;
    }

    public Integer getFailTimes() {
        return failTimes;
    }

    public void setFailTimes(Integer failTimes) {
        this.failTimes = failTimes;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }

    public String getFileCode() {
        return fileCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    @Override
    public Serializable getPrimaryKey() {
        return this.fileCode;
    }

}
