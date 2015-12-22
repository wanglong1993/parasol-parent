package com.ginkgocap.parasol.document.model;

import java.io.Serializable;

public class DocumentResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 请求参数名
	private	String name;
	// 类型及范围
	private String type;
	// 参数说明
	private String remark;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
