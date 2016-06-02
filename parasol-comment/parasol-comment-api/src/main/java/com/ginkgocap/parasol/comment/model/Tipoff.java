package com.ginkgocap.parasol.comment.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the tb_tipoff database table.
 * 
 */
@Entity
@Table(name="tb_tipoff")
@NamedQuery(name="TbTipoff.findAll", query="SELECT t FROM TbTipoff t")
public class Tipoff implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="comm_obj_id")
	private Long commObjId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@Column(name="create_user_id")
	private Long createUserId;

	@Column(name="create_user_name")
	private String createUserName;

	private String reason;

	@Column(name="tipoff_id")
	private Long tipoffId;

	@Column(name="tipoff_type")
	private int tipoffType;

	public Tipoff() {
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

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Long getTipoffId() {
		return this.tipoffId;
	}

	public void setTipoffId(Long tipoffId) {
		this.tipoffId = tipoffId;
	}

	public int getTipoffType() {
		return this.tipoffType;
	}

	public void setTipoffType(int tipoffType) {
		this.tipoffType = tipoffType;
	}

}