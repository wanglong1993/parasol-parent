package com.ginkgocap.parasol.person.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFilter;
/**
 * 附件
 */
@JsonFilter("com.ginkgocap.parasol.person.model.PersonAttachment")
@Entity
@Table(name = "tb_person_attachment", catalog = "parasol_person")
public class PersonAttachment implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2517473660791832138L;
	/**
	 * 用户id
	 */
	private Long personId;
	/**
	 * 应用id
	 */
	private Long appId;
	/**
	 * 附件
	 */
	private Long taskId;
	
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
	

	@Column(name="taskId")
	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
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

