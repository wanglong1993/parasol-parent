package com.ginkgocap.parasol.user.model;

/**
 * 个人用户基本资料
 * @author  liurenyuan
 * @ti
 */
import java.util.Date;

public class UserBasic implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int userId;
	private String name;
	private byte sex;
	private Integer provinceId;
	private Integer cityId;
	private Integer countyId;
	private String companyName;
	private String companyJob;
	private String shortName;
	private String picPath;
	private String description;
	private String regFrom;
	private byte status;
	private String nameFirst;
	private String nameIndex;
	private String nameIndexAll;
	private Date ctime2;
	private Date utime;
	private String ipRegistered;

	public UserBasic() {
	}

	public UserBasic(int userId, String name, byte sex, byte status,
			String nameFirst, String nameIndex, String nameIndexAll,
			Date ctime2, Date utime, String ipRegistered) {
		this.userId = userId;
		this.name = name;
		this.sex = sex;
		this.status = status;
		this.nameFirst = nameFirst;
		this.nameIndex = nameIndex;
		this.nameIndexAll = nameIndexAll;
		this.ctime2 = ctime2;
		this.utime = utime;
		this.ipRegistered = ipRegistered;
	}

	public UserBasic(int userId, String name, byte sex, Integer provinceId,
			Integer cityId, Integer countyId, String companyName,
			String companyJob, String shortName, String picPath,
			String description, String regFrom, byte status, String nameFirst,
			String nameIndex, String nameIndexAll, Date ctime2, Date utime,
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

	public byte getSex() {
		return this.sex;
	}

	public void setSex(byte sex) {
		this.sex = sex;
	}

	public Integer getProvinceId() {
		return this.provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public Integer getCityId() {
		return this.cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Integer getCountyId() {
		return this.countyId;
	}

	public void setCountyId(Integer countyId) {
		this.countyId = countyId;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyJob() {
		return this.companyJob;
	}

	public void setCompanyJob(String companyJob) {
		this.companyJob = companyJob;
	}

	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getPicPath() {
		return this.picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRegFrom() {
		return this.regFrom;
	}

	public void setRegFrom(String regFrom) {
		this.regFrom = regFrom;
	}

	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
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
