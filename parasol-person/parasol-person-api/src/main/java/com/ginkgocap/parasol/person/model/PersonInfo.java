package com.ginkgocap.parasol.person.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 个人情况
 */
@Entity
@Table(name = "tb_person_info", catalog = "parasol_person")
public class PersonInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final Long serialVersionUID = 4089605921400987683L;
	/**
	 * 个人用户id.
	 */
	private Long personId;
	/**
	 * 出生日期。.
	 */
	private Long birthday;
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

	public PersonInfo() {
	}


	@Id
	@Column(name = "person_id", unique = true)
	public Long getPersonId() {
		return this.personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	@Column(name = "birthday", length = 10)
	public Long getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Long birthday) {
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

}
