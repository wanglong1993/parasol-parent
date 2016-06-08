package com.ginkgocap.parasol.organ.web.jetty.web.vo.organ;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class PeopleMeet implements Serializable{

	/**
	 *  会面记录关联  人脉
	 */
	private static final long serialVersionUID = 8979292310033267394L;

	private long id;	
	private int type;
	private String name;
	private String ownerid;
	private String ownername;
	private String caree;
	private String company;
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
	public String getName() {
		return StringUtils.trimToEmpty(name);
	}
	public void setName(String name) {
		this.name = StringUtils.trimToEmpty(name);
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
	public String getCaree() {
		return StringUtils.trimToEmpty(caree);
	}
	public void setCaree(String caree) {
		this.caree = StringUtils.trimToEmpty(caree);
	}
	public String getCompany() {
		return StringUtils.trimToEmpty(company);
	}
	public void setCompany(String company) {
		this.company = StringUtils.trimToEmpty(company);
	}
		

}
