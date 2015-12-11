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
public class UserExt implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3191844990568874921L;
	/**
	 * 个人用户id.
	 */
	private Long userId;
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
	 * 职位.
	 */
	private String companyJob;
	/**
	 * 简称：组织用户的简称或个人用户简称，组织用户可修改。.
	 */
	private String shortName;
	private String description;
	/**
	 * 姓名首字母.
	 */
	private String nameFirst;
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

	public UserExt() {
	}
	@Id
	@Column(name = "user_id")
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	@Column(name = "description", length = 300)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "nameFirst", nullable = false, length = 1)
	public String getNameFirst() {
		return this.nameFirst;
	}

	public void setNameFirst(String nameFirst) {
		this.nameFirst = nameFirst;
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
