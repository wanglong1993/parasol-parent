package com.ginkgocap.parasol.person.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 教育经历
 */
@Entity
@Table(name = "tb_person_education_history", catalog = "parasol_person")
public class PersonEducationHistory implements java.io.Serializable {

	/**
	 * 
	 */
	private static final Long serialVersionUID = -3176181471395972961L;
	/**
	 * 主键.
	 */
	private Long id;
	/**
	 * 个人用户id.
	 */
	private Long personId;
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
	private Byte degree;
	/**
	 * 开始时间.
	 */
	private Long beginTime;
	/**
	 * 结束时间.
	 */
	private Long endTime;
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

	public PersonEducationHistory() {
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

	@Column(name = "person_id")
	public Long getPersonId() {
		return this.personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
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

	@Column(name = "degree", length = 1)
	public Byte getDegree() {
		return this.degree;
	}

	public void setDegree(Byte degree) {
		this.degree = degree;
	}

	@Column(name = "begin_time")
	public Long getBeginTime() {
		return this.beginTime;
	}

	public void setBeginTime(Long beginTime) {
		this.beginTime = beginTime;
	}

	@Column(name = "end_time")
	public Long getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Long endTime) {
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

	@Column(name = "ctime")
	public Long getCtime() {
		return this.ctime;
	}

	public void setCtime(Long ctime) {
		this.ctime = ctime;
	}

	@Column(name = "utime")
	public Long getUtime() {
		return this.utime;
	}

	public void setUtime(Long utime) {
		this.utime = utime;
	}

	@Column(name = "ip", length = 16)
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
