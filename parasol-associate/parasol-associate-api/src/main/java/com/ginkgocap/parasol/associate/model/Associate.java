package com.ginkgocap.parasol.associate.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonFilter;

/**
 * 关联 主要用来描述：形如 知识关联的人、组织、事件等资源。 Exmaple： 应用A中的知识关联了人（张三），事物（XXX）等，其中张三是被关联对对象。
 * 
 * @author allenshen
 * @date 2016年2月23日
 * @time 下午2:05:18
 * @Copyright Copyright©2015 www.gintong.com
 */
@JsonFilter("com.ginkgocap.parasol.associate.model.Associate")
@Entity
@Table(name = "tb_associate")
public class Associate implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1637202901564479194L;
	private long id; // id标识
	private long userId;// 创建这个记录的人用户(OwerID)
	private long userName;//创建这个记录的人用户名字(OwerName)
	private long appId; // 应用Id
	private long sourceTypeId;// 表示知识, 人脉ID,组织ID，等资源类型，可以参考AssociateType对象
	private long sourceId; // 知识ID, 人脉ID,组织ID，等资源ID
	private String assocDesc;// 关联描述，比如文章的作者，或者编辑等；关联标签描述
	private long assocTypeId; // 被关联的类型可以参考AssociateType对象，如：知识, 人脉,组织，需求，事件等
	private long assocId; // 被关联数据ID
	private String assocTitle; // 被关联数据ID
	private String assocMetadata; // 被关联数据的的摘要用Json存放，如图片，连接URL定义等。
	private long createAt; // 记录创建的时间

	@Id
	@GeneratedValue(generator = "AssociateId")
	@GenericGenerator(name = "AssociateId", strategy = "com.ginkgocap.ywxt.framework.dal.dao.id.util.TimeIdGenerator", parameters = { @Parameter(name = "sequence", value = "AssociateId") })
	@Column(name = "id")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "userId")
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@Column(name = "userName")
	public long getUserName() {
		return userName;
	}

	public void setUserName(long userName) {
		this.userName = userName;
	}

	@Column(name = "appId")
	public long getAppId() {
		return appId;
	}

	public void setAppId(long appId) {
		this.appId = appId;
	}

	@Column(name = "sourceTypeId")
	public long getSourceTypeId() {
		return sourceTypeId;
	}

	public void setSourceTypeId(long sourceTypeId) {
		this.sourceTypeId = sourceTypeId;
	}

	@Column(name = "sourceId")
	public long getSourceId() {
		return sourceId;
	}

	public void setSourceId(long sourceId) {
		this.sourceId = sourceId;
	}

	@Column(name = "assocDesc")
	public String getAssocDesc() {
		return assocDesc;
	}

	public void setAssocDesc(String assocDesc) {
		this.assocDesc = assocDesc;
	}

	@Column(name = "assocTypeId")
	public long getAssocTypeId() {
		return assocTypeId;
	}

	public void setAssocTypeId(long assocTypeId) {
		this.assocTypeId = assocTypeId;
	}

	@Column(name = "assocId")
	public long getAssocId() {
		return assocId;
	}

	public void setAssocId(long assocId) {
		this.assocId = assocId;
	}

	@Column(name = "assocTitle")
	public String getAssocTitle() {
		return assocTitle;
	}

	public void setAssocTitle(String assocTitle) {
		this.assocTitle = assocTitle;
	}

	@Column(name = "assocMetadata")
	public String getAssocMetadata() {
		return assocMetadata;
	}

	public void setAssocMetadata(String assocMetadata) {
		this.assocMetadata = assocMetadata;
	}

	@Column(name = "createAt")
	public long getCreateAt() {
		return createAt;
	}

	public void setCreateAt(long createAt) {
		this.createAt = createAt;
	}

	@Override
	public String toString() {
		return "Associate [id=" + id + ", userId=" + userId + ", appId=" + appId + ", sourceTypeId=" + sourceTypeId + ", sourceId=" + sourceId + ", assocDesc=" + assocDesc
				+ ", assocTypeId=" + assocTypeId + ", assocId=" + assocId + ", assocTitle=" + assocTitle + ", assocMetadata=" + assocMetadata + ", createAt=" + createAt + "]";
	}

}
