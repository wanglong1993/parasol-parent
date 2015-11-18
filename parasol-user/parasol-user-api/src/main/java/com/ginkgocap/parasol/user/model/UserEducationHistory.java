package com.ginkgocap.parasol.user.model;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 教育经历
 */
@Entity
@Table(name = "tb_user_education_history", catalog = "parasol_user")
public class UserEducationHistory implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5825315645529184397L;
	/**
	 * 主键.
	 */
	private int id;
	/**
	 * 个人用户id.
	 */
	private int userId;
	/**
	 * 学校.
	 */
	private String school;
	/**
	 * 专业.
	 */
	private String major;
	/**
	 * 学历  0小学 1初中 2高中 3中专 4专科 5本科 6硕士 7博士.
	 */
	private char degree;
	/**
	 * 开始时间.
	 */
	private String beginTime;
	/**
	 * 结束时间.
	 */
	private String endTime;
	/**
	 * 描述.
	 */
	private String description;
	/**
	 * 好友可见 1.公开，2.好友可见.
	 */
	private Byte isVisible;
	/**
	 * 创建时间.
	 */
	private Date ctime2;
	/**
	 * 修改时间.
	 */
	private Date utime;
	/**
	 * 用户IP.
	 */
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

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "user_id", nullable = false)
	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Column(name = "school")
	public String getSchool() {
		return this.school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	@Column(name = "major")
	public String getMajor() {
		return this.major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	@Column(name = "degree", nullable = false, length = 1)
	public char getDegree() {
		return this.degree;
	}

	public void setDegree(char degree) {
		this.degree = degree;
	}

	@Column(name = "begin_time", nullable = false)
	public String getBeginTime() {
		return this.beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	@Column(name = "end_time", nullable = false)
	public String getEndTime() {
		return this.endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@Column(name = "description", length = 2024)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "is_visible")
	public Byte getIsVisible() {
		return this.isVisible;
	}

	public void setIsVisible(Byte isVisible) {
		this.isVisible = isVisible;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ctime2", nullable = false, length = 19)
	public Date getCtime2() {
		return this.ctime2;
	}

	public void setCtime2(Date ctime2) {
		this.ctime2 = ctime2;
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
