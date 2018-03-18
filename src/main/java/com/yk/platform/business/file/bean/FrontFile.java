package com.yk.platform.business.file.bean;

/**
 * @author liupengfei 返回到前台的文件的信息包含filecode文件的下载码,filename文件名
 */
public class FrontFile {
	private String url;
	private String name;
	private String type;
	private Double size;

	public FrontFile(String url, String name, String type, Double size) {
		super();
		this.url = url;
		this.name = name;
		this.type = type;
		this.size = size;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getSize() {
		return size;
	}

	public void setSize(Double size) {
		this.size = size;
	}

	public FrontFile() {
	}
}
