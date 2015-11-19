package com.ginkgocap.parasol.user.model;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 用户感兴趣行业
 */
@Entity
@Table(name = "tb_user_interest_industry", catalog = "parasol_user")
public class UserInterestIndustry implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1958374720596146371L;
	/**
	 * 主键.
	 */
	private long id;
	/**
	 * 个人用户id和组织用户id.
	 */
	private long userId;
	/**
	 * 一级行业ID.
	 */
	private Integer firstIndustryId;
	/**
	 * 二级行业ID.
	 */
	private Integer secondIndustryId;
	/**
	 * 三级行业ID.
	 */
	private Integer threeIndustryId;
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

	public UserInterestIndustry() {
	}

	public UserInterestIndustry(long id, long userId, Date ctime, Date utime,
			String ipRegistered) {
		this.id = id;
		this.userId = userId;
		this.ctime = ctime;
		this.utime = utime;
		this.ipRegistered = ipRegistered;
	}

	public UserInterestIndustry(long id, long userId, Integer firstIndustryId,
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

	@Column(name = "first_industry_id")
	public Integer getFirstIndustryId() {
		return this.firstIndustryId;
	}

	public void setFirstIndustryId(Integer firstIndustryId) {
		this.firstIndustryId = firstIndustryId;
	}

	@Column(name = "second_industry_id")
	public Integer getSecondIndustryId() {
		return this.secondIndustryId;
	}

	public void setSecondIndustryId(Integer secondIndustryId) {
		this.secondIndustryId = secondIndustryId;
	}

	@Column(name = "three_industry_id")
	public Integer getThreeIndustryId() {
		return this.threeIndustryId;
	}

	public void setThreeIndustryId(Integer threeIndustryId) {
		this.threeIndustryId = threeIndustryId;
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
