package com.ginkgocap.parasol.user.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 个人情况
 */
@Entity
@Table(name = "tb_user_info", catalog = "parasol_user")
public class UserInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final Long serialVersionUID = 4089605921400987683L;
	/**
	 * 个人用户id.
	 */
	private Long userId;
	/**
	 * 应用id
	 */
	private Long appId;
	/**
	 * 国家
	 */
	private String birthPlaceCountryName;
	/**
	 * 民族
	 */
	private String nation;
	/**
	 * 信仰
	 */
	private String faith;
	/**
	 * 血型
	 */
	private String bloodtype;
	/**
	 * 婚姻状况
	 */
	private String marriaged;
	/**
	 * 语言
	 */
	private String language;
	/**
	 * 出生日期。.
	 */
	private String birthday;
	/**
	 * 省份id.
	 */
	private Long provinceId;
	/**
	 * 省份.
	 */
	private String provinceName;
	/**
	 * 城市id.
	 */
	private Long cityId;
	/**
	 * 城市
	 */
	private String cityName;
	/**
	 * 县id.
	 */
	private Long countyId;
	/**
	 * 县.
	 */
	private String countyName;
	/**
	 * 0:私密,1:好友可见,2:部分好友,3:公开
	 */
	private int permission;
	/**
	 * 部分好友可见时存放好友的id，用逗号“,”隔开。
	 */
	private String friendIds;
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

	public UserInfo() {
	}


	@Id
	@Column(name = "user_id", unique = true)
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "birthday", length = 10)
	public String getBirthday() {
		return this.birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
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

	@Column(name = "birthPlaceCountryName")
	public String getBirthPlaceCountryName() {
		return birthPlaceCountryName;
	}


	public void setBirthPlaceCountryName(String birthPlaceCountryName) {
		this.birthPlaceCountryName = birthPlaceCountryName;
	}

	@Column(name = "nation")
	public String getNation() {
		return nation;
	}


	public void setNation(String nation) {
		this.nation = nation;
	}

	@Column(name = "faith")
	public String getFaith() {
		return faith;
	}


	public void setFaith(String faith) {
		this.faith = faith;
	}

	@Column(name = "bloodtype")
	public String getBloodtype() {
		return bloodtype;
	}


	public void setBloodtype(String bloodtype) {
		this.bloodtype = bloodtype;
	}

	@Column(name = "marriaged")
	public String getMarriaged() {
		return marriaged;
	}


	public void setMarriaged(String marriaged) {
		this.marriaged = marriaged;
	}

	@Column(name = "language")
	public String getLanguage() {
		return language;
	}


	public void setLanguage(String language) {
		this.language = language;
	}
	@Column(name = "permission")
	public int getPermission() {
		return permission;
	}

	public void setPermission(int permission) {
		this.permission = permission;
	}
	@Column(name = "friendIds")
	public String getFriendIds() {
		return friendIds;
	}

	public void setFriendIds(String friendIds) {
		this.friendIds = friendIds;
	}	

	@Column(name = "ctime")
	public Long getCtime() {
		return this.ctime;
	}

	public void setCtime(Long ctime) {
		this.ctime = ctime;
	}

	@Column(name = "utime")
	public Long getUtime() {
		return this.utime;
	}

	public void setUtime(Long utime) {
		this.utime = utime;
	}

	@Column(name = "ip", length = 16)
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Transient
	public String getProvinceName() {
		return provinceName;
	}


	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	@Transient
	public String getCityName() {
		return cityName;
	}


	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	@Transient
	public String getCountyName() {
		return countyName;
	}


	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
	@Column(name = "appId")
	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

}
