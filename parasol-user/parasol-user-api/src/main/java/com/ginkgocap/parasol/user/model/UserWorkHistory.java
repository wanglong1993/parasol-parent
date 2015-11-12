package com.ginkgocap.parasol.user.model;

/**
 * 工作经历
 */

import java.util.Date;

public class UserWorkHistory implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int userId;
	private String incName;
	private String position;
	private String beginTime;
	private String endTime;
	private String description;
	private Byte isVisible;
	private Date ctime;
	private Date utime;
	private String ipRegistered;

	public UserWorkHistory() {
	}

	public UserWorkHistory(int id, int userId, Date ctime, Date utime,
			String ipRegistered) {
		this.id = id;
		this.userId = userId;
		this.ctime = ctime;
		this.utime = utime;
		this.ipRegistered = ipRegistered;
	}

	public UserWorkHistory(int id, int userId, String incName,
			String position, String beginTime, String endTime,
			String description, Byte isVisible, Date ctime, Date utime,
			String ipRegistered) {
		this.id = id;
		this.userId = userId;
		this.incName = incName;
		this.position = position;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.description = description;
		this.isVisible = isVisible;
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

	public String getIncName() {
		return this.incName;
	}

	public void setIncName(String incName) {
		this.incName = incName;
	}

	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getBeginTime() {
		return this.beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return this.endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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
