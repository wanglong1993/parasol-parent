package com.ginkgocap.parasol.user.model;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
	private int userId;
	/**
	 * 若为组织则为全称，不可更改；若为个人则为昵称，可修改。.
	 */
	private Date birthday;
	/**
	 * 省份id.
	 */
	private int provinceId2;
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
	private Date ctime;
	/**
	 * 修改时间.
	 */
	private Date utime;
	/**
	 * 用户IP.
	 */
	private String ipRegistered;

	public UserInfo() {
	}

	public UserInfo(int userId, Date birthday, int provinceId2, int cityId,
			int countyId, Date ctime, Date utime, String ipRegistered) {
		this.userId = userId;
		this.birthday = birthday;
		this.provinceId2 = provinceId2;
		this.cityId = cityId;
		this.countyId = countyId;
		this.ctime = ctime;
		this.utime = utime;
		this.ipRegistered = ipRegistered;
	}

	public UserInfo(int userId, Date birthday, int provinceId2, int cityId,
			int countyId, Byte isVisible, Date ctime, Date utime,
			String ipRegistered) {
		this.userId = userId;
		this.birthday = birthday;
		this.provinceId2 = provinceId2;
		this.cityId = cityId;
		this.countyId = countyId;
		this.isVisible = isVisible;
		this.ctime = ctime;
		this.utime = utime;
		this.ipRegistered = ipRegistered;
	}

	@Id
	@Column(name = "user_id", unique = true, nullable = false)
	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "birthday", nullable = false, length = 10)
	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Column(name = "province_id2", nullable = false)
	public int getProvinceId2() {
		return this.provinceId2;
	}

	public void setProvinceId2(int provinceId2) {
		this.provinceId2 = provinceId2;
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
