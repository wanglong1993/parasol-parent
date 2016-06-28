package com.ginkgocap.parasol.person.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFilter;
/**
 * 用户描述
 */
@JsonFilter("com.ginkgocap.parasol.person.model.PersonDescription")
@Entity
@Table(name = "tb_person_description", catalog = "parasol_person")
public class PersonDescription implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -6394445962590277131L;
	/**
	 * 用户id
	 */
	private Long personId;
	/**
	 * 应用id
	 */
	private Long appId;
	/**
	 * 用户描述
	 */
	private String description;
	
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

	@Column(name="description",length=2048)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

