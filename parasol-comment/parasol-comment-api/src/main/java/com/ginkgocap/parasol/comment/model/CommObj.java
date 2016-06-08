package com.ginkgocap.parasol.comment.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the tb_comm_obj database table.
 * 
 */
@Entity
@Table(name="tb_comm_obj")
public class CommObj implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4878400708106550720L;

	@Id
	@Column(name="comm_obj_id")
	private Long commObjId;

	@Column(name="app_id")
	private Long appId;

	@Column(name="comm_obj_type")
	private int commObjType;

	@Column(name="comm_times")
	private Long commTimes;

	@Column(name="create_time")
	private Long createTime;

	@Column(name="parent_comm_obj_id")
	private Long parentCommObjId;

	@Column(name="root_comm_obj_id")
	private Long rootCommObjId;

	@Column(name="up_times")
	private Long upTimes;
	
	@Column(name="is_ano")
	private Integer isAno;

	public CommObj() {
	}

	public Long getCommObjId() {
		return this.commObjId;
	}

	public void setCommObjId(Long commObjId) {
		this.commObjId = commObjId;
	}

	public Long getAppId() {
		return this.appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public int getCommObjType() {
		return this.commObjType;
	}

	public void setCommObjType(int commObjType) {
		this.commObjType = commObjType;
	}

	public Long getCommTimes() {
		return this.commTimes;
	}

	public void setCommTimes(Long commTimes) {
		this.commTimes = commTimes;
	}

	public Long getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getParentCommObjId() {
		return this.parentCommObjId;
	}

	public void setParentCommObjId(Long parentCommObjId) {
		this.parentCommObjId = parentCommObjId;
	}

	public Long getRootCommObjId() {
		return this.rootCommObjId;
	}

	public void setRootCommObjId(Long rootCommObjId) {
		this.rootCommObjId = rootCommObjId;
	}

	public Long getUpTimes() {
		return this.upTimes;
	}

	public void setUpTimes(Long upTimes) {
		this.upTimes = upTimes;
	}

	public Integer getIsAno() {
		return isAno;
	}

	public void setIsAno(Integer isAno) {
		this.isAno = isAno;
	}

}