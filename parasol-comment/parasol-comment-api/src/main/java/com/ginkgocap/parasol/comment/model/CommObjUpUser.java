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


public class CommObjUpUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7663498171612135119L;
	private Long id;
	private Long commObjId;
	private Date createTime;
	private Long createUserId;
	private String createUserName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCommObjId() {
		return commObjId;
	}

	public void setCommObjId(Long commObjId) {
		this.commObjId = commObjId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

}