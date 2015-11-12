package com.ginkgocap.parasol.user.model;

/**
 * 用户感兴趣行业
 */

import java.util.Date;

public class UserInterestIndustry implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int userId;
	private Integer firstIndustryId;
	private Integer secondIndustryId;
	private Integer threeIndustryId;
	private Date ctime;
	private Date utime;
	private String ipRegistered;

	public UserInterestIndustry() {
	}

	public UserInterestIndustry(int id, int userId, Date ctime, Date utime,
			String ipRegistered) {
		this.id = id;
		this.userId = userId;
		this.ctime = ctime;
		this.utime = utime;
		this.ipRegistered = ipRegistered;
	}

	public UserInterestIndustry(int id, int userId, Integer firstIndustryId,
			Integer secondIndustryId, Integer threeIndustryId, Date ctime,
			Date utime, String ipRegistered) {
		this.id = id;
		this.userId = userId;
		this.firstIndustryId = firstIndustryId;
		this.secondIndustryId = secondIndustryId;
		this.threeIndustryId = threeIndustryId;
		this.ctime = ctime;
		this.utime = utime;
		this.ipRegistered = ipRegistered;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Integer getFirstIndustryId() {
		return this.firstIndustryId;
	}

	public void setFirstIndustryId(Integer firstIndustryId) {
		this.firstIndustryId = firstIndustryId;
	}

	public Integer getSecondIndustryId() {
		return this.secondIndustryId;
	}

	public void setSecondIndustryId(Integer secondIndustryId) {
		this.secondIndustryId = secondIndustryId;
	}

	public Integer getThreeIndustryId() {
		return this.threeIndustryId;
	}

	public void setThreeIndustryId(Integer threeIndustryId) {
		this.threeIndustryId = threeIndustryId;
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
