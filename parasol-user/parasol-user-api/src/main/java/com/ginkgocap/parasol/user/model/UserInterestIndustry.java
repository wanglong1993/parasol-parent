package com.ginkgocap.parasol.user.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

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
	private Long firstIndustryId;
	/**
	 * 二级行业ID.
	 */
	private Long secondIndustryId;
	/**
	 * 三级行业ID.
	 */
	private Long thirdIndustryId;
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
	private String ip;

	public UserInterestIndustry() {
	}

	public UserInterestIndustry(long id, long userId, Date ctime, Date utime,
			String ip) {
		this.id = id;
		this.userId = userId;
		this.ctime = ctime;
		this.utime = utime;
		this.ip = ip;
	}

	public UserInterestIndustry(long id, long userId, Long firstIndustryId,
			Long secondIndustryId, Long thirdIndustryId, Date ctime,
			Date utime, String ip) {
		this.id = id;
		this.userId = userId;
		this.firstIndustryId = firstIndustryId;
		this.secondIndustryId = secondIndustryId;
		this.thirdIndustryId = thirdIndustryId;
		this.ctime = ctime;
		this.utime = utime;
		this.ip = ip;
	}

	@Id
	@GeneratedValue(generator = "id")
	@GenericGenerator(name = "id", strategy = "com.ginkgocap.ywxt.framework.dal.dao.id.util.TimeIdGenerator")
	@Column(name = "id")
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
	public Long getFirstIndustryId() {
		return this.firstIndustryId;
	}

	public void setFirstIndustryId(Long firstIndustryId) {
		this.firstIndustryId = firstIndustryId;
	}

	@Column(name = "second_industry_id")
	public Long getSecondIndustryId() {
		return this.secondIndustryId;
	}

	public void setSecondIndustryId(Long secondIndustryId) {
		this.secondIndustryId = secondIndustryId;
	}

	@Column(name = "third_industry_id")
	public Long getThirdIndustryId() {
		return this.thirdIndustryId;
	}

	public void setThirdIndustryId(Long thirdIndustryId) {
		this.thirdIndustryId = thirdIndustryId;
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

	@Column(name = "ip", nullable = false, length = 16)
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
