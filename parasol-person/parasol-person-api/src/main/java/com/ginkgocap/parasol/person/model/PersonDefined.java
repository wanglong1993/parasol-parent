package com.ginkgocap.parasol.person.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFilter;

/**
 * 用户自定义
 */
@JsonFilter("com.ginkgocap.parasol.person.model.PersonDefined")
@Entity
@Table(name = "tb_person_defined", catalog = "parasol_person")
public class PersonDefined implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 8690847030158633991L;
	/**
	 * 主键.
	 */
	private Long id;
	/**
	 * 用户id.
	 */
	private Long personId;
	/**
	 * 应用id
	 */
	private Long appId;
	

	/**
	 * 自定义字段名.
	 */
	private String personDefinedFiled;
	/**
	 * 自定义字段值
	 */
	private String personDefinedValue;
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

	public PersonDefined() {
	}

	public PersonDefined(Long id, Long userId, String personDefinedValue, Long ctime,
			Long utime, String ip) {
		this.id = id;
		this.personId = userId;
		this.personDefinedValue = personDefinedValue;
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
	public Long getPersonId() {
		return this.personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	@Column(name = "user_defined_filed")
	public String getPersonDefinedFiled() {
		return this.personDefinedFiled;
	}

	public void setPersonDefinedFiled(String personDefinedFiled) {
		this.personDefinedFiled = personDefinedFiled;
	}

	@Column(name = "user_defined_value", nullable = false, length = 1)
	public String getPersonDefinedValue() {
		return this.personDefinedValue;
	}

	public void setPersonDefinedValue(String personDefinedValue) {
		this.personDefinedValue = personDefinedValue;
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
	@Column(name = "appId")
	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

}
