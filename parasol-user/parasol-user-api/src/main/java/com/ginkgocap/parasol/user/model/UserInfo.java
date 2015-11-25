package com.ginkgocap.parasol.user.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * 个人情况
 */
@Entity
@Table(name = "tb_user_info", catalog = "parasol_user")
public class UserInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -814700630019653933L;
	/**
	 * 个人用户id.
	 */
	private long userId;
	/**
	 * 若为组织则为全称，不可更改；若为个人则为昵称，可修改。.
	 */
	private Long birthday;
	/**
	 * 省份id.
	 */
	private int provinceId;
	/**
	 * 城市id.
	 */
	private int cityId;
	/**
	 * 县id.
	 */
	private int countyId;
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

	public UserInfo(long userId, Long birthday, int provinceId, int cityId,
			int countyId, Long ctime, Long utime, String ip) {
		this.userId = userId;
		this.birthday = birthday;
		this.provinceId = provinceId;
		this.cityId = cityId;
		this.countyId = countyId;
		this.ctime = ctime;
		this.utime = utime;
		this.ip = ip;
	}

	public UserInfo(long userId, Long birthday, int provinceId, int cityId,
			int countyId, Byte isVisible, Long ctime, Long utime,
			String ip) {
		this.userId = userId;
		this.birthday = birthday;
		this.provinceId = provinceId;
		this.cityId = cityId;
		this.countyId = countyId;
		this.isVisible = isVisible;
		this.ctime = ctime;
		this.utime = utime;
		this.ip = ip;
	}

	@Id
	@Column(name = "user_id", unique = true, nullable = false)
	public long getUserId() {
		return this.userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@Column(name = "birthday", nullable = false, length = 10)
	public Long getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Long birthday) {
		this.birthday = birthday;
	}

	@Column(name = "province_id", nullable = false)
	public int getProvinceId() {
		return this.provinceId;
	}

	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}

	@Column(name = "city_id", nullable = false)
	public int getCityId() {
		return this.cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	@Column(name = "county_id", nullable = false)
	public int getCountyId() {
		return this.countyId;
	}

	public void setCountyId(int countyId) {
		this.countyId = countyId;
	}

	@Column(name = "is_visible")
	public Byte getIsVisible() {
		return this.isVisible;
	}

	public void setIsVisible(Byte isVisible) {
		this.isVisible = isVisible;
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
