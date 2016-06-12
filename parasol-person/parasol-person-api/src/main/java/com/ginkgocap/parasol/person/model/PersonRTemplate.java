package com.ginkgocap.parasol.person.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 用户关系表
 * @author gintong
 *
 */
@Entity
@Table(name = "tb_person_r_template", catalog = "parasol_person")
public class PersonRTemplate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2302580298301200012L;
	/**
	 * 用户id
	 */
	private Long personId;
	/**
	 * 模板id
	 */
	private Long templateId;
	
	private String type;
	@Id
	@Column(name = "person_id", unique = true)
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	@Column(name="template_id")
	public Long getTemplateId() {
		return templateId;
	}
	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}
	
	@Column(name="type")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
