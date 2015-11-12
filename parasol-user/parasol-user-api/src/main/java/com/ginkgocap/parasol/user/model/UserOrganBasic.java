package com.ginkgocap.parasol.user.model;

/**
 * 组织用户基本信息
 */
import java.util.Date;

public class UserOrganBasic implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int userId;
	private String name;
	private String picPath;
	private byte status;
	private String regFrom;
	private String brief;
	private String shortName;
	private String phone;
	private String orgType;
	private byte auth;
	private String nameFirst;
	private String nameIndex;
	private String nameIndexAll;
	private Date ctime2;
	private Date utime;
	private String ipRegistered;

	public UserOrganBasic() {
	}

	public UserOrganBasic(int userId, String name, String picPath, byte status,
			String regFrom, String shortName, byte auth, String nameFirst,
			String nameIndex, String nameIndexAll, Date ctime2, Date utime,
			String ipRegistered) {
		this.userId = userId;
		this.name = name;
		this.picPath = picPath;
		this.status = status;
		this.regFrom = regFrom;
		this.shortName = shortName;
		this.auth = auth;
		this.nameFirst = nameFirst;
		this.nameIndex = nameIndex;
		this.nameIndexAll = nameIndexAll;
		this.ctime2 = ctime2;
		this.utime = utime;
		this.ipRegistered = ipRegistered;
	}

	public UserOrganBasic(int userId, String name, String picPath, byte status,
			String regFrom, String brief, String shortName, String phone,
			String orgType, byte auth, String nameFirst, String nameIndex,
			String nameIndexAll, Date ctime2, Date utime, String ipRegistered) {
		this.userId = userId;
		this.name = name;
		this.picPath = picPath;
		this.status = status;
		this.regFrom = regFrom;
		this.brief = brief;
		this.shortName = shortName;
		this.phone = phone;
		this.orgType = orgType;
		this.auth = auth;
		this.nameFirst = nameFirst;
		this.nameIndex = nameIndex;
		this.nameIndexAll = nameIndexAll;
		this.ctime2 = ctime2;
		this.utime = utime;
		this.ipRegistered = ipRegistered;
	}
	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPicPath() {
		return this.picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public String getRegFrom() {
		return this.regFrom;
	}

	public void setRegFrom(String regFrom) {
		this.regFrom = regFrom;
	}

	public String getBrief() {
		return this.brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOrgType() {
		return this.orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public byte getAuth() {
		return this.auth;
	}

	public void setAuth(byte auth) {
		this.auth = auth;
	}

	public String getNameFirst() {
		return this.nameFirst;
	}

	public void setNameFirst(String nameFirst) {
		this.nameFirst = nameFirst;
	}

	public String getNameIndex() {
		return this.nameIndex;
	}

	public void setNameIndex(String nameIndex) {
		this.nameIndex = nameIndex;
	}

	public String getNameIndexAll() {
		return this.nameIndexAll;
	}

	public void setNameIndexAll(String nameIndexAll) {
		this.nameIndexAll = nameIndexAll;
	}

	public Date getCtime2() {
		return this.ctime2;
	}

	public void setCtime2(Date ctime2) {
		this.ctime2 = ctime2;
	}

	public Date getUtime() {
		return this.utime;
	}

	public void setUtime(Date utime) {
		this.utime = utime;
	}

	public String getIpRegistered() {
		return this.ipRegistered;
	}

	public void setIpRegistered(String ipRegistered) {
		this.ipRegistered = ipRegistered;
	}

}
