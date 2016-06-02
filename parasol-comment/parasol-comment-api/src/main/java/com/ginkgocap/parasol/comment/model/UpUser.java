package com.ginkgocap.parasol.comment.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the tb_up_user database table.
 * 
 */
@Entity
@Table(name="tb_up_user")
@NamedQuery(name="TbUpUser.findAll", query="SELECT t FROM TbUpUser t")
public class UpUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="up_user_id")
	private String upUserId;

	@Column(name="comm_obj_id")
	private Long commObjId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@Column(name="create_user_id")
	private Long createUserId;

	@Column(name="create_user_name")
	private String createUserName;

	public UpUser() {
	}

	public String getUpUserId() {
		return this.upUserId;
	}

	public void setUpUserId(String upUserId) {
		this.upUserId = upUserId;
	}

	public Long getCommObjId() {
		return this.commObjId;
	}

	public void setCommObjId(Long commObjId) {
		this.commObjId = commObjId;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateUserName() {
		return this.createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

}