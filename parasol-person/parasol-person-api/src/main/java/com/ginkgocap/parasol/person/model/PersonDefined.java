package com.ginkgocap.parasol.person.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 人脉自定义模块
 */
@Entity
@Table(name = "tb_person_defined", catalog = "parasol_person")
public class PersonDefined implements java.io.Serializable {

	/**
	 * 
	 */
	private static final Long serialVersionUID = -4739693535991876072L;
	/**
	 * 主键.
	 */
	private Long id;
	/**
	 * 用户id.
	 */
	private Long personId;
	/**
	 * 自定义模块名.
	 */
	private String personDefinedModel;
	/**
	 * 自定义字段名.
	 */
	private String personDefinedFiled;
	/**
	 * 自定义字段内容.
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

	@Column(name = "person_defined_model")
	public String getPersonDefinedModel() {
		return this.personDefinedModel;
	}

	public void setPersonDefinedModel(String personDefinedModel) {
		this.personDefinedModel = personDefinedModel;
	}

	@Column(name = "person_defined_filed")
	public String getPersonDefinedFiled() {
		return this.personDefinedFiled;
	}

	public void setPersonDefinedFiled(String personDefinedFiled) {
		this.personDefinedFiled = personDefinedFiled;
	}

	@Column(name = "person_defined_value")
	public String getPersonDefinedValue() {
		return this.personDefinedValue;
	}

	public void setPersonDefinedValue(String personDefinedValue) {
		this.personDefinedValue = personDefinedValue;
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
