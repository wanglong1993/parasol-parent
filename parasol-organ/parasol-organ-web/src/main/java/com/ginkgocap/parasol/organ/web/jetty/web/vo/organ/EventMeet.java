package com.ginkgocap.parasol.organ.web.jetty.web.vo.organ;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class EventMeet implements Serializable{

	/**
	 * 会面情况关联   事件
	 */
	private static final long serialVersionUID = 8979292310033267394L;
	
	
	private long id;	
	private String type;
	private String title;
	private int ownerid;
	private String ownername;
	private String requirementtype;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = StringUtils.trimToEmpty(type);
	}
	public String getTitle() {
		return StringUtils.trimToEmpty(title);
	}
	public void setTitle(String title) {
		this.title = StringUtils.trimToEmpty(title);
	}
	public int getOwnerid() {
		return ownerid;
	}
	public void setOwnerid(int ownerid) {
		this.ownerid = ownerid;
	}
	public String getOwnername() {
		return StringUtils.trimToEmpty(ownername);
	}
	public void setOwnername(String ownername) {
		this.ownername = StringUtils.trimToEmpty(ownername);
	}
	public String getRequirementtype() {
		return StringUtils.trimToEmpty(requirementtype);
	}
	public void setRequirementtype(String requirementtype) {
		this.requirementtype = StringUtils.trimToEmpty(requirementtype);
	}
	
	
}
