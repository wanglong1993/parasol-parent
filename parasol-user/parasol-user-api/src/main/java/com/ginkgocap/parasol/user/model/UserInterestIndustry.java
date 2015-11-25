package com.ginkgocap.parasol.user.model;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

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
	private Long id;
	/**
	 * 个人用户id和组织用户id.
	 */
	private Long userId;
	/**
	 * 一级行业ID.
	 */
	private Long firstIndustryId;
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

	public UserInterestIndustry() {
	}

	public UserInterestIndustry(Long id, Long userId, Long ctime, Long utime,
			String ip) {
		this.id = id;
		this.userId = userId;
		this.ctime = ctime;
		this.utime = utime;
		this.ip = ip;
	}

	public UserInterestIndustry(Long id, Long userId, Long firstIndustryId,
			Long ctime,
			Long utime, String ip) {
		this.id = id;
		this.userId = userId;
		this.firstIndustryId = firstIndustryId;
		this.ctime = ctime;
		this.utime = utime;
		this.ip = ip;
	}

	@Id
	@GeneratedValue(generator = "id")
	@GenericGenerator(name = "id", strategy = "com.ginkgocap.ywxt.framework.dal.dao.id.util.TimeIdGenerator")
	@Column(name = "id")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "user_id", nullable = false)
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "first_industry_id")
	public Long getFirstIndustryId() {
		return this.firstIndustryId;
	}

	public void setFirstIndustryId(Long firstIndustryId) {
		this.firstIndustryId = firstIndustryId;
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
