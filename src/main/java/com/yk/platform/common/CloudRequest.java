package com.yk.platform.common;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件上传下载时用到的请求JSON
 * @author qiaopengfei
 * @date 2014年10月9日
 * @version 1.0.0
 * @Copyright (c) 2014, www.xtc.com All Rights Reserved.
 * 
 */
public class CloudRequest
{

    /**
     * 图片长边显示
     */
    private Integer longEdge;

    /**
     * 图片短边显示
     */
    private Integer shortEdge;

    /**
     * 上传类型 0：微聊
     */
    private Integer uploadType;

    /**
     * 下载时文件类型
     */
    private Integer fileType;

    /**
     * 文件的key
     */
    private String key;

    /**
     * 上传下载多个文件时的url
     */
    private List<String> keys = new ArrayList<String>();

    /**
     * 0：公有空间、1：私有空间
     */
    private Integer type;

    /**
     * 获取Access token，固定值：stat、delete
     */
    private String operation;

    public Integer getType()
    {
        return type;
    }

    public void setType(Integer type)
    {
        this.type = type;
    }

    public String getOperation()
    {
        return operation;
    }

    public void setOperation(String operation)
    {
        this.operation = operation;
    }

    public Integer getLongEdge()
    {
        return longEdge;
    }

    public void setLongEdge(Integer longEdge)
    {
        this.longEdge = longEdge;
    }

    public Integer getShortEdge()
    {
        return shortEdge;
    }

    public void setShortEdge(Integer shortEdge)
    {
        this.shortEdge = shortEdge;
    }

    public Integer getUploadType()
    {
        return uploadType;
    }

    public void setUploadType(Integer uploadType)
    {
        this.uploadType = uploadType;
    }

    public Integer getFileType()
    {
        return fileType;
    }

    public void setFileType(Integer fileType)
    {
        this.fileType = fileType;
    }

    public List<String> getKeys()
    {
        return keys;
    }

    public void setKeys(List<String> keys)
    {
        this.keys = keys;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }
}
