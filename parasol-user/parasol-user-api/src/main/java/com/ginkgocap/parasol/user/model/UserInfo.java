package com.ginkgocap.parasol.user.model;

/**
 * 个人情况
 */
import java.util.Date;
public class UserInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int userId;
	private Date birthday;
	private int provinceId2;
	private int cityId;
	private int countyId;
	private Byte isVisible;
	private Date ctime;
	private Date utime;
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

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public int getProvinceId2() {
		return this.provinceId2;
	}

	public void setProvinceId2(int provinceId2) {
		this.provinceId2 = provinceId2;
	}

	public int getCityId() {
		return this.cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public int getCountyId() {
		return this.countyId;
	}

	public void setCountyId(int countyId) {
		this.countyId = countyId;
	}

	public Byte getIsVisible() {
		return this.isVisible;
	}

	public void setIsVisible(Byte isVisible) {
		this.isVisible = isVisible;
	}

	public Date getCtime() {
		return this.ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
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
