package com.ginkgocap.parasol.comment.model;

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
 * 
 * @author allenshen
 * @date 2016年2月23日
 * @time 下午2:05:18
 * @Copyright Copyright©2015 www.gintong.com
 */
@JsonFilter("com.ginkgocap.parasol.comment.model.Comment")
@Entity
@Table(name = "tb_comment")
public class Comment implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1637202901564479194L;
	private long id; // id标识
	private long userId;// 创建这个记录的人用户（OwerID）
	private long toUserId = -1l;// 评论回复给谁
	private long appId; // 应用Id
	private long sourceTypeId; // 表示AppId中的类型
	private long sourceId; // 知识ID, 人脉ID,组织ID，等资源ID
	private String content; // 评论内容
	private long createAt; // 记录创建的时间
	private boolean visable; // 评论是否可见（审核的时候使用）

	@Id
	@GeneratedValue(generator = "CommentId")
	@GenericGenerator(name = "CommentId", strategy = "com.ginkgocap.ywxt.framework.dal.dao.id.util.TimeIdGenerator", parameters = { @Parameter(name = "sequence", value = "CommentId") })
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

	@Column(name = "content")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "createAt")
	public long getCreateAt() {
		return createAt;
	}

	public void setCreateAt(long createAt) {
		this.createAt = createAt;
	}

	@Column(name = "isVisable")
	public boolean isVisable() {
		return visable;
	}

	public void setVisable(boolean visable) {
		this.visable = visable;
	}

	@Column(name = "toUserId")
	public long getToUserId() {
		return toUserId;
	}

	public void setToUserId(long toUserId) {
		this.toUserId = toUserId;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", userId=" + userId + ", toUserId=" + toUserId + ", appId=" + appId + ", sourceTypeId=" + sourceTypeId + ", sourceId=" + sourceId
				+ ", content=" + content + ", createAt=" + createAt + ", visable=" + visable + "]";
	}

}
