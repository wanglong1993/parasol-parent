package com.ginkgocap.parasol.person.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFilter;
/**
 * 专业技能
 */
@JsonFilter("com.ginkgocap.parasol.person.model.PersonSkill")
@Entity
@Table(name = "tb_person_skill", catalog = "parasol_person")
public class PersonSkill implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1795838785855804624L;
	/**
	 * 用户id
	 */
	private Long personId;
	/**
	 * 应用id
	 */
	private Long appId;
	/**
	 * 专业技能 用逗号隔开
	 */
	private String skills;
	
	
	private Long ctime;
	
	private Long utime;
	
	private String ip;
	@Id
	@Column(name = "person_id", unique = true)
	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	@Column(name = "appId")
	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}
	@Column(name = "skills")
	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}
	@Column(name = "ctime")
	public Long getCtime() {
		return ctime;
	}

	public void setCtime(Long ctime) {
		this.ctime = ctime;
	}
	@Column(name = "utime")
	public Long getUtime() {
		return utime;
	}

	public void setUtime(Long utime) {
		this.utime = utime;
	}
	@Column(name = "ip")
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
}
