package com.ginkgocap.parasol.user.model;

/**
 * 教育经历
 */
import java.util.Date;

public class UserEducationHistory implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int userId;
	private String school;
	private String major;
	private char degree;
	private String beginTime;
	private String endTime;
	private String description;
	private Byte isVisible;
	private Date ctime2;
	private Date utime;
	private String ipRegistered;

	public UserEducationHistory() {
	}

	public UserEducationHistory(int id, int userId, char degree,
			String beginTime, String endTime, Date ctime2, Date utime,
			String ipRegistered) {
		this.id = id;
		this.userId = userId;
		this.degree = degree;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.ctime2 = ctime2;
		this.utime = utime;
		this.ipRegistered = ipRegistered;
	}

	public UserEducationHistory(int id, int userId, String school,
			String major, char degree, String beginTime, String endTime,
			String description, Byte isVisible, Date ctime2, Date utime,
			String ipRegistered) {
		this.id = id;
		this.userId = userId;
		this.school = school;
		this.major = major;
		this.degree = degree;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.description = description;
		this.isVisible = isVisible;
		this.ctime2 = ctime2;
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

	public String getSchool() {
		return this.school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getMajor() {
		return this.major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public char getDegree() {
		return this.degree;
	}

	public void setDegree(char degree) {
		this.degree = degree;
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

	public Date getCtime2() {
		return this.ctime2;
	}

	public void setCtime2(Date ctime2) {
		this.ctime2 = ctime2;
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
