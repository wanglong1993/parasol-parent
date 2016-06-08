package com.ginkgocap.parasol.organ.web.jetty.web.vo.organ;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class OrgMeet implements Serializable{

	/**
	 * 会面记录关联  组织
	 */
	private static final long serialVersionUID = 8979292310033267394L;
	
	

	private long id;	
	private int type;
	private String title;
	private String ownerid;
	private String ownername;
	private String address;
	private String hy;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getTitle() {
		return StringUtils.trimToEmpty(title);
	}
	public void setTitle(String title) {
		this.title = StringUtils.trimToEmpty(title);
	}
	public String getOwnerid() {
		return StringUtils.trimToEmpty(ownerid);
	}
	public void setOwnerid(String ownerid) {
		this.ownerid = StringUtils.trimToEmpty(ownerid);
	}
	public String getOwnername() {
		return StringUtils.trimToEmpty(ownername);
	}
	public void setOwnername(String ownername) {
		this.ownername = StringUtils.trimToEmpty(ownername);
	}
	public String getAddress() {
		return StringUtils.trimToEmpty(address);
	}
	public void setAddress(String address) {
		this.address =StringUtils.trimToEmpty(address);
	}
	public String getHy() {
		return StringUtils.trimToEmpty(hy);
	}
	public void setHy(String hy) {
		this.hy = StringUtils.trimToEmpty(hy);
	}
	

}
