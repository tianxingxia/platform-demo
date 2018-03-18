package com.yk.platform.business.file.bean;

import java.io.Serializable;

/**
 * @author liupengfei 上传文件的信息，路径，名称和传到什么空间，异步上传到七牛的时候使用
 */
public class UploadInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String filePath;
	private String fileName;
	private String type;

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public UploadInfo(String filePath, String fileName, String type) {
		super();
		this.filePath = filePath;
		this.fileName = fileName;
		this.type = type;
	}

	public UploadInfo() {
	}

}
