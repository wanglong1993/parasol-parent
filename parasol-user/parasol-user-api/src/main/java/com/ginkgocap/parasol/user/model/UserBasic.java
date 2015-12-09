package com.ginkgocap.parasol.user.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
	private Long userId;
	/**
	 * 若为组织则为全称，不可更改；若为个人则为昵称，不可修改。.
	 */
	private String name;
	/**
	 * 性别 男：1，女：2，0：保密.
	 */
	private Byte sex;
	/**
	 * 省份id.
	 */
	private Long provinceId;
	/**
	 * 城市id.
	 */
	private Long cityId;
	/**
	 * 县id.
	 */
	private Long countyId;
	/**
	 * 用户所在行业第三级id
	 */
	private Long thirdIndustryId;
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
	private Long picId;

	private String description;
	/**
	 * 1：正常；0：锁定；-1：注销 ；2： 删除.
	 */
	private Byte status;
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
	private Long ctime;
	/**
	 * 修改时间.
	 */
	private Long utime;
	/**
	 * 用户IP.
	 */
	private String ip;

	public UserBasic() {
	}

	public UserBasic(Long userId, String name, Byte sex, Byte status,
			String nameFirst, String nameIndex, String nameIndexAll,
			Long ctime, Long utime, String ip) {
		this.userId = userId;
		this.name = name;
		this.sex = sex;
		this.status = status;
		this.nameFirst = nameFirst;
		this.nameIndex = nameIndex;
		this.nameIndexAll = nameIndexAll;
		this.ctime = ctime;
		this.utime = utime;
		this.ip = ip;
	}

	public UserBasic(Long userId, String name, Byte sex, Long provinceId,
			Long cityId, Long countyId, String companyName,
			String companyJob, String shortName, Long picId,
			String description,  Byte status, String nameFirst,
			String nameIndex, String nameIndexAll, Long ctime, Long utime,
			String ip) {
		this.userId = userId;
		this.name = name;
		this.sex = sex;
		this.provinceId = provinceId;
		this.cityId = cityId;
		this.countyId = countyId;
		this.companyName = companyName;
		this.companyJob = companyJob;
		this.shortName = shortName;
		this.picId = picId;
		this.description = description;
		this.status = status;
		this.nameFirst = nameFirst;
		this.nameIndex = nameIndex;
		this.nameIndexAll = nameIndexAll;
		this.ctime = ctime;
		this.utime = utime;
		this.ip = ip;
	}

	@Id
	@Column(name = "user_id")
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
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
	public Byte getSex() {
		return this.sex;
	}

	public void setSex(Byte sex) {
		this.sex = sex;
	}

	@Column(name = "province_id")
	public Long getProvinceId() {
		return this.provinceId;
	}

	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}

	@Column(name = "city_id")
	public Long getCityId() {
		return this.cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	@Column(name = "county_id")
	public Long getCountyId() {
		return this.countyId;
	}

	public void setCountyId(Long countyId) {
		this.countyId = countyId;
	}
	@Column(name = "third_industry_id")
	public Long getThirdIndustryId() {
		return thirdIndustryId;
	}

	public void setThirdIndustryId(Long thirdIndustryId) {
		this.thirdIndustryId = thirdIndustryId;
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

	@Column(name = "pic_id")
	public Long getPicId() {
		return this.picId;
	}

	public void setPicId(Long picId) {
		this.picId = picId;
	}

	@Column(name = "description", length = 300)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "status", nullable = false)
	public Byte getStatus() {
		return this.status;
	}

	public void setStatus(Byte status) {
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

	
	@Column(name = "ctime", nullable = false, length = 19)
	public Long getCtime() {
		return this.ctime;
	}

	public void setCtime(Long ctime) {
		this.ctime = ctime;
	}

	
	@Column(name = "utime", nullable = false, length = 19)
	public Long getUtime() {
		return this.utime;
	}

	public void setUtime(Long utime) {
		this.utime = utime;
	}

	@Column(name = "ip", nullable = false, length = 16)
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
