package com.ginkgocap.parasol.user.model;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 组织用户基本信息
 */
@Entity
@Table(name = "tb_organ_basic", catalog = "parasol_user")
public class UserOrganBasic implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2592970951379721944L;
	/**
	 * 组织用户id.
	 */
	private long userId;
	/**
	 * 若为组织则为全称，不可更改；若为个人则为昵称，可修改。.
	 */
	private String name;
	/**
	 * 用户头像.
	 */
	private String picPath;
	/**
	 * 1：正常；0：锁定；-1：注销 ；2： 删除.
	 */
	private byte status;
	/**
	 * 1. gintongweb、2.gintongapp.
	 */
	private String regFrom;
	/**
	 * 若为组织则为全称，不可更改；若为个人则为昵称，可修改。.
	 */
	private String brief;
	/**
	 * 简称：组织用户的简称或个人用户简称，组织用户可修改。.
	 */
	private String shortName;

	private String phone;
	/**
	 * 组织类型 金融机构 一般企业 中介机构 政府机构 期刊报纸 研究机构 电视广播 互联网媒体.
	 */
	private String orgType;
	/**
	 * 审核是否通过: 认证状态-1 未进行认证 0认证进行中 1认证失败 2认证成功.
	 */
	private byte auth;
	/**
	 * 组织首字母.
	 */
	private String nameFirst;
	/**
	 * 简拼音.
	 */
	private String nameIndex;
	/**
	 * 全拼音.
	 */
	private String nameIndexAll;
	/**
	 * 创建时间.
	 */
	private Date ctime2;
	/**
	 * 修改时间.
	 */
	private Date utime;
	/**
	 * 用户IP.
	 */
	private String ipRegistered;

	public UserOrganBasic() {
	}

	public UserOrganBasic(long userId, String name, String picPath, byte status,
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

	public UserOrganBasic(long userId, String name, String picPath, byte status,
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

	@Id
	@Column(name = "user_id", unique = true, nullable = false)
	public long getUserId() {
		return this.userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@Column(name = "name", nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "pic_path", nullable = false, length = 200)
	public String getPicPath() {
		return this.picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	@Column(name = "status", nullable = false)
	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	@Column(name = "regFrom", nullable = false, length = 20)
	public String getRegFrom() {
		return this.regFrom;
	}

	public void setRegFrom(String regFrom) {
		this.regFrom = regFrom;
	}

	@Column(name = "brief", length = 100)
	public String getBrief() {
		return this.brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	@Column(name = "shortName", nullable = false, length = 50)
	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@Column(name = "phone", length = 15)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "orgType", length = 20)
	public String getOrgType() {
		return this.orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	@Column(name = "auth", nullable = false)
	public byte getAuth() {
		return this.auth;
	}

	public void setAuth(byte auth) {
		this.auth = auth;
	}

	@Column(name = "nameFirst", nullable = false, length = 1)
	public String getNameFirst() {
		return this.nameFirst;
	}

	public void setNameFirst(String nameFirst) {
		this.nameFirst = nameFirst;
	}

	@Column(name = "nameIndex", nullable = false, length = 20)
	public String getNameIndex() {
		return this.nameIndex;
	}

	public void setNameIndex(String nameIndex) {
		this.nameIndex = nameIndex;
	}

	@Column(name = "nameIndexAll", nullable = false, length = 50)
	public String getNameIndexAll() {
		return this.nameIndexAll;
	}

	public void setNameIndexAll(String nameIndexAll) {
		this.nameIndexAll = nameIndexAll;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ctime2", nullable = false, length = 19)
	public Date getCtime2() {
		return this.ctime2;
	}

	public void setCtime2(Date ctime2) {
		this.ctime2 = ctime2;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "utime", nullable = false, length = 19)
	public Date getUtime() {
		return this.utime;
	}

	public void setUtime(Date utime) {
		this.utime = utime;
	}

	@Column(name = "ip_registered", nullable = false, length = 16)
	public String getIpRegistered() {
		return this.ipRegistered;
	}

	public void setIpRegistered(String ipRegistered) {
		this.ipRegistered = ipRegistered;
	}

}
