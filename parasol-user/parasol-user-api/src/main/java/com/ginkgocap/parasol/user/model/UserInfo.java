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
	 * 兴趣爱好.
	 */
	private String interests;
	/**
	 * 擅长技能.
	 */
	private String skills;
	
	/**
	 * 好友可见 1.公开，2.好友可见.
	 */
	private Byte isVisible;
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

	public String getInterests() {
		return interests;
	}


	public void setInterests(String interests) {
		this.interests = interests;
	}


	public String getSkills() {
		return skills;
	}


	public void setSkills(String skills) {
		this.skills = skills;
	}


	@Column(name = "is_visible")
	public Byte getIsVisible() {
		return this.isVisible;
	}

	public void setIsVisible(Byte isVisible) {
		this.isVisible = isVisible;
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

}
