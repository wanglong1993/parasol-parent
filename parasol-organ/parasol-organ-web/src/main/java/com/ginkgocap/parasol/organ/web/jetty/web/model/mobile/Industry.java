package com.ginkgocap.parasol.organ.web.jetty.web.model.mobile;

import java.io.Serializable;
/**
* <p>Title: Industry.java<／p> 
* <p>Description: 行业对象<／p> 
* @author wfl
* @date 2015-1-31 
* @version 1.0
 */
public class Industry implements Serializable {

	private long id;
	private String name;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
