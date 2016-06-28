package com.ginkgocap.parasol.comment.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

public class ResReviewCommObj implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2587888060757620186L;
	private Long id;
	private Long createUserId;
	private String createUserName;
	private Long targetUserId;
	private String targetUserName;
	private String mainContent;
	private Integer resType;
	private Long resId;
	private Long ownerUserId;
	private Long commObjId;
	private Long appId;
	private int commObjType;
	private Long commTimes;
	private Long createTime;
	private Long parentCommObjId;
	private Long rootCommObjId;
	private Long upTimes;
	private Integer isAno;
	private Boolean delStatus;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Long getTargetUserId() {
		return targetUserId;
	}
	public void setTargetUserId(Long targetUserId) {
		this.targetUserId = targetUserId;
	}
	public String getTargetUserName() {
		return targetUserName;
	}
	public void setTargetUserName(String targetUserName) {
		this.targetUserName = targetUserName;
	}
	public String getMainContent() {
		return mainContent;
	}
	public void setMainContent(String mainContent) {
		this.mainContent = mainContent;
	}
	public Integer getResType() {
		return resType;
	}
	public void setResType(Integer resType) {
		this.resType = resType;
	}
	public Long getResId() {
		return resId;
	}
	public void setResId(Long resId) {
		this.resId = resId;
	}
	public Long getOwnerUserId() {
		return ownerUserId;
	}
	public void setOwnerUserId(Long ownerUserId) {
		this.ownerUserId = ownerUserId;
	}
	public Long getCommObjId() {
		return commObjId;
	}
	public void setCommObjId(Long commObjId) {
		this.commObjId = commObjId;
	}
	public Long getAppId() {
		return appId;
	}
	public void setAppId(Long appId) {
		this.appId = appId;
	}
	public int getCommObjType() {
		return commObjType;
	}
	public void setCommObjType(int commObjType) {
		this.commObjType = commObjType;
	}
	public Long getCommTimes() {
		return commTimes;
	}
	public void setCommTimes(Long commTimes) {
		this.commTimes = commTimes;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Long getParentCommObjId() {
		return parentCommObjId;
	}
	public void setParentCommObjId(Long parentCommObjId) {
		this.parentCommObjId = parentCommObjId;
	}
	public Long getRootCommObjId() {
		return rootCommObjId;
	}
	public void setRootCommObjId(Long rootCommObjId) {
		this.rootCommObjId = rootCommObjId;
	}
	public Long getUpTimes() {
		return upTimes;
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
	public Boolean getDelStatus() {
		return delStatus;
	}
	public void setDelStatus(Boolean delStatus) {
		this.delStatus = delStatus;
	}
}
