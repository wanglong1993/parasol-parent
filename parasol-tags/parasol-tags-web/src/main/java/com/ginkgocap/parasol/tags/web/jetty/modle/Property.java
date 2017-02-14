package com.ginkgocap.parasol.tags.web.jetty.modle;

import java.io.Serializable;

public class Property implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3360254587306141097L;

	/** 属性id **/
	private String id;

	/** 属性名称 **/
	private String name;

	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
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

    public Property(String id, String name)
    {
        this.id = id;
        this.name = name;
    }
}