package com.ginkgocap.parasol.user.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFilter;

/**
 * 个人用户基本资料
 */
@JsonFilter("com.ginkgocap.parasol.user.model.UserBasic")
@Entity
@Table(name = "tb_user_basic", catalog = "parasol_user")
public class UserBasic implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2834877048769466137L;
	/**
	 * 个人用户id.
	 */
	private Long userId;
	/**
	 * 应用好
	 */
	private Long appId;
	/**
	 * 若为组织则为全称，不可更改；若为个人则为昵称，不可修改。.
	 */
	private String name;
	/**
	 * 性别 男：1，女：2，0：保密.
	 */
	private Byte sex;
	/**
	 * 省市id
	 */
	private long provinceId;
	/**
	 * 省名称
	 */
	private String provinceName;
	/**
	 * 城市id
	 */
	private long cityId;
	/**
	 * 市名称
	 */
	private String cityName;
	/**
	 * 县id
	 */
	private long countyId;
	/**
	 * 县名称
	 */
	private String countyName;
	
	/**
	 * 公司.
	 */
	private String companyName;
	/**
	 * 职位
	 */
	private String companyJob;
	/**
	 * 用户头像.
	 */
	private Long picId;
	/**
	 * 行业id
	 */
	private Long hy_id;
	/**
	 * 姓名手写字母
	 */
	private String nameFirst;


	/**
	 * 姓名全拼
	 */
	private String nameIndexAll;
	/**
	 * 简拼音.
	 */
	private String nameIndex;
	/**
	 * 创建时间.
	 */
	private Long ctime;
	/**
	 * 修改时间.
	 */
	private Long utime;
	/**
	 * 
	 */
	private String picPath;
	
	private String ip;
	
	@Column(name="ip",length=16)
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public UserBasic() {
	}


	@Id
	@Column(name = "user_id")
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "name",  length = 100)
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

	@Column(name = "company_name", length = 50)
	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Column(name = "pic_id")
	public Long getPicId() {
		return this.picId;
	}

	public void setPicId(Long picId) {
		this.picId = picId;
	}
	
    @Transient
	public String getPicPath() {
		return picPath;
	}


	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	@Column(name = "hy_id")
	public Long getHy_id() {
		return hy_id;
	}

	public void setHy_id(Long hy_id) {
		this.hy_id = hy_id;
	}

	@Column(name = "nameIndex", length = 20)
	public String getNameIndex() {
		return this.nameIndex;
	}

	public void setNameIndex(String nameIndex) {
		this.nameIndex = nameIndex;
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

	@Column(name = "province_id", length = 19)
	public long getProvinceId() {
		return provinceId;
	}


	public void setProvinceId(long provinceId) {
		this.provinceId = provinceId;
	}

	@Transient
	public String getProvinceName() {
		return provinceName;
	}


	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	@Column(name = "city_id", length = 19)
	public long getCityId() {
		return cityId;
	}


	public void setCityId(long cityId) {
		this.cityId = cityId;
	}

	@Transient
	public String getCityName() {
		return cityName;
	}


	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	@Column(name = "county_id", length = 19)
	public long getCountyId() {
		return countyId;
	}


	public void setCountyId(long countyId) {
		this.countyId = countyId;
	}

	@Transient
	public String getCountyName() {
		return countyName;
	}


	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	@Column(name = "company_job", length = 50)
	public String getCompanyJob() {
		return companyJob;
	}


	public void setCompanyJob(String companyJob) {
		this.companyJob = companyJob;
	}

	@Column(name = "nameFirst", length =20)
	public String getNameFirst() {
		return nameFirst;
	}


	public void setNameFirst(String nameFirst) {
		this.nameFirst = nameFirst;
	}

	@Column(name = "nameIndexAll", length = 50)
	public String getNameIndexAll() {
		return nameIndexAll;
	}


	public void setNameIndexAll(String nameIndexAll) {
		this.nameIndexAll = nameIndexAll;
	}
	@Column(name = "appId", length = 19)
	public Long getAppId() {
		return appId;
	}

	
	public void setAppId(Long appId) {
		this.appId = appId;
	}
	
	

}
