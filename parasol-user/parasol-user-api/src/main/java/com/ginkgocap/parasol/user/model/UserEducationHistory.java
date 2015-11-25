package com.ginkgocap.parasol.user.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
	private long id;
	/**
	 * 个人用户id.
	 */
	private long userId;
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
	private Long ctime;
	/**
	 * 修改时间.
	 */
	private Long utime;
	/**
	 * 用户IP.
	 */
	private String ip;

	public UserEducationHistory() {
	}

	public UserEducationHistory(long id, long userId, char degree,
			String beginTime, String endTime, Long ctime, Long utime,
			String ip) {
		this.id = id;
		this.userId = userId;
		this.degree = degree;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.ctime = ctime;
		this.utime = utime;
		this.ip = ip;
	}

	public UserEducationHistory(long id, long userId, String school,
			String major, char degree, String beginTime, String endTime,
			String description, Byte isVisible, Long ctime, Long utime,
			String ip) {
		this.id = id;
		this.userId = userId;
		this.school = school;
		this.major = major;
		this.degree = degree;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.description = description;
		this.isVisible = isVisible;
		this.ctime = ctime;
		this.utime = utime;
		this.ip = ip;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "user_id", nullable = false)
	public long getUserId() {
		return this.userId;
	}

	public void setUserId(long userId) {
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
