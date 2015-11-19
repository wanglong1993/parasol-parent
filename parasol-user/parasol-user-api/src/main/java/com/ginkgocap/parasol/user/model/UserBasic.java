package com.ginkgocap.parasol.user.model;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 个人用户基本资料
 */
@Entity
@Table(name = "tb_user_basic", catalog = "parasol_user")
public class UserBasic implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3191844990568874921L;
	/**
	 * 个人用户id.
	 */
	private long userId;
	/**
	 * 若为组织则为全称，不可更改；若为个人则为昵称，不可修改。.
	 */
	private String name;
	/**
	 * 性别 男：1，女：2，0：保密.
	 */
	private byte sex;
	/**
	 * 省份id.
	 */
	private Integer provinceId;
	/**
	 * 城市id.
	 */
	private Integer cityId;
	/**
	 * 县id.
	 */
	private Integer countyId;
	/**
	 * 公司.
	 */
	private String companyName;
	/**
	 * 职位.
	 */
	private String companyJob;
	/**
	 * 简称：组织用户的简称或个人用户简称，组织用户可修改。.
	 */
	private String shortName;
	/**
	 * 用户头像.
	 */
	private String picPath;

	private String description;
	/**
	 * 1. gintongweb、2.gintongapp.
	 */
	private String regFrom;
	/**
	 * 1：正常；0：锁定；-1：注销 ；2： 删除.
	 */
	private byte status;
	/**
	 * 姓名首字母.
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
	private Date ctime;
	/**
	 * 修改时间.
	 */
	private Date utime;
	/**
	 * 用户IP.
	 */
	private String ipRegistered;

	public UserBasic() {
	}

	public UserBasic(long userId, String name, byte sex, byte status,
			String nameFirst, String nameIndex, String nameIndexAll,
			Date ctime, Date utime, String ipRegistered) {
		this.userId = userId;
		this.name = name;
		this.sex = sex;
		this.status = status;
		this.nameFirst = nameFirst;
		this.nameIndex = nameIndex;
		this.nameIndexAll = nameIndexAll;
		this.ctime = ctime;
		this.utime = utime;
		this.ipRegistered = ipRegistered;
	}

	public UserBasic(long userId, String name, byte sex, Integer provinceId,
			Integer cityId, Integer countyId, String companyName,
			String companyJob, String shortName, String picPath,
			String description, String regFrom, byte status, String nameFirst,
			String nameIndex, String nameIndexAll, Date ctime, Date utime,
			String ipRegistered) {
		this.userId = userId;
		this.name = name;
		this.sex = sex;
		this.provinceId = provinceId;
		this.cityId = cityId;
		this.countyId = countyId;
		this.companyName = companyName;
		this.companyJob = companyJob;
		this.shortName = shortName;
		this.picPath = picPath;
		this.description = description;
		this.regFrom = regFrom;
		this.status = status;
		this.nameFirst = nameFirst;
		this.nameIndex = nameIndex;
		this.nameIndexAll = nameIndexAll;
		this.ctime = ctime;
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

	@Column(name = "sex", nullable = false)
	public byte getSex() {
		return this.sex;
	}

	public void setSex(byte sex) {
		this.sex = sex;
	}

	@Column(name = "province_id")
	public Integer getProvinceId() {
		return this.provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	@Column(name = "city_id")
	public Integer getCityId() {
		return this.cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	@Column(name = "county_id")
	public Integer getCountyId() {
		return this.countyId;
	}

	public void setCountyId(Integer countyId) {
		this.countyId = countyId;
	}

	@Column(name = "company_name", length = 50)
	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Column(name = "company_job", length = 100)
	public String getCompanyJob() {
		return this.companyJob;
	}

	public void setCompanyJob(String companyJob) {
		this.companyJob = companyJob;
	}

	@Column(name = "shortName", length = 50)
	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@Column(name = "pic_path", length = 200)
	public String getPicPath() {
		return this.picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	@Column(name = "description", length = 300)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "regFrom", length = 20)
	public String getRegFrom() {
		return this.regFrom;
	}

	public void setRegFrom(String regFrom) {
		this.regFrom = regFrom;
	}

	@Column(name = "status", nullable = false)
	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
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
	@Column(name = "ctime", nullable = false, length = 19)
	public Date getCtime() {
		return this.ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
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
