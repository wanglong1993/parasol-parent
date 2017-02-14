package com.ginkgocap.parasol.directory.web.jetty.modle;

import java.io.Serializable;

public class Property implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3360254587306141097L;

	/** 属性id **/
	private long id;

	/** 属性名称 **/
	private String name;


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

    public Property(){}

}