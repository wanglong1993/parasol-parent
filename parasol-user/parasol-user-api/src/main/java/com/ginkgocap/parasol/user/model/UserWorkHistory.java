package com.ginkgocap.parasol.user.model;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 工作经历
 */
@Entity
@Table(name = "tb_user_work_history", catalog = "parasol_user")
public class UserWorkHistory implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8539917753197384890L;
	/**
	 * 主键.
	 */
	private long id;
	/**
	 * 个人用户id.
	 */
	private long userId;
	/**
	 * 单位名称.
	 */
	private String incName;
	/**
	 * 职务.
	 */
	private String position;
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
	private Date ctime;
	/**
	 * 修改时间.
	 */
	private Date utime;
	/**
	 * 用户IP.
	 */
	private String ipRegistered;

	public UserWorkHistory() {
	}

	public UserWorkHistory(long id, long userId, Date ctime, Date utime,
			String ipRegistered) {
		this.id = id;
		this.userId = userId;
		this.ctime = ctime;
		this.utime = utime;
		this.ipRegistered = ipRegistered;
	}

	public UserWorkHistory(long id, long userId, String incName,
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

	@Column(name = "inc_name")
	public String getIncName() {
		return this.incName;
	}

	public void setIncName(String incName) {
		this.incName = incName;
	}

	@Column(name = "position")
	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	@Column(name = "begin_time")
	public String getBeginTime() {
		return this.beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	@Column(name = "end_time")
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
