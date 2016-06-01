package com.ginkgocap.parasol.user.model;

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
@Table(name = "tb_user_r_template", catalog = "parasol_user")
public class UserRTemplate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7751739618404799763L;
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 模板id
	 */
	private Long templateId;
	
	private String template_code;
	@Id
	@Column(name = "user_id", unique = true)
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	@Column(name="template_id")
	public Long getTemplateId() {
		return templateId;
	}
	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

}
