package com.ginkgocap.parasol.tags.model;

import java.io.Serializable;

public class TagSource implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8746712290836358492L;

	private long id; // 'tagSources 主键',
	private long tagId; // '标签ID',
	private long appId;// 'Source 的应用ID',
	private long userId;// '创建TagSource的人',
	private long sourceId; // '资源ID',
	private String sourceTitle;// '资源标题'
	private long sourceType; // '资源类型 知识、人脉',
	private long createAt; // '更新时间',

	private String tagName; //不持久化
	private String sourceExtra;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getTagId() {
		return tagId;
	}

	public void setTagId(long tagId) {
		this.tagId = tagId;
	}

	public long getAppId() {
		return appId;
	}

	public void setAppId(long appId) {
		this.appId = appId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getSourceId() {
		return sourceId;
	}

	public void setSourceId(long sourceId) {
		this.sourceId = sourceId;
	}

	public String getSourceTitle() {
		return sourceTitle;
	}

	public void setSourceTitle(String sourceTitle) {
		this.sourceTitle = sourceTitle;
	}

	public long getSourceType() {
		return sourceType;
	}

	public void setSourceType(long sourceType) {
		this.sourceType = sourceType;
	}

	public long getCreateAt() {
		return createAt;
	}

	public void setCreateAt(long createAt) {
		this.createAt = createAt;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getSourceExtra() {
		return sourceExtra;
	}

	public void setSourceExtra(String sourceExtra) {
		this.sourceExtra = sourceExtra;
	}
}
